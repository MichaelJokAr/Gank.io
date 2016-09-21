package org.jokar.gankio.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.jokar.gankio.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JokAr on 16/9/21.
 */

public class AddGankActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.select_type)
    TextView selectType;
    @BindView(R.id.ed_desc)
    TextInputEditText edDesc;
    @BindView(R.id.layout_desc)
    TextInputLayout layoutDesc;
    @BindView(R.id.ed_url)
    TextInputEditText edUrl;
    @BindView(R.id.layout_url)
    TextInputLayout layoutUrl;
    @BindView(R.id.ed_who)
    TextInputEditText edWho;
    @BindView(R.id.layout_who)
    TextInputLayout layoutWho;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgank);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        initToolbar(toolbar, "提交干货");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addgank_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.submit:{
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
