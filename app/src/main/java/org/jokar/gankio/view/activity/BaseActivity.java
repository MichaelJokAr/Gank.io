package org.jokar.gankio.view.activity;

import android.graphics.Color;
import android.support.v7.widget.Toolbar;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


/**
 * Created by JokAr on 16/9/9.
 */
public abstract class BaseActivity extends RxAppCompatActivity {


    public void initToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());
    }

}
