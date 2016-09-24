package org.jokar.gankio.di.component.network;

import org.jokar.gankio.di.module.network.DailyGankModule;
import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.model.impl.DailyGankModelImpl;

import dagger.Component;

/**
 * Created by JokAr on 2016/9/24.
 */
@UserScope
@Component(dependencies = NetComponent.class, modules = DailyGankModule.class)
public interface DailyGankComponent {
    void inject(DailyGankModelImpl dailyGankModel);
}
