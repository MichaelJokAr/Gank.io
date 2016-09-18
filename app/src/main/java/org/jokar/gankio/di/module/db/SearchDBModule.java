package org.jokar.gankio.di.module.db;

import android.content.Context;

import org.jokar.gankio.db.SearchDB;
import org.jokar.gankio.di.scoped.DBScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 16/9/18.
 */
@Module
public class SearchDBModule {

    private Context mContext;

    public SearchDBModule(Context context) {
        mContext = context;
    }

    @DBScope
    @Provides
    public SearchDB searchDBProvider(){
        return new SearchDB(mContext);
    }
}
