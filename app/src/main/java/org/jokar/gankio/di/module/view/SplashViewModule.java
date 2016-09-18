package org.jokar.gankio.di.module.view;

import org.jokar.gankio.view.ui.SplashView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 16/9/10.
 */
@Module
public class SplashViewModule {
    private SplashView mSplashView;

    public SplashViewModule(SplashView splashView) {
        mSplashView = splashView;
    }

    @Provides
    public SplashView providerSplashView(){
        return mSplashView;
    }


}
