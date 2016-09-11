package org.jokar.gankio.presenter.listener;

import org.jokar.gankio.model.entities.SplashImage;

/**
 * Created by JokAr on 16/9/10.
 */
public interface SplashViewListener {

    void getImageSuccess(SplashImage splashImage);

    void getImageError(Throwable e,SplashImage splashImage);
}
