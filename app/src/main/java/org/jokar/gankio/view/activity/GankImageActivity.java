package org.jokar.gankio.view.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding.view.RxView;

import org.jokar.gankio.R;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.utils.NavigationbarUtil;
import org.jokar.gankio.view.listener.DownloadIntentService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 福利图片
 * Created by JokAr on 2016/9/24.
 */
public class GankImageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.photoView)
    ImageView photoView;

    private DataEntities mDataEntities;

    private boolean isHide = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gankimage);
        ButterKnife.bind(this);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gankimage_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.download) {
            download();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 下载文件
     */
    private void download() {
        Intent intent = new Intent(this, DownloadIntentService.class);
        intent.putExtra("url", mDataEntities.getUrl());
        startService(intent);
    }


    private void init() {
        initToolbar(toolbar, "");
        toolbar.setBackgroundColor(Color.parseColor("#dd000000"));
        mDataEntities = getIntent().getParcelableExtra("dataEntities");
        toolbar.setTitle(mDataEntities.getDesc());

        Glide.with(this)
                .load(mDataEntities.getUrl())
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(photoView);


        RxView.clicks(photoView).subscribe(aVoid -> hideBar());

    }

    /**
     * 隐藏toolbar和navBar
     */
    private void hideBar() {
        if (isHide) {//显示
            NavigationbarUtil.exit(getWindow().getDecorView());
            toolbar.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(2))
                    .start();
            isHide = false;
        } else {//隐藏
            NavigationbarUtil.enter(getWindow().getDecorView());
            toolbar.animate()
                    .translationY(-toolbar.getHeight())
                    .setInterpolator(new DecelerateInterpolator(2))
                    .start();

            isHide = true;
        }
    }
}
