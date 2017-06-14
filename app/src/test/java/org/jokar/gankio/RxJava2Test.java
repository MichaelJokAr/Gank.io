package org.jokar.gankio;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subscribers.ResourceSubscriber;
import io.reactivex.subscribers.SafeSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;

/**
 * Created by JokAr on 2017/2/26.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class RxJava2Test {
    private static final String TAG = "RxJava2Test";
    @Before
    public void before() {
        //输出日志
        ShadowLog.stream = System.out;
    }
    
    @Test
    public void test(){
        ArrayList<String> arrays = new ArrayList<>();


        Subscriber<ArrayList<String>> subscriber = new Subscriber<ArrayList<String>>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(ArrayList<String> strings) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

    }
}
