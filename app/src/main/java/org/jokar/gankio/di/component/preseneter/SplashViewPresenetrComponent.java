package org.jokar.gankio.di.component.preseneter;

import org.jokar.gankio.di.module.models.SplashModelModule;
import org.jokar.gankio.di.module.view.SplashViewModule;
import org.jokar.gankio.di.component.config.CPComponent;
import org.jokar.gankio.view.activity.SplashViewActivity;

import dagger.Component;

/**
 * Created by JokAr on 16/9/10.
 */
@Component(dependencies = CPComponent.class,
            modules = {SplashViewModule.class, SplashModelModule.class})
public interface SplashViewPresenetrComponent {

    void inject(SplashViewActivity target);
}
