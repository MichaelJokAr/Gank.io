package org.jokar.gankio.view.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.bumptech.glide.load.engine.cache.DiskCache;

import org.jokar.gankio.R;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.presenter.event.AboutPresenter;
import org.jokar.gankio.presenter.impl.AboutPresneterImpl;
import org.jokar.gankio.utils.JLog;
import org.jokar.gankio.view.activity.GankActivity;

import java.io.File;

/**
 * Created by JokAr on 2016/9/26.
 */

public class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    AboutPresenter mAboutPresenter;
    private File cacheFile;
    private Preference imageCache;
    private Preference github;
    private Preference csdn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_about);
        //图片缓存目录
        cacheFile = new File(getActivity().getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR);

        mAboutPresenter = new AboutPresneterImpl();

        Preference version = findPreference("currentVersion");
        imageCache = findPreference("imageCache");
        github = findPreference("github");
        csdn = findPreference("csdn");

        imageCache.setOnPreferenceClickListener(this);
        github.setOnPreferenceClickListener(this);
        csdn.setOnPreferenceClickListener(this);

        version.setSummary(getVersion());

        mAboutPresenter.showCache(getActivity(), cacheFile, imageCache);

    }

    private String getVersion() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = getActivity().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getActivity().getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            JLog.e(e);
        }
        return versionName;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {

            case "imageCache": {
                mAboutPresenter.clearCache(getActivity(), cacheFile, imageCache);
                break;
            }
            case "github": {
                DataEntities dataEntities = new DataEntities();
                dataEntities.setDesc("Github");
                dataEntities.setUrl("https://github.com/a1018875550");
                Intent intent = new Intent(getActivity(), GankActivity.class);
                intent.putExtra("dataEntities", dataEntities);
                startActivity(intent);
                break;
            }
            case "csdn": {
                DataEntities dataEntities = new DataEntities();
                dataEntities.setDesc("CSDN");
                dataEntities.setUrl("http://blog.csdn.net/a1018875550");
                Intent intent = new Intent(getActivity(), GankActivity.class);
                intent.putExtra("dataEntities", dataEntities);
                startActivity(intent);
                break;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        imageCache.setOnPreferenceClickListener(null);
        github.setOnPreferenceClickListener(null);
        csdn.setOnPreferenceClickListener(null);
    }
}
