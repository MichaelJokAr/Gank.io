package org.jokar.gankio.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.rxlifecycle2.components.support.RxFragment;


/**
 * Created by JokAr on 16/9/9.
 */
public abstract class LazzyFragment extends RxFragment {
    //控件是否已经初始化
    private boolean isCreateView = false;
    //是否已经加载过数据
    public boolean isLoadData = false;

    /**
     * 返回layoutView
     * @param inflater
     * @param container
     * @return
     */
    public abstract View getView(LayoutInflater inflater, ViewGroup container);

    /**
     * 初始化控件
     */
    public abstract void initViews(View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getView(inflater, container);
        initViews(view);
        isCreateView = true;
        return view;
    }


    //此方法在控件初始化前调用，所以不能在此方法中直接操作控件会出现空指针
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isCreateView && !isLoadData) {
            loadData();
        }

    }


    /**
     * 加载数据
     */
    public void loadData() {
        //如果没有加载过就加载，否则就不再加载了
        if (!isLoadData) {
            //加载数据操作
            isLoadData = true;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //第一个加载fragment会调用
        if (getUserVisibleHint())
            loadData();
    }
}
