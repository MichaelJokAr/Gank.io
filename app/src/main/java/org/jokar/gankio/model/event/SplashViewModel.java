package org.jokar.gankio.model.event;



import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.model.config.ConfigPreferences;
import org.jokar.gankio.model.entities.SplashImage;

/**
 * Created by JokAr on 16/9/10.
 */
public interface SplashViewModel {

     interface SplashLoadCallback{
        void loadSuccess(SplashImage splashView);
        void loadError(Throwable e,SplashImage splashView);
    }

    void getImage(ConfigPreferences configPreferences,
                  LifecycleTransformer transformer,
                  SplashLoadCallback callback);
}
