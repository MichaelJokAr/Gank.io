package org.jokar.gankio.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 普通的toast
 * Created by JokAr on 2016/1/6.
 */
public class JToast {


    /**
     * 普通toast
     *
     * @param content
     */
    public static void Toast(Context context, String content) {
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 可以设置位置toast
     *
     * @param content
     * @param gravity
     */
    public static void toastGravity(Context context, String content, int gravity) {
        Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    /**
     * 设置显示时间长短，
     *
     * @param content
     * @param duration
     */
    public static void Toast(Context context, String content, int duration) {
        Toast toast = Toast.makeText(context, content, duration);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
