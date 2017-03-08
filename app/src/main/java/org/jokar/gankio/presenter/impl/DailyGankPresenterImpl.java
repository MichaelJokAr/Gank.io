package org.jokar.gankio.presenter.impl;


import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.db.DailyGankDB;
import org.jokar.gankio.model.entities.GankDayEntities;
import org.jokar.gankio.model.event.DailyGankModel;
import org.jokar.gankio.presenter.event.DailyGankPresenter;
import org.jokar.gankio.view.ui.DailyGankView;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

/**
 * Created by JokAr on 2016/9/25.
 */

public class DailyGankPresenterImpl implements DailyGankPresenter {
    private DailyGankModel mDailyGankModel;
    private DailyGankView mDailyGankView;
    private SimpleDateFormat formatter;

    @Inject
    public DailyGankPresenterImpl(DailyGankModel dailyGankModel, DailyGankView dailyGankView) {
        mDailyGankModel = dailyGankModel;
        mDailyGankView = dailyGankView;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public void requestDailyGank(long time, DailyGankDB dailyGankDB,
                                 LifecycleTransformer lifecycleTransformer) {
        String day = formatter.format(new Date(time));
        String[] times = day.split("-");
        mDailyGankModel.request(lifecycleTransformer, dailyGankDB, times[0], times[1], times[2],
                new DailyGankModel.DailyGankCallback() {

                    @Override
                    public void start() {
                        mDailyGankView.showRequestProgress();
                    }

                    @Override
                    public void requestSuccess(GankDayEntities gankDayEntities) {
                        mDailyGankView.loadData(gankDayEntities);
                    }

                    @Override
                    public void compelete() {
                        mDailyGankView.compeleteProgress();
                    }

                    @Override
                    public void requestFail(Throwable e) {
                        mDailyGankView.showFail(e);
                    }
                });
    }
}
