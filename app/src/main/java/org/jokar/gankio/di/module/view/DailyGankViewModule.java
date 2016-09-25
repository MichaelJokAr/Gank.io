package org.jokar.gankio.di.module.view;

import org.jokar.gankio.view.ui.DailyGankView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 2016/9/25.
 */
@Module
public class DailyGankViewModule {

    private DailyGankView mDailyGankView;

    public DailyGankViewModule(DailyGankView dailyGankView) {
        mDailyGankView = dailyGankView;
    }

    @Provides
    public DailyGankView dailyGankViewProvider(){
        return mDailyGankView;
    }
}
