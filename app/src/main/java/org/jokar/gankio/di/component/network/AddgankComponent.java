package org.jokar.gankio.di.component.network;

import org.jokar.gankio.di.module.network.AddgankModule;
import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.model.impl.AddGankModelImpl;

import dagger.Component;

/**
 * Created by JokAr on 2016/9/23.
 */
@UserScope
@Component(dependencies = NetComponent.class,modules = AddgankModule.class)
public interface AddgankComponent {
    void inject(AddGankModelImpl addGankModel);
}
