package org.jokar.gankio.app;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 16/9/16.
 */
@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    public Application applicationProvides(){
        return mApplication;
    }
}
