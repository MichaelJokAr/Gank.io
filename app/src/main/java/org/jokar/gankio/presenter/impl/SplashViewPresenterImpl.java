package org.jokar.gankio.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.trello.rxlifecycle.LifecycleTransformer;

import org.jokar.gankio.model.config.ConfigPreferences;
import org.jokar.gankio.model.entities.SplashImage;
import org.jokar.gankio.model.event.SplashViewModel;
import org.jokar.gankio.model.impl.SplashViewModelImpl;
import org.jokar.gankio.presenter.event.SplashViewPresenter;
import org.jokar.gankio.utils.JLog;
import org.jokar.gankio.view.ui.SplashView;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by JokAr on 16/9/10.
 */
public class SplashViewPresenterImpl implements SplashViewPresenter {
    private SplashView mSplashView;
    private SplashViewModel mSplashViewModel;

    @Inject
    public SplashViewPresenterImpl(SplashView splashView,SplashViewModel splashViewModel) {
        mSplashView = splashView;
        mSplashViewModel = splashViewModel;
    }

    @Override
    public void getImage(final Context context, LifecycleTransformer transformer) {
        final ConfigPreferences preferences = ConfigPreferences.getInstance(context);
        mSplashViewModel.getImage(transformer, new Subscriber<SplashImage>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getDataError(preferences,e);
            }

            @Override
            public void onNext(SplashImage splashImage) {
                mSplashView.loadImage(splashImage);
                preferences.putString("splash",splashImage.getJsonString());
//                JLog.d(splashImage.getJsonString());
            }
        });
    }

    private void getDataError( ConfigPreferences preferences,Throwable e) {

        String splash = preferences.getString("splash", "");
        if(TextUtils.isEmpty(splash)){
            mSplashView.getDataError(e);
        }else {
            Gson gson = new Gson();
            SplashImage splashImage = gson.fromJson(splash, SplashImage.class);
            mSplashView.getDataError(e,splashImage);
        }
    }
}
