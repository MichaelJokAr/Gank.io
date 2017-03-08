package org.jokar.gankio.utils;


import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by JokAr on 16/9/17.
 */
public class SchedulersUtil {



    public static <T> ObservableTransformer<T, T> applySchedulersIO(){

        return observable -> observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    };
}
