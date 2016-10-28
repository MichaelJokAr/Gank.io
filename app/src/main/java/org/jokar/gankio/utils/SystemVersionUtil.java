package org.jokar.gankio.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by JokAr on 2016/10/18.
 */

public class SystemVersionUtil {

    /**
     * 获取versionCode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        // 获取版本号(内部识别号)
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
            return 0;
        }
    }
}
