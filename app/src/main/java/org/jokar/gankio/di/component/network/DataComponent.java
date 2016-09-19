package org.jokar.gankio.di.component.network;

import org.jokar.gankio.di.module.network.DataModule;
import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.model.impl.DataModelImpl;

import dagger.Component;

/**
 * Created by JokAr on 16/9/19.
 */
@UserScope
@Component(dependencies = NetComponent.class, modules = DataModule.class)
public interface DataComponent {

    void inject(DataModelImpl dataModel);
}
