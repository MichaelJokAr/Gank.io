package org.jokar.gankio.presenter.impl;


import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.db.DailyGankDB;
import org.jokar.gankio.model.entities.GankDayEntities;
import org.jokar.gankio.model.event.DailyGankModel;
import org.jokar.gankio.presenter.event.DailyGankPresenter;
import org.jokar.gankio.view.ui.MainView;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * Created by JokAr on 2016/9/25.
 */
public class MainPresenterImpl implements DailyGankPresenter {

    private DailyGankModel mDailyGankModel;
    private MainView mMainView;
    private SimpleDateFormat formatter;

    @Inject
    public MainPresenterImpl(DailyGankModel dailyGankModel, MainView mainView) {
        mDailyGankModel = dailyGankModel;
        mMainView = mainView;

        formatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public void requestDailyGank(long time, DailyGankDB dailyGankDB,
                                 LifecycleTransformer lifecycleTransformer) {

        String day = formatter.format(new Date(time));
        String[] times = day.split("-");
        Observable.create((ObservableOnSubscribe<Boolean>) subscriber ->{
            subscriber.onNext(true);
        })
                .filter(aBoolean -> {
                    if (dailyGankDB.hasDailyGank(day)) {

                        return false;
                    }
                    return true;
                }).subscribe(aBoolean -> {
            mDailyGankModel.request(lifecycleTransformer,
                    dailyGankDB, times[0], times[1], times[2],
                    new DailyGankModel.DailyGankCallback() {
                        @Override
                        public void start() {

                        }

                        @Override
                        public void requestSuccess(GankDayEntities gankDayEntities) {
                            gankDayEntities = null;
                            mMainView.showDailyGank();
                        }

                        @Override
                        public void compelete() {

                        }

                        @Override
                        public void requestFail(Throwable e) {

                        }
                    });
        });


    }
}
