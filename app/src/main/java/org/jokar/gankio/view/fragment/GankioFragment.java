package org.jokar.gankio.view.fragment;

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
import org.jokar.gankio.view.adapter.GankioFragmentAdapter;
import org.jokar.gankio.view.ui.FragmentView;
import org.jokar.gankio.widget.ErrorView;
import org.jokar.gankio.widget.LazzyFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * GanK.io
 * Created by JokAr on 16/9/17.
 */
public class GankioFragment extends LazzyFragment implements FragmentView{

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light
                , android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

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
        mPresenter.request(mDataDB,type,15,1,bindUntilEvent(FragmentEvent.STOP));

    }

    @Override
    public void showLoadProgress() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

    }

    @Override
    public void completeLoadProgress() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void loadStartLocalData(List<DataEntities> searchEntities) {
        mAdapter = new GankioFragmentAdapter(getContext(),searchEntities);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void loadStartNoLocalData() {

    }

    @Override
    public void loadNoLoacalData(Throwable e) {

        errorView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorView.showError(e.getMessage());
    }

    @Override
    public void loadNoData(Throwable e) {

    }

    @Override
    public void loadData(List<DataEntities> searchEntities) {
        mAdapter = new GankioFragmentAdapter(getContext(),searchEntities);
        recyclerView.setAdapter(mAdapter);
    }
}
