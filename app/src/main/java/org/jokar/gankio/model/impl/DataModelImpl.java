package org.jokar.gankio.model.impl;

import android.support.annotation.NonNull;


import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.app.GankioApplication;
import org.jokar.gankio.db.DataDB;
import org.jokar.gankio.di.component.network.DaggerDataComponent;
import org.jokar.gankio.di.module.network.DataModule;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.model.event.DataModel;
import org.jokar.gankio.model.network.exception.CustomizeException;
import org.jokar.gankio.model.network.exception.DataException;
import org.jokar.gankio.model.network.result.HttpResultFunc;
import org.jokar.gankio.model.network.services.DataService;
import org.jokar.gankio.utils.JLog;
import org.jokar.gankio.utils.SchedulersUtil;
import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.ResourceObserver;
import retrofit2.Retrofit;

import static org.jokar.gankio.utils.Preconditions.checkNotNull;

/**
 * Created by JokAr on 16/9/19.
 */
public class DataModelImpl implements DataModel {

    @Inject
    Retrofit mRetrofit;
    @Inject
    DataService mDataService;

    public DataModelImpl() {

        DaggerDataComponent.builder()
                .netComponent(GankioApplication.getNetComponent())
                .dataModule(new DataModule())
                .build()
                .inject(this);
    }

    @Override
    public void request(String type, int count, int pageSize, final DataDB dataDB,
                        @NonNull LifecycleTransformer lifecycleTransformer,
                        @NonNull final DataCallBack callBack) {
        checkNotNull(lifecycleTransformer);
        checkNotNull(callBack);
        final List<DataEntities> loaclEntities;
        if (type.equals("all")) {
            loaclEntities = dataDB.query();
        } else {
            loaclEntities = dataDB.query(type);
        }

        final boolean hasLocalData = (loaclEntities != null && loaclEntities.size() > 0) ? true : false;
        callBack.start(hasLocalData, loaclEntities);
        mDataService.getData(type, count, pageSize)
                .compose(lifecycleTransformer)
                .compose(SchedulersUtil.applySchedulersIO())
                .map(new HttpResultFunc())
                .map(new Function<ArrayList<DataEntities>, ArrayList<DataEntities>>() {

                    /**
                     * Apply some calculation to the input value and return some other value.
                     *
                     * @param dataEntities the input value
                     * @return the output value
                     * @throws Exception on error
                     */
                    @Override
                    public ArrayList<DataEntities> apply(@NonNull ArrayList<DataEntities> dataEntities) throws Exception {
                        if (dataEntities == null || dataEntities.size() == 0) {
                            throw new DataException("无数据");
                        }
                        return dataEntities;
                    }



                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ArrayList<DataEntities>>() {

                    @Override
                    public void onComplete() {
                        callBack.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        JLog.e(e);
                        callBack.requestFail(hasLocalData, e);
                    }

                    @Override
                    public void onNext(ArrayList<DataEntities> dataEntitiesList) {
                        dataDB.insert(dataEntitiesList);
                        callBack.requestSuccess(dataEntitiesList);
                    }
                });
    }

    @Override
    public void refresh(String type, int count, int pageSize, DataDB dataDB,
                        LifecycleTransformer lifecycleTransformer,
                        DataCallBack callBack) {
        checkNotNull(callBack);
        checkNotNull(lifecycleTransformer);

        callBack.start(false, null);

        mDataService.getData(type, count, pageSize)
                .compose(lifecycleTransformer)
                .compose(SchedulersUtil.applySchedulersIO())
                .map(new HttpResultFunc())
                .map(new Function<ArrayList<DataEntities>, ArrayList<DataEntities>>() {

                    @Override
                    public ArrayList<DataEntities> apply(@NonNull ArrayList<DataEntities> entitiesList) throws Exception {
                        if (entitiesList == null || entitiesList.size() == 0) {
                            throw new DataException("无数据");
                        }
                        return entitiesList;
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<ArrayList<DataEntities>>() {

                    @Override
                    public void onComplete() {
                        callBack.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        JLog.e(e);
                        callBack.requestFail(false, e);
                    }

                    @Override
                    public void onNext(ArrayList<DataEntities> dataEntitiesList) {
                        dataDB.insert(dataEntitiesList);
                        callBack.requestSuccess(dataEntitiesList);
                    }
                });
    }
}
