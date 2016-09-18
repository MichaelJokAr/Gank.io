package org.jokar.gankio.di.module.config;

import android.content.Context;

import org.jokar.gankio.model.config.ConfigPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 16/9/16.
 */
@Module
public class CPModule {

    private Context mContext;

    public CPModule(Context context) {
        mContext = context;
    }

    @Provides
    public ConfigPreferences preferencesProvider(){
        return ConfigPreferences.getInstance(mContext);
    }
}
