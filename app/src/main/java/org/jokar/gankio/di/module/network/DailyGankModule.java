package org.jokar.gankio.di.module.network;

import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.model.network.services.DailyGankService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by JokAr on 2016/9/24.
 */
@Module
public class DailyGankModule {

    @UserScope
    @Provides
    public DailyGankService dailyGankServiceProvider(Retrofit retrofit){
        return retrofit.create(DailyGankService.class);
    }
}
