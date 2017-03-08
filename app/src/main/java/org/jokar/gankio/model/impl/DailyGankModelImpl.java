package org.jokar.gankio.model.impl;


import com.trello.rxlifecycle2.LifecycleTransformer;

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

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


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
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> subscriber) throws Exception {
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
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .map(new HttpResultFunc<GankDayEntities>())
                            .map(new Function<GankDayEntities, GankDayEntities>() {
                                @Override
                                public GankDayEntities apply(@NonNull GankDayEntities gankDayEntities)
                                        throws Exception {
                                    if (gankDayEntities.isNull()) {
                                        throw new DataException("今日暂无干货!");
                                    }
                                    return gankDayEntities;
                                }

                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ResourceObserver<GankDayEntities>() {

                                @Override
                                public void onError(Throwable e) {
                                    JLog.e(e);
                                    callback.requestFail(e);
                                }

                                @Override
                                public void onComplete() {
                                    callback.compelete();
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
