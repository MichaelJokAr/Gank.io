package org.jokar.gankio.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jokar.gankio.utils.RecyclerViewPositionHelper;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * RecycleView上拉加载更多
 */
public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {


    private int previousTotal = 0;

    private boolean loading = true;

    int lastCompletelyVisiableItemPosition, visibleItemCount, totalItemCount;

    private int currentPage = 1;
    private boolean shouldLoading = true;

    private RecyclerViewPositionHelper mHelper;

    public RecyclerOnScrollListener(RecyclerView recyclerView) {

        mHelper = new RecyclerViewPositionHelper(recyclerView);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        super.onScrolled(recyclerView, dx, dy);
        if (shouldLoading) {
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
    }

    public void setCanLoading(boolean loading) {
        shouldLoading = loading;
        if (true == shouldLoading)
            previousTotal = mHelper.getItemCount();
    }

    public void setLoading(boolean loading){
        this.loading = loading;
        if (true == loading)
            previousTotal = mHelper.getItemCount();
    }
    public abstract void onLoadMore(int currentPage);
}