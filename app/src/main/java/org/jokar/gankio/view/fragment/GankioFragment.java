package org.jokar.gankio.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.android.FragmentEvent;

import org.jokar.gankio.R;
import org.jokar.gankio.db.DataDB;
import org.jokar.gankio.di.component.db.DaggerDataDBCom;
import org.jokar.gankio.di.component.db.DataDBCom;
import org.jokar.gankio.di.component.preseneter.DaggerDataPreseneterCom;
import org.jokar.gankio.di.module.db.DataDBModule;
import org.jokar.gankio.di.module.models.DataModelModule;
import org.jokar.gankio.di.module.view.FragmentViewModule;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.model.rxbus.RxBus;
import org.jokar.gankio.model.rxbus.event.MainViewPagerEvent;
import org.jokar.gankio.presenter.impl.DataPresenterImpl;
import org.jokar.gankio.utils.JToast;
import org.jokar.gankio.view.activity.GankActivity;
import org.jokar.gankio.view.adapter.GankioFragmentAdapter;
import org.jokar.gankio.view.ui.FragmentView;
import org.jokar.gankio.widget.ErrorView;
import org.jokar.gankio.widget.LazzyFragment;
import org.jokar.gankio.widget.RecyclerOnScrollListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * GanK.io
 * Created by JokAr on 16/9/17.
 */
public class GankioFragment extends LazzyFragment implements FragmentView {

    @BindView(R.id.errorView)
    ErrorView errorView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    DataDB mDataDB;
    @Inject
    DataPresenterImpl mPresenter;

    private GankioFragmentAdapter mAdapter;
    private String type;
    private int pageSize = 1;
    private int count = 15;

    private List<DataEntities> mDataEntitiesList;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        type = args.getString("type");
    }


    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initViews(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light
                , android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        recyclerView.addOnScrollListener(new RecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mAdapter.setFootClickable(false);
                pageSize++;
                mPresenter.loadMore(mDataDB, type, count, pageSize, bindUntilEvent(FragmentEvent.STOP));
            }
        });
        swipeRefreshLayout.setOnRefreshListener(() -> {
            errorView.setRetryButtonClickable(false);
            mPresenter.refrsh(mDataDB, type, count, 1, bindUntilEvent(FragmentEvent.STOP));
        });

        errorView.setOnRetryListener(() -> {
            showLoadProgress();
            errorView.setRetryButtonClickable(false);
            mPresenter.refrsh(mDataDB, type, count, 1, bindUntilEvent(FragmentEvent.STOP));
        });

    }

    @Override
    public void loadData() {
        super.loadData();
        RxBus.getBus().send(new MainViewPagerEvent(type));

        //初始化
        DataDBCom dataDBCom = DaggerDataDBCom.builder()
                .dataDBModule(new DataDBModule(getContext()))
                .build();

        DaggerDataPreseneterCom.builder()
                .dataDBCom(dataDBCom)
                .fragmentViewModule(new FragmentViewModule(this))
                .dataModelModule(new DataModelModule())
                .build()
                .inject(this);

        //请求数据
        mPresenter.request(mDataDB, type, 15, 1, bindUntilEvent(FragmentEvent.STOP));

    }

    @Override
    public void showLoadProgress() {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));

    }

    @Override
    public void completeLoadProgress() {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void loadStartLocalData(List<DataEntities> searchEntities) {
        mDataEntitiesList = searchEntities;
        mAdapter = new GankioFragmentAdapter(getContext(), type, mDataEntitiesList);
        recyclerView.setAdapter(mAdapter);
        setAdapterClick();
    }

    @Override
    public void loadStartNoLocalData() {
        errorView.setVisibility(View.GONE);
        errorView.setRetryButtonClickable(true);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadNoLoacalData(Throwable e) {

        errorView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorView.showError(e.getMessage());
    }

    @Override
    public void loadNoData(Throwable e) {
        JToast.Toast(getContext(), e.getMessage());
    }

    @Override
    public void loadData(List<DataEntities> searchEntities) {
        if (errorView.isShown()) {
            errorView.setVisibility(View.GONE);
            errorView.setRetryButtonClickable(true);
            recyclerView.setVisibility(View.VISIBLE);
        }
        mDataEntitiesList = searchEntities;
        mAdapter = new GankioFragmentAdapter(getContext(), type, mDataEntitiesList);
        recyclerView.setAdapter(mAdapter);
        setAdapterClick();
    }

    @Override
    public void refreshFail(Throwable e) {
        if (errorView.isShown()) {
            errorView.showError("刷新失败: " + e.getMessage(), true);
        } else {
            JToast.Toast(getContext(), "刷新失败: " + e.getMessage());
        }
    }

    @Override
    public void loadMore(List<DataEntities> dataEntitiesList) {
        mDataEntitiesList.addAll(dataEntitiesList);
        mAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(mAdapter.getItemCount() - count);
    }

    @Override
    public void loadMoreFail(Throwable e) {
        JToast.Toast(getContext(), "加载失败: " + e.getMessage());
        pageSize--;
        mAdapter.setFootClickable(true);
        mAdapter.footShowClickText();
    }

    public void setAdapterClick() {

        mAdapter.setOnItemClickListener(new GankioFragmentAdapter.ItemClickListener() {
            @Override
            public void itemClick(DataEntities dataEntities) {
                if(dataEntities.getType().equals("福利")){

                }else {
                    Intent intent = new Intent(getActivity(), GankActivity.class);
                    intent.putExtra("dataEntities",dataEntities);
                    startActivity(intent);
                }
            }

            @Override
            public void footViewClick() {
                mAdapter.setFootClickable(false);
                pageSize++;
                mPresenter.loadMore(mDataDB, type, count, pageSize,
                        bindUntilEvent(FragmentEvent.STOP));
            }
        });
    }
}
