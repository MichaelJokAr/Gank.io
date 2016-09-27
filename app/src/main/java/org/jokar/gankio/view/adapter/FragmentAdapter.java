package org.jokar.gankio.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by JokAr on 16/9/17.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;
    private ArrayList<String> titles;

    public FragmentManager fm;
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
        mFragments = new ArrayList<>();
        titles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    public FragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments,
                           ArrayList<String> mTitles) {
        super(fm);
        mFragments = fragments;
        titles = mTitles;

    }


    @Override
    public int getCount() {
        return mFragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragments(Fragment fragments,String titles) {
        mFragments.add(fragments);
        this.titles.add(titles);
    }

    public void addTitles(String titles) {
        this.titles.add(titles);
    }

    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
        fm.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        Fragment fragment = mFragments.get(position);
        fm.beginTransaction().hide(fragment).commit();
    }
}
