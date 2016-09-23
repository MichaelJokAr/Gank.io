package org.jokar.gankio.di.module.network;

import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.model.network.services.AddgankService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by JokAr on 2016/9/23.
 */
@Module
public class AddgankModule {

    @UserScope
    @Provides
    public AddgankService addGankProvider(Retrofit retrofit){
        return retrofit.create(AddgankService.class);
    }
}
