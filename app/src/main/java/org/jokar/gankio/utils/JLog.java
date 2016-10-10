package org.jokar.gankio.utils;

import android.util.Log;

import org.jokar.gankio.BuildConfig;

/**
 * Created by JokAr on 16/9/11.
 */
public class JLog {
    private static final String TAG = "Gank.io";

    public static void d(String value){
        try {
            if(BuildConfig.LOG_DEBUG)
                Log.d(TAG, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(int value){
        try {
            if(BuildConfig.LOG_DEBUG)
                Log.d(TAG, value+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(long value){
        try {
            if(BuildConfig.LOG_DEBUG)
                Log.d(TAG, value+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void e(String value){
        try {
            if(BuildConfig.LOG_DEBUG)
                Log.e(TAG, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void e(Throwable value){
        try {
            if(BuildConfig.LOG_DEBUG) {
                value.printStackTrace();
                Log.e(TAG, value.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(boolean value) {
        try {
            if(BuildConfig.LOG_DEBUG)
                Log.d(TAG, value+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
