package org.jokar.gankio.app;

import android.app.Application;

import org.jokar.gankio.di.component.network.DaggerNetComponent;
import org.jokar.gankio.di.component.network.NetComponent;
import org.jokar.gankio.di.module.network.NetModule;


/**
 * Created by JokAr on 16/9/9.
 */
public class GankioApplication extends Application {

    private static NetComponent mNetComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();
    }

    public static NetComponent getNetComponent() {
        return mNetComponent;
    }
}
