package org.jokar.gankio.view.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.trello.rxlifecycle2.android.FragmentEvent;

import org.jokar.gankio.R;
import org.jokar.gankio.db.DataDB;
import org.jokar.gankio.di.component.db.DaggerDataDBCom;
import org.jokar.gankio.di.component.db.DataDBCom;
import org.jokar.gankio.di.component.preseneter.DaggerDataPreseneterCom;
import org.jokar.gankio.di.module.db.DataDBModule;
import org.jokar.gankio.di.module.models.DataModelModule;
import org.jokar.gankio.di.module.view.FragmentViewModule;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.model.network.exception.DataException;
import org.jokar.gankio.model.rxbus.RxBus;
import org.jokar.gankio.model.rxbus.event.MainToolbarEvent;
import org.jokar.gankio.presenter.impl.DataPresenterImpl;
import org.jokar.gankio.utils.DataEntitieDiffCallback;
import org.jokar.gankio.utils.JToast;
import org.jokar.gankio.utils.image.Imageloader;
import org.jokar.gankio.view.activity.GankActivity;
import org.jokar.gankio.view.activity.GankImageActivity;
import org.jokar.gankio.view.adapter.GankioFragmentAdapter;
import org.jokar.gankio.view.adapter.base.LoadMoreAdapterItemClickListener;
import org.jokar.gankio.view.ui.FragmentView;
import org.jokar.gankio.widget.ErrorView;
import org.jokar.gankio.widget.LazzyFragment;
import org.jokar.gankio.widget.RecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    Unbinder mUnbinder;

    private GankioFragmentAdapter mAdapter;
    private String type;
    private int pageSize = 1;
    private int count = 15;

    private ArrayList<DataEntities> mDataEntitiesList;

    private boolean mIsVisibleToUser = false;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        type = args.getString("type");
    }


    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            type = savedInstanceState.getString("type");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initViews(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        if (type.equals("福利")) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        } else
            recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light
                , android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);


        swipeRefreshLayout.setOnRefreshListener(() -> {
            errorView.setRetryButtonClickable(false);
            mPresenter.refrsh(mDataDB, type, count, 1, bindUntilEvent(FragmentEvent.STOP));
        });

        errorView.setOnRetryListener(() -> {
            showLoadProgress();
            errorView.setRetryButtonClickable(false);
            mPresenter.refrsh(mDataDB, type, count, 1, bindUntilEvent(FragmentEvent.STOP));
        });

        RxBus.getBus().toMainThreadObservable(bindUntilEvent(FragmentEvent.STOP))
                .subscribe(event -> {
                    if (event instanceof MainToolbarEvent) {
                        if (recyclerView != null && mIsVisibleToUser) {

                            recyclerView.post(() -> recyclerView.scrollToPosition(0));

                        }
                    }
                });
    }

    @Override
    public void loadData() {
        super.loadData();
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

        mAdapter = new GankioFragmentAdapter(getContext(), type, mDataEntitiesList,
                bindUntilEvent(FragmentEvent.DESTROY));
        recyclerView.setAdapter(mAdapter);
        setAdapterClick();
        //请求数据
        mPresenter.request(mDataDB, type, 15, 1, bindUntilEvent(FragmentEvent.STOP));

    }

    @Override
    public void showLoadProgress() {
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));

    }

    @Override
    public void completeLoadProgress() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void loadStartLocalData(ArrayList<DataEntities> searchEntities) {
        mDataEntitiesList = searchEntities;
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new DataEntitieDiffCallback(mAdapter.getData(), mDataEntitiesList));
        mAdapter.setData(mDataEntitiesList);
        diffResult.dispatchUpdatesTo(mAdapter);

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
    public void loadData(ArrayList<DataEntities> searchEntities) {
        if (errorView.isShown()) {
            errorView.setVisibility(View.GONE);
            errorView.setRetryButtonClickable(true);
            recyclerView.setVisibility(View.VISIBLE);
        }
        mDataEntitiesList = searchEntities;

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new DataEntitieDiffCallback(mAdapter.getData(), mDataEntitiesList));
        mAdapter.setData(mDataEntitiesList);
        diffResult.dispatchUpdatesTo(mAdapter);

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
    public void loadMore(ArrayList<DataEntities> dataEntitiesList) {
        mDataEntitiesList.addAll(dataEntitiesList);

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff
                (new DataEntitieDiffCallback(mAdapter.getData(), mDataEntitiesList), true);

        mAdapter.setData(mDataEntitiesList);
        diffResult.dispatchUpdatesTo(mAdapter);

        recyclerView.scrollToPosition(mAdapter.getItemCount() - count);

    }

    @Override
    public void loadMoreFail(Throwable e) {
        mAdapter.setCanLoading(true);
        if (e.getClass() == DataException.class) {
            mAdapter.showNoData();
        } else {
            JToast.Toast(getContext(),e.getMessage());
            pageSize--;
            mAdapter.setFootClickable(true);
            mAdapter.footShowClickText();
        }
    }

    public void setAdapterClick() {

        mAdapter.setOnItemClickListener(new GankioFragmentAdapter.ItemClickListener() {
            @Override
            public void itemClick(DataEntities dataEntities) {

                Intent intent = new Intent(getActivity(), GankActivity.class);
                intent.putExtra("dataEntities", dataEntities);
                startActivity(intent);

            }

            @Override
            public void imageItemClick(DataEntities dataEntitie, ImageView imageView) {
                Intent intent = new Intent(getActivity(), GankImageActivity.class);
                ActivityOptionsCompat mActivityOptionsCompat;
                intent.putExtra("dataEntities", dataEntitie);
                if (Build.VERSION.SDK_INT >= 21) {
                    mActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            getActivity(), imageView, getString(R.string.transition_name_image));
                } else {
                    mActivityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(
                            imageView, 0, 0,
                            imageView.getWidth(),
                            imageView.getHeight());
                }
                startActivity(intent, mActivityOptionsCompat.toBundle());
            }
        });

        mAdapter.setOnItemClickListener(new LoadMoreAdapterItemClickListener() {
            @Override
            public void itemClickListener(int position) {

            }

            @Override
            public void loadMore() {
                pageSize++;
                mPresenter.loadMore(mDataDB, type, count, pageSize, bindUntilEvent(FragmentEvent.STOP));
            }

            @Override
            public void footViewClick() {
                pageSize++;
                mPresenter.loadMore(mDataDB, type, count, pageSize, bindUntilEvent(FragmentEvent.STOP));
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence("type", type);
        super.onSaveInstanceState(outState);
    }
}
