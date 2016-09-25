package org.jokar.gankio.di.component.db;

import org.jokar.gankio.db.DailyGankDB;
import org.jokar.gankio.di.module.db.DailyGankDBModule;
import org.jokar.gankio.di.scoped.DBScope;

import dagger.Component;

/**
 * Created by JokAr on 2016/9/25.
 */
@DBScope
@Component(modules = DailyGankDBModule.class)
public interface DailyGankDBCom {

    DailyGankDB dailyGankDB();
}
