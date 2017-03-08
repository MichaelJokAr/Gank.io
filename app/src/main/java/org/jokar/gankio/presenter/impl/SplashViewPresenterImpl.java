package org.jokar.gankio.presenter.impl;



import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.model.config.ConfigPreferences;
import org.jokar.gankio.model.entities.SplashImage;
import org.jokar.gankio.model.event.SplashViewModel;
import org.jokar.gankio.presenter.event.SplashViewPresenter;
import org.jokar.gankio.view.ui.SplashView;

import javax.inject.Inject;


/**
 * Created by JokAr on 16/9/10.
 */
public class SplashViewPresenterImpl implements SplashViewPresenter {
    private SplashView mSplashView;
    private SplashViewModel mSplashViewModel;


    @Inject
    public SplashViewPresenterImpl(SplashView splashView, SplashViewModel splashViewModel) {
        mSplashView = splashView;
        mSplashViewModel = splashViewModel;
    }

    @Override
    public void getImage(final ConfigPreferences preferences, LifecycleTransformer transformer) {
        mSplashViewModel.getImage(preferences,
                transformer,
                new SplashViewModel.SplashLoadCallback() {
                    @Override
                    public void loadSuccess(SplashImage splashImage) {
                        mSplashView.loadImage(splashImage);
                    }

                    @Override
                    public void loadError(Throwable e, SplashImage splashView) {
                        if (splashView != null)
                            mSplashView.getDataError(e, splashView);
                        else
                            mSplashView.getDataError(e);
                    }
                });

    }


}
