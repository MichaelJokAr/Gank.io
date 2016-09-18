package org.jokar.gankio.model.config;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Inject;

/**
 * singlton
 * Created by JokAr on 16/9/11.
 */
public class ConfigPreferences {
    private SharedPreferences mSharedPreferences;
    private String name = "config";
    private static final AtomicReference<ConfigPreferences> instance = new AtomicReference<>();

    @Inject
    public ConfigPreferences(Context context) {
       mSharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
    }

    /**
     * singlton
     * @param context
     * @return
     */
    public static ConfigPreferences getInstance(Context context){
        for(;;){
            ConfigPreferences configPreferences = instance.get();
            if(configPreferences != null)
                return configPreferences;
            configPreferences = new ConfigPreferences(context.getApplicationContext());
            if(instance.compareAndSet(null,configPreferences))
                return configPreferences;
        }
    }

    public void putString(String key,String value){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(key,value);
        edit.commit();

    }

    public String getString(String key,String defaultValue){

        return mSharedPreferences.getString(key,defaultValue);

    }

    public void putBoolen(String key,boolean value){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public boolean getBoolen(String key,boolean defaultValue){

        return mSharedPreferences.getBoolean(key,defaultValue);
    }
}
