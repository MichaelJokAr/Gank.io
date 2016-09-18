package org.jokar.gankio.di.component.network;

import org.jokar.gankio.di.module.network.SearchModule;
import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.model.impl.SearchModelImpl;

import dagger.Component;

/**
 * Created by JokAr on 16/9/17.
 */
@UserScope
@Component(dependencies = NetComponent.class,modules = SearchModule.class)
public interface SearchComponent {
    void inject(SearchModelImpl searchModel);
}
