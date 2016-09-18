package org.jokar.gankio.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by JokAr on 16/9/17.
 */
public class Schedulers {

    public static  <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(rx.schedulers.Schedulers.io())
                        .unsubscribeOn(rx.schedulers.Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static  <T> Observable.Transformer<T, T> applySchedulersIO() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(rx.schedulers.Schedulers.io())
                        .unsubscribeOn(rx.schedulers.Schedulers.io());
            }
        };
    }
}
