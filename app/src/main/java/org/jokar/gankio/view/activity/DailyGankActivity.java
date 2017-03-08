package org.jokar.gankio.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;


import com.trello.rxlifecycle2.android.ActivityEvent;

import org.jokar.gankio.R;
import org.jokar.gankio.db.DailyGankDB;
import org.jokar.gankio.di.component.db.DaggerDailyGankDBCom;
import org.jokar.gankio.di.component.db.DailyGankDBCom;
import org.jokar.gankio.di.component.preseneter.DaggerDailyGankPresenterCom;
import org.jokar.gankio.di.module.db.DailyGankDBModule;
import org.jokar.gankio.di.module.models.DailyGankModelModule;
import org.jokar.gankio.di.module.view.DailyGankViewModule;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.model.entities.GankDayEntities;
import org.jokar.gankio.presenter.impl.DailyGankPresenterImpl;
import org.jokar.gankio.utils.image.Imageloader;
import org.jokar.gankio.view.adapter.DailyGankAdapter;
import org.jokar.gankio.view.ui.DailyGankView;
import org.jokar.gankio.widget.ErrorView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 每日干货
 * Created by JokAr on 2016/9/24.
 */
public class DailyGankActivity extends BaseActivity implements DailyGankView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.errorView)
    ErrorView errorView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.image)
    ImageView image;

    @Inject
    DailyGankDB mDailyGankDB;
    @Inject
    DailyGankPresenterImpl mGankPresenter;

    private BottomSheetBehavior<RecyclerView> behavior;
    private DailyGankAdapter mAdapter;
    private GankDayEntities mGankDayEntities;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailygank);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        initToolbar(toolbar, "今日干货");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        errorView.setOnRetryListener(() -> {
            mGankPresenter.requestDailyGank(System.currentTimeMillis(), mDailyGankDB,
                    bindUntilEvent(ActivityEvent.STOP));
        });

        //初始化
        DailyGankDBCom gankDBCom = DaggerDailyGankDBCom.builder()
                .dailyGankDBModule(new DailyGankDBModule(this))
                .build();
        DaggerDailyGankPresenterCom.builder()
                .dailyGankDBCom(gankDBCom)
                .dailyGankModelModule(new DailyGankModelModule())
                .dailyGankViewModule(new DailyGankViewModule(this))
                .build()
                .inject(this);

        //
        behavior = BottomSheetBehavior.from(recyclerView);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        behavior.setPeekHeight(400);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        //请求数据
        mGankPresenter.requestDailyGank(System.currentTimeMillis(), mDailyGankDB,
                bindUntilEvent(ActivityEvent.STOP));
    }

    @Override
    public void showRequestProgress() {
        recyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        errorView.showLoad();

    }

    @Override
    public void compeleteProgress() {
        recyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void loadData(GankDayEntities gankDayEntities) {
        mGankDayEntities = gankDayEntities;
        setBackgroundImage(mGankDayEntities);
        mAdapter = new DailyGankAdapter(this, mGankDayEntities, bindUntilEvent(ActivityEvent.DESTROY));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setListener(dataEntities -> {
            Intent intent = new Intent(this, GankActivity.class);
            intent.putExtra("dataEntities", dataEntities);
            startActivity(intent);
        });
    }

    private void setBackgroundImage(GankDayEntities gankDayEntities) {
        if (gankDayEntities.hasWelfare()) {
            List<DataEntities> welfare = gankDayEntities.getWelfare();

            Imageloader.loadImage(this,welfare.get(0).getUrl(),image);
        }
    }

    @Override
    public void showFail(Throwable e) {
        recyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        errorView.showError(e.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGankDayEntities = null;
        if (mAdapter != null) {
            mAdapter.setListener(null);
        }
    }
}
