package org.jokar.gankio.model.event;


import com.trello.rxlifecycle.LifecycleTransformer;

import org.jokar.gankio.model.entities.SplashImage;

import rx.Subscriber;

/**
 * Created by JokAr on 16/9/10.
 */
public interface SplashViewModel {

    void getImage(
                  LifecycleTransformer transformer,
                  Subscriber<SplashImage> subscriber);
}
