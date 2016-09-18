package org.jokar.gankio.di.component.network;

import org.jokar.gankio.di.module.network.SplashModule;
import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.model.impl.SplashViewModelImpl;

import dagger.Component;


/**
 * Created by JokAr on 16/9/17.
 */
@UserScope
@Component(dependencies = SplashNetComponent.class,modules = SplashModule.class)
public interface SplashComponent {

    void inject(SplashViewModelImpl splashAPI);

}
