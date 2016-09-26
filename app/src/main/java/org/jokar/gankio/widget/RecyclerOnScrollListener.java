package org.jokar.gankio.widget;

import android.support.v7.widget.RecyclerView;

import org.jokar.gankio.utils.RecyclerViewPositionHelper;

/**
 * RecycleView上拉加载更多
 */
public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {


    private int previousTotal = 0;

    private boolean loading = true;

    int lastCompletelyVisiableItemPosition, visibleItemCount, totalItemCount;

    private int currentPage = 1;

    private RecyclerViewPositionHelper mHelper;

    public RecyclerOnScrollListener(RecyclerView recyclerView) {

        mHelper = new RecyclerViewPositionHelper(recyclerView);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mHelper.getItemCount();
        lastCompletelyVisiableItemPosition = mHelper.findLastCompletelyVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading
                && (visibleItemCount > 0)
                && (lastCompletelyVisiableItemPosition >= totalItemCount - 1)) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}