package org.jokar.gankio.model.rxbus;


import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;


/**
 * Created by JokAr on 16/8/2.
 */
public class RxBus {
    private final FlowableProcessor<Object> _bus;
    private volatile static RxBus instace;

    public RxBus() {
        _bus =PublishProcessor.create().toSerialized();
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
    public Flowable toObservable(LifecycleTransformer lifecycleTransformer){
        return _bus.compose(lifecycleTransformer).onBackpressureDrop();
    }

    /**
     * designation use the MainThread, whatever the 'send' method use
     *
     * @param lifecycleTransformer rxlifecycle
     * @return
     */
    public Flowable toMainThreadObservable(LifecycleTransformer lifecycleTransformer){
        return _bus.observeOn(AndroidSchedulers.mainThread()).compose(lifecycleTransformer).onBackpressureDrop();
    }
}
