package org.jokar.gankio.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.ActivityEvent;

import org.jokar.gankio.R;
import org.jokar.gankio.db.DailyGankDB;
import org.jokar.gankio.di.component.db.DaggerDailyGankDBCom;
import org.jokar.gankio.di.component.db.DailyGankDBCom;
import org.jokar.gankio.di.component.preseneter.DaggerMainPresenterCom;
import org.jokar.gankio.di.module.db.DailyGankDBModule;
import org.jokar.gankio.di.module.models.DailyGankModelModule;
import org.jokar.gankio.di.module.view.MainViewModule;
import org.jokar.gankio.model.rxbus.RxBus;
import org.jokar.gankio.model.rxbus.event.MainToolbarEvent;
import org.jokar.gankio.presenter.impl.MainPresenterImpl;
import org.jokar.gankio.view.adapter.FragmentAdapter;
import org.jokar.gankio.view.fragment.GankioFragment;
import org.jokar.gankio.view.ui.MainView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;

    @Inject
    DailyGankDB mDailyGankDB;
    @Inject
    MainPresenterImpl mMainPresenter;

    private FragmentAdapter mPagerAdapter;
    // all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
//    private List<String> types = Arrays.asList("all", "Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化toolbar
        initToolbar();

        init();
    }

    private void init() {
        //初始化
        DailyGankDBCom gankDBCom = DaggerDailyGankDBCom.builder()
                .dailyGankDBModule(new DailyGankDBModule(this))
                .build();

        DaggerMainPresenterCom.builder()
                .dailyGankDBCom(gankDBCom)
                .mainViewModule(new MainViewModule(this))
                .dailyGankModelModule(new DailyGankModelModule())
                .build()
                .inject(this);

        mPagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        //添加fragment
        addFragmet();
        //设置tabLayout
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        viewPager.setAdapter(mPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (fab.getVisibility() != View.VISIBLE) {
                    //判断fab是否已隐藏,若隐藏则让其显示
                    animateIn(fab);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        RxView.clicks(toolbar).compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(aVoid -> { RxBus.getBus().send(new MainToolbarEvent());
        });
        //请求获取今日干货
        mMainPresenter.requestDailyGank(System.currentTimeMillis(),
                mDailyGankDB, bindUntilEvent(ActivityEvent.STOP));
    }

    /**
     * 添加fragment
     */
    private void addFragmet() {
        {
            GankioFragment gankioFragment = new GankioFragment();
            Bundle args = new Bundle();
            args.putString("type", "all");
            gankioFragment.setArguments(args);
            mPagerAdapter.addFragments(gankioFragment, "all");
        }
        {
            GankioFragment gankioFragment = new GankioFragment();
            Bundle args = new Bundle();
            args.putString("type", "Android");
            gankioFragment.setArguments(args);
            mPagerAdapter.addFragments(gankioFragment, "Android");
        }
        {
            GankioFragment gankioFragment = new GankioFragment();
            Bundle args = new Bundle();
            args.putString("type", "iOS");
            gankioFragment.setArguments(args);
            mPagerAdapter.addFragments(gankioFragment, "iOS");
        }
        {
            GankioFragment gankioFragment = new GankioFragment();
            Bundle args = new Bundle();
            args.putString("type", "休息视频");
            gankioFragment.setArguments(args);
            mPagerAdapter.addFragments(gankioFragment, "休息视频");
        }
        {
            GankioFragment gankioFragment = new GankioFragment();
            Bundle args = new Bundle();
            args.putString("type", "福利");
            gankioFragment.setArguments(args);
            mPagerAdapter.addFragments(gankioFragment, "福利");
        }
        {
            GankioFragment gankioFragment = new GankioFragment();
            Bundle args = new Bundle();
            args.putString("type", "拓展资源");
            gankioFragment.setArguments(args);
            mPagerAdapter.addFragments(gankioFragment, "拓展资源");
        }
        {
            GankioFragment gankioFragment = new GankioFragment();
            Bundle args = new Bundle();
            args.putString("type", "前端");
            gankioFragment.setArguments(args);
            mPagerAdapter.addFragments(gankioFragment, "前端");
        }
        {
            GankioFragment gankioFragment = new GankioFragment();
            Bundle args = new Bundle();
            args.putString("type", "瞎推荐");
            gankioFragment.setArguments(args);
            mPagerAdapter.addFragments(gankioFragment, "瞎推荐");
        }
        {
            GankioFragment gankioFragment = new GankioFragment();
            Bundle args = new Bundle();
            args.putString("type", "App");
            gankioFragment.setArguments(args);
            mPagerAdapter.addFragments(gankioFragment, "App");
        }
    }


    private void animateIn(FloatingActionButton fab) {

        fab.setVisibility(View.VISIBLE);
        ViewCompat.animate(fab)
                .translationY(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .withLayer()
                .setListener(null)
                .start();
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dailyGank: {
                Intent intent = new Intent(this, DailyGankActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.setting: {
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void addGank(View view) {
        Intent intent = new Intent(this, AddGankActivity.class);
        startActivity(intent);
    }

    @Override
    public void showDailyGank() {
        Snackbar.make(coordinator, "今日干货到啦!", Snackbar.LENGTH_LONG)
                .setAction("去看看", v -> {
                    Intent intent = new Intent(this, DailyGankActivity.class);
                    startActivity(intent);
                }).show();
    }
}
