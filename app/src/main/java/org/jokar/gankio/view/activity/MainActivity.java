package org.jokar.gankio.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import org.jokar.gankio.R;
import org.jokar.gankio.view.adapter.FragmentAdapter;
import org.jokar.gankio.view.fragment.APPFragment;
import org.jokar.gankio.view.fragment.AllFragment;
import org.jokar.gankio.view.fragment.AndroidFragment;
import org.jokar.gankio.view.fragment.ExpandResourcesFragment;
import org.jokar.gankio.view.fragment.FrontEndFragment;
import org.jokar.gankio.view.fragment.IOSFragment;
import org.jokar.gankio.view.fragment.RecommendFragment;
import org.jokar.gankio.view.fragment.VideoRestFragment;
import org.jokar.gankio.view.fragment.WelfareFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;


    private FragmentAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragments;
    // all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
    private List<String> types = Arrays.asList("all", "Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App");

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
        mPagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragments(new AllFragment(),"All");
        mPagerAdapter.addFragments(new AndroidFragment(),"Android");
        mPagerAdapter.addFragments(new IOSFragment(),"iOS");
        mPagerAdapter.addFragments(new VideoRestFragment(),"休息视频");
        mPagerAdapter.addFragments(new WelfareFragment(),"福利");
        mPagerAdapter.addFragments(new ExpandResourcesFragment(),"拓展资源");
        mPagerAdapter.addFragments(new FrontEndFragment(),"前端");
        mPagerAdapter.addFragments(new RecommendFragment(),"瞎推荐");
        mPagerAdapter.addFragments(new APPFragment(),"APP");

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(9);
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
