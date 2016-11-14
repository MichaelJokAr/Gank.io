package org.jokar.gankio.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tencent.bugly.crashreport.CrashReport;

import org.jokar.gankio.BuildConfig;
import org.jokar.gankio.di.component.network.DaggerNetComponent;
import org.jokar.gankio.di.component.network.NetComponent;
import org.jokar.gankio.di.module.network.NetModule;
import org.jokar.gankio.utils.image.Imageloader;


/**
 * Created by JokAr on 16/9/9.
 */
public class GankioApplication extends Application {

    private static NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bugly
        initBugly();
        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();
    }

    /**
     * 初始化bugly
     */
    private void initBugly() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(String.valueOf(getVersionCode(getApplicationContext())));
        CrashReport.initCrashReport(getApplicationContext(), "eba929c175", BuildConfig.DEBUG, strategy);
    }


    public static NetComponent getNetComponent() {
        return mNetComponent;
    }

    /**
     * 获取versionCode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {// 获取版本号(内部识别号)
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Imageloader.clearCache(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level){
            case TRIM_MEMORY_RUNNING_MODERATE:
                Imageloader.clearCache(this);
                break;
        }
    }
}
