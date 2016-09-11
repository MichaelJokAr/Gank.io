package org.jokar.gankio.model.impl;


import com.trello.rxlifecycle.LifecycleTransformer;

import org.jokar.gankio.model.entities.SplashImage;
import org.jokar.gankio.model.event.SplashViewModel;
import org.jokar.gankio.model.network.api.SplashAPI;

import rx.Subscriber;

/**
 * Created by JokAr on 16/9/10.
 */
public class SplashViewModelImpl implements SplashViewModel {

    @Override
    public void getImage( LifecycleTransformer transformer,
                         Subscriber<SplashImage> subscriber) {

        new SplashAPI().getImage(subscriber,transformer);
    }
}
