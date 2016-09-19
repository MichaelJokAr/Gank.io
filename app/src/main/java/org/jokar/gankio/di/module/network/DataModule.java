package org.jokar.gankio.di.module.network;

import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.model.network.services.DataService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by JokAr on 16/9/19.
 */
@Module
public class DataModule {

    @UserScope
    @Provides
    public DataService dataProvider(Retrofit retrofit){
        return retrofit.create(DataService.class);
    }
}
