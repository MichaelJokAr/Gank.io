package org.jokar.gankio.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import org.jokar.gankio.R;
import org.jokar.gankio.view.fragment.AboutFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JokAr on 2016/9/25.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fragment)
    FrameLayout fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        initToolbar(toolbar,"设置");
        getFragmentManager().beginTransaction().replace(R.id.fragment, new AboutFragment()).commit();
    }
}
