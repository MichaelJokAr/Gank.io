package org.jokar.gankio.model.impl;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.di.component.network.DaggerSplashComponent;
import org.jokar.gankio.di.component.network.DaggerSplashNetComponent;
import org.jokar.gankio.di.component.network.SplashNetComponent;
import org.jokar.gankio.di.module.network.SplashModule;
import org.jokar.gankio.di.module.network.SplashNetModule;
import org.jokar.gankio.model.config.ConfigPreferences;
import org.jokar.gankio.model.entities.SplashImage;
import org.jokar.gankio.model.event.SplashViewModel;
import org.jokar.gankio.model.network.services.SplashService;
import org.jokar.gankio.utils.SchedulersUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceObserver;
import retrofit2.Retrofit;


import static org.jokar.gankio.utils.Preconditions.checkNotNull;

/**
 * Created by JokAr on 16/9/10.
 */
public class SplashViewModelImpl implements SplashViewModel {


    @Inject
    Retrofit mRetrofit;

    @Inject
    SplashService mSplashService;

    public SplashViewModelImpl() {
        SplashNetComponent splashNetComponent = DaggerSplashNetComponent.builder()
                .splashNetModule(new SplashNetModule())
                .build();

        DaggerSplashComponent.builder()
                .splashNetComponent(splashNetComponent)
                .splashModule(new SplashModule())
                .build()
                .inject(this);
    }

    @Override
    public void getImage(final ConfigPreferences configPreferences,
                         LifecycleTransformer lifecycleTransformer,
                         @NonNull final SplashLoadCallback callback) {
        checkNotNull(callback);
        checkNotNull(lifecycleTransformer);

        mSplashService.getImage()
                .compose(lifecycleTransformer)
                .compose(SchedulersUtil.applySchedulersIO())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceObserver<SplashImage>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getDataError(configPreferences, e, callback);
                    }

                    @Override
                    public void onNext(SplashImage splashImage) {
                        configPreferences.putString("splash", splashImage.getJsonString());
                        callback.loadSuccess(splashImage);
                    }
                });
    }

    private void getDataError(ConfigPreferences preferences, Throwable e, SplashLoadCallback callback) {

        String splash = preferences.getString("splash", "");
        if (TextUtils.isEmpty(splash)) {
            callback.loadError(e, null);
        } else {
            Gson gson = new Gson();
            SplashImage splashImage = gson.fromJson(splash, SplashImage.class);
            callback.loadError(e, splashImage);
        }


    }
}
