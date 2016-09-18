package org.jokar.gankio.di.component.preseneter;

import org.jokar.gankio.di.component.app.SearchDBComponent;
import org.jokar.gankio.di.module.models.SearchModelModule;
import org.jokar.gankio.di.module.view.FragmentViewModule;
import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.view.fragment.AndroidFragment;

import dagger.Component;

/**
 * Created by JokAr on 16/9/18.
 */
@UserScope
@Component(dependencies = SearchDBComponent.class,
        modules = {SearchModelModule.class, FragmentViewModule.class})
public interface SearchPresenterCom {

    void inject(AndroidFragment androidFragment);
}
