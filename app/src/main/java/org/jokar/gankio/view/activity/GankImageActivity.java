package org.jokar.gankio.view.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding2.view.RxMenuItem;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.jokar.gankio.R;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.utils.JToast;
import org.jokar.gankio.utils.NavigationbarUtil;
import org.jokar.gankio.view.listener.DownloadIntentService;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 福利图片
 * Created by JokAr on 2016/9/24.
 */
@RuntimePermissions
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
        //点击事件
        RxMenuItem.clicks(menu.getItem(0))
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    GankImageActivityPermissionsDispatcher.downloadWithCheck(this);
                });
        return super.onCreateOptionsMenu(menu);
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
     * 下载文件
     */
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void download() {
        Intent intent = new Intent(this, DownloadIntentService.class);
        intent.putExtra("url", mDataEntities.getUrl());
        startService(intent);
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void showRationaleForDownlaod(PermissionRequest request){
        new AlertDialog.Builder(this)
                .setTitle("请求权限")
                .setMessage("是否允许Gank.io获得存储空间权限?")
                .setPositiveButton("允许", (dialog, button) -> request.proceed())
                .setNegativeButton("拒绝", (dialog, button) -> request.cancel())
                .show();
    }


    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForCamera() {
        JToast.Toast(this,"很抱歉,您需要允许GanK.io获得存储空间权限才能下载");
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAskForCamera() {
        JToast.Toast(this,"很抱歉,您需要允许GanK.io获得存储空间权限才能下载,请到设置里配置权限");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GankImageActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
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
