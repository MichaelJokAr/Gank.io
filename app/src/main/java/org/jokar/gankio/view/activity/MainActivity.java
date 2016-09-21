package org.jokar.gankio.view.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.trello.rxlifecycle.android.ActivityEvent;

import org.jokar.gankio.R;
import org.jokar.gankio.model.rxbus.RxBus;
import org.jokar.gankio.model.rxbus.event.MainViewPagerEvent;
import org.jokar.gankio.view.adapter.FragmentAdapter;
import org.jokar.gankio.view.fragment.GankioFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;


    private FragmentAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragments;
    // all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
//    private List<String> types = Arrays.asList("all", "Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App");

    private ArrayList<String> viewPageOffscreenCount;

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
        mFragments = new ArrayList<>();
        viewPageOffscreenCount = new ArrayList<>();
        viewPageOffscreenCount.add("All");
        mPagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        addFragmet();

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(viewPageOffscreenCount.size());

        RxBus.getBus().toMainThreadObserverable(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(event -> {
                    //设置viewpager缓存
                    setOffscreenPageLimit((MainViewPagerEvent) event);
                });
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

    /**
     * 设置viewpager缓存
     *
     * @param event
     */
    private void setOffscreenPageLimit(MainViewPagerEvent event) {
        String type = event.getType();
        if (!viewPageOffscreenCount.contains(type)) {
            viewPageOffscreenCount.add(type);
            viewPager.setOffscreenPageLimit(viewPageOffscreenCount.size());

        }
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

    @OnClick(R.id.fab)
    public void addGank(View view) {
        Intent intent = new Intent(this, AddGankActivity.class);
        startActivity(intent);
    }
}
