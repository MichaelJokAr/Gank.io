package org.jokar.gankio.utils;

import android.util.Log;

import org.jokar.gankio.BuildConfig;

/**
 * Created by JokAr on 16/9/11.
 */
public class JLog {
    private static final String TAG = "Gank.io";

    public static void d(String value){
        if(BuildConfig.LOG_DEBUG)
            Log.d(TAG, value);
    }

    public static void d(int value){
        if(BuildConfig.LOG_DEBUG)
            Log.d(TAG, value+"");
    }

    public static void d(long value){
        if(BuildConfig.LOG_DEBUG)
            Log.d(TAG, value+"");
    }

    public static void e(String value){
        if(BuildConfig.LOG_DEBUG)
            Log.e(TAG, value);
    }

    public static void e(Throwable value){
        if(BuildConfig.LOG_DEBUG) {
            value.printStackTrace();
            Log.e(TAG, value.getMessage());
        }
    }

    public static void d(boolean value) {
        if(BuildConfig.LOG_DEBUG)
            Log.d(TAG, value+"");
    }
}
