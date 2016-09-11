package org.jokar.gankio.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


/**
 * Created by JokAr on 16/9/9.
 */
public abstract class BaseActivity extends RxAppCompatActivity {



    public void initToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
