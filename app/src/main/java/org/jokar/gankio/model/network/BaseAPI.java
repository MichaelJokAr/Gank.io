package org.jokar.gankio.model.network;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * gank.io api: http://gank.io/api
 * Created by JokAr on 16/9/10.
 */
public abstract class BaseAPI {

    public String baseUrl = "http://gank.io/api/";

    public BaseAPI() {

    }

    protected <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    protected  <T> Observable.Transformer<T, T> applySchedulersIO() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io());
            }
        };
    }
}
