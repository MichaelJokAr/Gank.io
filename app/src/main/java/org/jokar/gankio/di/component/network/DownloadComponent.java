package org.jokar.gankio.di.component.network;

import org.jokar.gankio.di.module.network.DownloadModule;
import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.model.impl.DownloadModelImpl;

import dagger.Component;

/**
 * Created by JokAr on 2016/9/24.
 */
@UserScope
@Component(dependencies = DownNetComponent.class,modules = DownloadModule.class)
public interface DownloadComponent {
    void inject(DownloadModelImpl downloadModel);
}
