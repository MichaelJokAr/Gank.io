package org.jokar.gankio.view.ui;

import org.jokar.gankio.model.entities.SplashImage;

/**
 * Created by JokAr on 16/9/9.
 */
public interface SplashView {

    void loadImage(SplashImage splashImage);

    void getDataError(Throwable e,SplashImage splashImage);
    void getDataError(Throwable e);
}
