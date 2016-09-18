package org.jokar.gankio.di.component.network;

import org.jokar.gankio.di.module.network.NetModule;


import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by JokAr on 16/9/16.
 */
@Singleton
@Component(modules = NetModule.class)
public interface NetComponent {

    Retrofit retrofit();
}
