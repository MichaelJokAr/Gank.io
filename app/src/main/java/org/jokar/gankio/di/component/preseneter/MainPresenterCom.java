package org.jokar.gankio.di.component.preseneter;

import org.jokar.gankio.di.component.db.DailyGankDBCom;
import org.jokar.gankio.di.module.models.DailyGankModelModule;
import org.jokar.gankio.di.module.view.MainViewModule;
import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.view.activity.MainActivity;

import dagger.Component;

/**
 * Created by JokAr on 2016/9/25.
 */
@UserScope
@Component(dependencies = DailyGankDBCom.class,
            modules ={DailyGankModelModule.class, MainViewModule.class} )
public interface MainPresenterCom {

    void inject(MainActivity mainActivity);
}
