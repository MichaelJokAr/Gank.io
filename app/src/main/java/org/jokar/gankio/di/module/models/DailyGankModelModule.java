package org.jokar.gankio.di.module.models;

import org.jokar.gankio.model.event.DailyGankModel;
import org.jokar.gankio.model.impl.DailyGankModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 2016/9/25.
 */
@Module
public class DailyGankModelModule {

    @Provides
    public DailyGankModel dailyGankModel(){
        return new DailyGankModelImpl();
    }
}
