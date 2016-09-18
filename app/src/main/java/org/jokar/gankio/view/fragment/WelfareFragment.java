package org.jokar.gankio.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jokar.gankio.R;
import org.jokar.gankio.widget.ErrorView;
import org.jokar.gankio.widget.LazzyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 福利
 * Created by JokAr on 16/9/17.
 */
public class WelfareFragment extends LazzyFragment {
    @BindView(R.id.errorView)
    ErrorView errorView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View getView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initViews(View view) {


    }
}
