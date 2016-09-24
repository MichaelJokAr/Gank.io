package org.jokar.gankio.utils;

import android.view.View;

/**
 * Created by JokAr on 2016/9/24.
 */

public class NavigationbarUtil {

    private static final int FLAG_IMMERSIVE = View.SYSTEM_UI_FLAG_IMMERSIVE
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN;

    public static void enter(View decor)
    {

        SystemUiVisibilityUtil.addFlags(decor, FLAG_IMMERSIVE);
    }

    public static void exit(View decor)
    {

        SystemUiVisibilityUtil.clearFlags(decor, FLAG_IMMERSIVE);
    }
}
