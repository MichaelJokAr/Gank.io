package org.jokar.gankio.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

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
    public Context applicationContextProvides(){
        return mApplication.getApplicationContext();
    }
}
