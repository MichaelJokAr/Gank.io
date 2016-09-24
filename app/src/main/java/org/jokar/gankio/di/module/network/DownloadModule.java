package org.jokar.gankio.di.module.network;

import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.model.network.services.DownloadService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by JokAr on 2016/9/24.
 */
@Module
public class DownloadModule {

    @UserScope
    @Provides
    public DownloadService downloadProvider(Retrofit retrofit) {
        return retrofit.create(DownloadService.class);
    }
}
