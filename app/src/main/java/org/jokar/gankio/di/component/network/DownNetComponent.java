package org.jokar.gankio.di.component.network;

import org.jokar.gankio.di.module.network.DownNetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by JokAr on 2016/9/24.
 */
@Singleton
@Component(modules = DownNetModule.class)
public interface DownNetComponent {

    Retrofit retrofit();
}
