package org.jokar.gankio.presenter.event;

import android.app.Activity;
import android.content.Context;
import android.preference.Preference;

import java.io.File;

/**
 * Created by JokAr on 2016/9/26.
 */
public interface AboutPresenter {
    void showCache(Context context, File file, Preference tv_cache);

    void clearCache(Activity activity, File file, Preference tv_cache);
}
