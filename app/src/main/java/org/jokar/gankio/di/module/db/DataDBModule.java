package org.jokar.gankio.di.module.db;

import android.content.Context;

import org.jokar.gankio.db.DataDB;
import org.jokar.gankio.di.scoped.DBScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 16/9/19.
 */
@Module
public class DataDBModule {

    private Context mContext;

    public DataDBModule(Context context) {
        mContext = context;
    }

    @DBScope
    @Provides
    public DataDB dataDBProvider(){
        return new DataDB(mContext);
    }
}
