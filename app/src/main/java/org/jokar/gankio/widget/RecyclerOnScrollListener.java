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

    private RecyclerViewPositionHelper mHelper;
    private Context mContext;

    public RecyclerOnScrollListener(RecyclerView recyclerView, Context context) {

        mHelper = new RecyclerViewPositionHelper(recyclerView);
        mContext = context;
    }

//    @Override
//    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//        super.onScrollStateChanged(recyclerView, newState);
//        switch (newState) {
//            case SCROLL_STATE_SETTLING:
//            case SCROLL_STATE_IDLE: {
//                Glide.with(mContext).resumeRequests();
//                break;
//            }
//            case SCROLL_STATE_DRAGGING: {
//                Glide.with(mContext).pauseRequests();
//                break;
//            }
//        }
//    }

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

    public void setLoading(boolean loading) {
        this.loading = loading;
        if (loading)
            previousTotal = mHelper.getItemCount();
    }
}