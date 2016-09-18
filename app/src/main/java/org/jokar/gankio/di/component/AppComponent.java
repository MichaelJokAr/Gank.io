package org.jokar.gankio.di.component;

import android.app.Application;
import android.content.Context;

import org.jokar.gankio.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by JokAr on 16/9/18.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    Context context();

}
