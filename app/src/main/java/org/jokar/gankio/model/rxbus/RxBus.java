package org.jokar.gankio.model.rxbus;

import com.trello.rxlifecycle.LifecycleTransformer;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by JokAr on 16/8/2.
 */
public class RxBus {
    private final Subject<Object, Object> _bus;
    private volatile static RxBus instace;

    public RxBus() {
        _bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getBus() {
        if (instace == null) {
            synchronized (RxBus.class) {
                if (instace == null)
                    instace = new RxBus();
            }
        }
        return instace;
    }

    public void send(Object o) {
        _bus.onNext(o);
    }

    /**
     * dosen't designation to use specail thread,It's depending on what the 'send' method use
     *
     * @param lifecycleTransformer rxlifecycle
     * @return
     */
    public Observable<Object> toObserverable(LifecycleTransformer lifecycleTransformer){
        return _bus.compose(lifecycleTransformer);
    }

    /**
     * designation use the MainThread, whatever the 'send' method use
     *
     * @param lifecycleTransformer rxlifecycle
     * @return
     */
    public Observable<Object> toMainThreadObserverable(LifecycleTransformer lifecycleTransformer){
        return _bus.observeOn(AndroidSchedulers.mainThread()).compose(lifecycleTransformer);
    }
}
