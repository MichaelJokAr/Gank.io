package org.jokar.gankio.di.module.db;

import android.content.Context;

import org.jokar.gankio.db.DailyGankDB;
import org.jokar.gankio.di.scoped.DBScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 2016/9/25.
 */
@Module
public class DailyGankDBModule {

    private Context mContext;

    public DailyGankDBModule(Context context) {
        mContext = context;
    }

    @DBScope
    @Provides
    public DailyGankDB dailyGankDBProvider(){
        return new DailyGankDB(mContext);
    }
}
