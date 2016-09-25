package org.jokar.gankio.model.impl;

import com.trello.rxlifecycle.LifecycleTransformer;

import org.jokar.gankio.app.GankioApplication;
import org.jokar.gankio.db.DailyGankDB;
import org.jokar.gankio.di.component.network.DaggerDailyGankComponent;
import org.jokar.gankio.di.module.network.DailyGankModule;
import org.jokar.gankio.model.entities.GankDayEntities;
import org.jokar.gankio.model.event.DailyGankModel;
import org.jokar.gankio.model.network.exception.DataException;
import org.jokar.gankio.model.network.result.HttpResultFunc;
import org.jokar.gankio.model.network.services.DailyGankService;
import org.jokar.gankio.utils.JLog;
import org.jokar.gankio.utils.Schedulers;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


import static org.jokar.gankio.utils.Preconditions.checkNotNull;

/**
 * Created by JokAr on 2016/9/24.
 */
public class DailyGankModelImpl implements DailyGankModel {

    @Inject
    Retrofit mRetrofit;
    @Inject
    DailyGankService mDailyGankService;

    public DailyGankModelImpl() {
        DaggerDailyGankComponent.builder()
                .netComponent(GankioApplication.getNetComponent())
                .dailyGankModule(new DailyGankModule())
                .build()
                .inject(this);
    }

    @Override
    public void request(LifecycleTransformer lifecycleTransformer,
                        DailyGankDB dailyGankDB,
                        String year,
                        String month,
                        String day,
                        DailyGankCallback callback) {

        checkNotNull(lifecycleTransformer);
        checkNotNull(callback);
        checkNotNull(dailyGankDB);

        callback.start();
        String Day = year + "-" + month + "-" + day;

        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean hasDailyGank = dailyGankDB.hasDailyGank(Day);
                subscriber.onNext(hasDailyGank);
            }
        })
                .filter(hasDailyGank -> {
                    //如果有本地数据则不请求网络数据
                    if (hasDailyGank) {
                        GankDayEntities gankDayEntities = dailyGankDB.query(Day);
                        callback.requestSuccess(gankDayEntities);
                        callback.compelete();
                        return false;
                    }
                    return true;
                })
                .subscribe(hasDailyGank -> {
                    //请求网络数据
                    mDailyGankService.day(year, month, day)
                            .compose(lifecycleTransformer)
                            .compose(Schedulers.applySchedulersIO())
                            .map(new HttpResultFunc<GankDayEntities>())
                            .map(new Func1<GankDayEntities, GankDayEntities>() {
                                @Override
                                public GankDayEntities call(GankDayEntities gankDayEntities) {
                                    if (gankDayEntities.isNull()) {
                                        throw new DataException("今日暂无干货!");
                                    }
                                    return gankDayEntities;
                                }

                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<GankDayEntities>() {
                                @Override
                                public void onCompleted() {
                                    callback.compelete();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    JLog.e(e);
                                    callback.requestFail(e);
                                }

                                @Override
                                public void onNext(GankDayEntities gankDayEntities) {
                                    gankDayEntities.setDay(Day);
                                    dailyGankDB.insert(gankDayEntities);
                                    callback.requestSuccess(gankDayEntities);
                                }
                            });

                });

    }
}
