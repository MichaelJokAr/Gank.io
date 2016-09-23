package org.jokar.gankio.model.impl;

import com.trello.rxlifecycle.LifecycleTransformer;

import org.jokar.gankio.app.GankioApplication;
import org.jokar.gankio.di.component.network.DaggerAddgankComponent;
import org.jokar.gankio.di.module.network.AddgankModule;
import org.jokar.gankio.model.event.AddGankModel;
import org.jokar.gankio.model.network.result.HttpResultCodeFunc;
import org.jokar.gankio.model.network.services.AddgankService;
import org.jokar.gankio.utils.JLog;
import org.jokar.gankio.utils.Schedulers;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static org.jokar.gankio.utils.Preconditions.checkNotNull;

/**
 * Created by JokAr on 2016/9/23.
 */
public class AddGankModelImpl implements AddGankModel {

    @Inject
    Retrofit mRetrofit;
    @Inject
    AddgankService mAddgankService;

    public AddGankModelImpl() {
        DaggerAddgankComponent.builder()
                .netComponent(GankioApplication.getNetComponent())
                .addgankModule(new AddgankModule())
                .build()
                .inject(this);
    }

    @Override
    public void addGank(LifecycleTransformer lifecycleTransformer,
                        String url,
                        String desc,
                        String who,
                        String type,
                        boolean debug,
                        AddGankCallBack callBack) {
        checkNotNull(callBack);

        mAddgankService.addGank(url, desc, who, type, debug)
                .compose(lifecycleTransformer)
                .compose(Schedulers.applySchedulersIO())
                .map(new HttpResultCodeFunc())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        callBack.onStart();
                    }

                    @Override
                    public void onCompleted() {
                        callBack.compelete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        JLog.e(e);
                        callBack.fail(e);
                    }

                    @Override
                    public void onNext(String message) {
                        callBack.success(message);
                    }
                });
    }
}
