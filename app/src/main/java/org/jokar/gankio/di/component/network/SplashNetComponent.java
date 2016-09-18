package org.jokar.gankio.di.component.network;

import org.jokar.gankio.di.module.network.SplashNetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by JokAr on 16/9/18.
 */
@Singleton
@Component(modules = SplashNetModule.class)
public interface SplashNetComponent {
    Retrofit retrofit();
}
