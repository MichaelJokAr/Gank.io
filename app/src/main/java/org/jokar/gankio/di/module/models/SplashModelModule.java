package org.jokar.gankio.di.module.models;

import org.jokar.gankio.model.event.SplashViewModel;
import org.jokar.gankio.model.impl.SplashViewModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 16/9/11.
 */
@Module
public class SplashModelModule {

    @Provides
    public SplashViewModel splashModelProvider(){
        return new SplashViewModelImpl();
    }
}
