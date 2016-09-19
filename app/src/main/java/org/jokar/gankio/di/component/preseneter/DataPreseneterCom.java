package org.jokar.gankio.di.component.preseneter;

import org.jokar.gankio.di.component.db.DataDBCom;
import org.jokar.gankio.di.module.models.DataModelModule;
import org.jokar.gankio.di.module.view.FragmentViewModule;
import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.view.fragment.GankioFragment;

import dagger.Component;

/**
 * Created by JokAr on 16/9/19.
 */
@UserScope
@Component(dependencies = DataDBCom.class,
        modules = {DataModelModule.class, FragmentViewModule.class})
public interface DataPreseneterCom {

    void inject(GankioFragment fragment);
}
