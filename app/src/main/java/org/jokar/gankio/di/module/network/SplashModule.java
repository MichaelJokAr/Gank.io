package org.jokar.gankio.di.module.network;

import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.model.network.services.SplashService;


import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by JokAr on 16/9/17.
 */
@Module
public class SplashModule {

    @UserScope
    @Provides
    public SplashService splashServiceProvider(Retrofit retrofit) {
        return retrofit.create(SplashService.class);
    }
}
