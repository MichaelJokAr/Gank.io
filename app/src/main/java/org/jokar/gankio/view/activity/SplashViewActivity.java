package org.jokar.gankio.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.jokar.gankio.R;
import org.jokar.gankio.di.component.config.DaggerCPComponent;
import org.jokar.gankio.di.component.preseneter.DaggerSplashViewPresenetrComponent;
import org.jokar.gankio.di.module.models.SplashModelModule;
import org.jokar.gankio.di.module.view.SplashViewModule;
import org.jokar.gankio.di.component.config.CPComponent;
import org.jokar.gankio.di.module.config.CPModule;
import org.jokar.gankio.model.config.ConfigPreferences;
import org.jokar.gankio.model.entities.SplashImage;
import org.jokar.gankio.presenter.impl.SplashViewPresenterImpl;
import org.jokar.gankio.utils.JLog;
import org.jokar.gankio.view.ui.SplashView;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JokAr on 16/9/10.
 */
public class SplashViewActivity extends BaseActivity implements SplashView {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.text)
    TextView text;

    @Inject
    SplashViewPresenterImpl mSplashViewPresenter;

    @Inject
    ConfigPreferences mConfigPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //初始化SplashViewPresenter
        CPComponent cpComponent = DaggerCPComponent.builder()
                .cPModule(new CPModule(this))
                .build();

        DaggerSplashViewPresenetrComponent.builder()
                .cPComponent(cpComponent)
                .splashModelModule(new SplashModelModule())
                .splashViewModule(new SplashViewModule(this))
                .build()
                .inject(this);

        mSplashViewPresenter.getImage(mConfigPreferences,bindUntilEvent(ActivityEvent.STOP));

    }

    @Override
    public void loadImage(SplashImage splashImage) {

        setData(splashImage);
    }

    private void setData(SplashImage splashImage) {
        Glide.with(this)
                .load(splashImage.getImg())
                .error(R.mipmap.splash)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        image.setImageDrawable(resource);
                        animation();
                        return true;
                    }
                })
                .into(image);
        if (!TextUtils.isEmpty(splashImage.getText())) {
            text.setText("@" + splashImage.getText());
        }
    }

    @Override
    public void getDataError(Throwable e, SplashImage splashImage) {
        JLog.e(e);
        setData(splashImage);
    }

    @Override
    public void getDataError(Throwable e) {
        JLog.e(e);
        animation();
    }

    private void animation(){
        //图片放大动画
        final ScaleAnimation animation = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(1500);
        animation.setFillAfter(true);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束进入MainActivity
                startActivity(new Intent(SplashViewActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        image.startAnimation(animation);
    }
}
