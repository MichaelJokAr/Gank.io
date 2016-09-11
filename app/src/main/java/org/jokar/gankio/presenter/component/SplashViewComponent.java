package org.jokar.gankio.presenter.component;

import org.jokar.gankio.presenter.module.SplashModelModule;
import org.jokar.gankio.presenter.module.SplashViewModule;
import org.jokar.gankio.view.activity.SplashViewActivity;

import dagger.Component;

/**
 * Created by JokAr on 16/9/10.
 */
@Component(modules = {SplashViewModule.class, SplashModelModule.class})
public interface SplashViewComponent {

    void inject(SplashViewActivity target);
}
