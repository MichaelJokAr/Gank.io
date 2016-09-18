package org.jokar.gankio.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.rxlifecycle.android.FragmentEvent;

import org.jokar.gankio.R;
import org.jokar.gankio.db.SearchDB;
import org.jokar.gankio.di.component.app.DaggerSearchDBComponent;
import org.jokar.gankio.di.component.app.SearchDBComponent;
import org.jokar.gankio.di.component.preseneter.DaggerSearchPresenterCom;
import org.jokar.gankio.di.module.db.SearchDBModule;
import org.jokar.gankio.di.module.models.SearchModelModule;
import org.jokar.gankio.di.module.view.FragmentViewModule;
import org.jokar.gankio.model.entities.SearchEntities;
import org.jokar.gankio.model.rxbus.RxBus;
import org.jokar.gankio.model.rxbus.event.MainViewPagerEvent;
import org.jokar.gankio.presenter.impl.SearchPresenterImpl;
import org.jokar.gankio.view.adapter.TypeFragmentAdapter;
import org.jokar.gankio.view.ui.FragmentView;
import org.jokar.gankio.widget.ErrorView;
import org.jokar.gankio.widget.LazzyFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Android
 * Created by JokAr on 16/9/17.
 */
public class AndroidFragment extends LazzyFragment implements FragmentView{

    @BindView(R.id.errorView)
    ErrorView errorView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    SearchPresenterImpl mSearchPresenter;

    @Inject
    SearchDB mSearchDB;

    private TypeFragmentAdapter mAdapter;
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
        RxBus.getBus().send(new MainViewPagerEvent(1));
        //初始化
        SearchDBComponent searchDBComponent = DaggerSearchDBComponent.builder()
                .searchDBModule(new SearchDBModule(getContext()))
                .build();

        DaggerSearchPresenterCom.builder()
                .searchDBComponent(searchDBComponent)
                .fragmentViewModule(new FragmentViewModule(this))
                .searchModelModule(new SearchModelModule())
                .build()
                .inject(this);


        //请求数据
        mSearchPresenter.request(mSearchDB,"Android",15,1,
                bindUntilEvent(FragmentEvent.STOP));
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
    public void loadStartLocalData(List<SearchEntities> searchEntities) {
        mAdapter = new TypeFragmentAdapter(getContext(),searchEntities);
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
    public void loadData(List<SearchEntities> searchEntities) {
        mAdapter = new TypeFragmentAdapter(getContext(),searchEntities);
        recyclerView.setAdapter(mAdapter);
    }
}
