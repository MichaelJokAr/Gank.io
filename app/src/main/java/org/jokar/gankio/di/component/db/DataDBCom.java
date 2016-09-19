package org.jokar.gankio.di.component.db;

import org.jokar.gankio.db.DataDB;
import org.jokar.gankio.di.module.db.DataDBModule;
import org.jokar.gankio.di.scoped.DBScope;

import dagger.Component;

/**
 * Created by JokAr on 16/9/19.
 */
@DBScope
@Component(modules = DataDBModule.class)
public interface DataDBCom {
    DataDB dataDB();
}
