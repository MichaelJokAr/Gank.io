package org.jokar.gankio.view.adapter.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.R;
import org.jokar.gankio.utils.JLog;
import org.jokar.gankio.widget.RecyclerOnScrollListener;

import java.util.ArrayList;

/**
 * Created by JokAr on 2017/3/8.
 */

public abstract class LoadMoreAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public Context mContext;
    public LayoutInflater mInflater;
    /**
     * 数据
     */
    public ArrayList<T> dataList;
    /**
     * 是否显示加载更多
     */
    private boolean showLoadMore = true;
    /**
     * 加载更多type
     */
    public final int TYPE_FOOTVIEW = 100;
    public FootViewHolder mFootViewHolder;
    /**
     *
     */
    public LoadMoreAdapterItemClickListener itemClickListener;
    private RecyclerOnScrollListener onScrollListener;
    public LifecycleTransformer mTransformer;
    private RecyclerView mRecyclerView;

    private NotifyObserver mNotifyObserver;

    public LoadMoreAdapter(Context context,
                           ArrayList<T> dataList,
                           @NonNull LifecycleTransformer mTransformer) {
        this.mContext = context;
        this.dataList = dataList;
        mInflater = LayoutInflater.from(this.mContext);
        this.mTransformer = mTransformer;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        onScrollListener = new RecyclerOnScrollListener(mRecyclerView) {
            @Override
            public void onLoadMore(int currentPage) {
                if (itemClickListener != null)
                    itemClickListener.loadMore();
            }
        };
        mRecyclerView.addOnScrollListener(onScrollListener);
        onScrollListener.setLoading(false);
        mNotifyObserver = new NotifyObserver();
        registerAdapterDataObserver(mNotifyObserver);

        if (dataList != null) {
            if (dataList.size() <= 3) {
                setShowLoadMore(false);
            } else {
                setShowLoadMore(true);
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        JLog.d("loadMore: onDetachedFromRecyclerView");
        if (onScrollListener != null) {
            mRecyclerView.removeOnScrollListener(onScrollListener);
        }
        onScrollListener = null;
        if (mNotifyObserver != null) {
            unregisterAdapterDataObserver(mNotifyObserver);
        }
        mNotifyObserver = null;
        mRecyclerView = null;
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTVIEW) {
            mFootViewHolder = new FootViewHolder(mInflater.inflate(R.layout.item_footview, parent,
                    false), mContext);
            return mFootViewHolder;
        } else {
            return createViewholder(parent, viewType);
        }
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_FOOTVIEW) {
            RxView.clicks(mFootViewHolder.ll_foot)
                    .compose(mTransformer)
                    .subscribe(o -> {
                        if (itemClickListener != null) {
                            mFootViewHolder.showProgress();
                            itemClickListener.footViewClick();
                        }
                    });

        } else {
            bindHolder(viewHolder, position);
        }
    }


    protected abstract void bindHolder(BaseViewHolder holder, int position);

    protected abstract BaseViewHolder createViewholder(ViewGroup parent, int viewType);

    public void loadMore() {

    }


    public void setOnItemClickListener(LoadMoreAdapterItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return showLoadMore ? (dataList != null ? dataList.size() + 1 : 0) :
                (dataList != null ? dataList.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (showLoadMore) {
            if (position == getItemCount() - 1) {
                return TYPE_FOOTVIEW;
            } else {
                return getViewType(position);
            }
        }
        return getViewType(position);

    }

    public void destroy() {
        if (onScrollListener != null) {
            mRecyclerView.removeOnScrollListener(onScrollListener);
        }
    }

    public ArrayList<T> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<T> dataList) {
        this.dataList = dataList;
    }

    public abstract int getViewType(int position);

    /**
     * 设置footShow显示
     */
    public void footShowClickText() {
        if (mFootViewHolder != null) {
            mFootViewHolder.showClickText();
        }
    }

    /**
     * 显示-已加载全部数据
     */
    public void showNoData() {
        if (mFootViewHolder != null) {
            mFootViewHolder.setClickable(false);
            mFootViewHolder.showText("已加载全部数据");
            setCanLoading(false);
        }
    }

    /**
     * 显示loading
     */
    public void showLoading() {
        try {
            if (mFootViewHolder != null) {
                mFootViewHolder.setClickable(true);
                mFootViewHolder.showProgress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLoading(boolean loading) {
        showLoading();
        setCanLoading(loading);
    }


    /**
     * 是否需要加载更多
     *
     * @param loading
     */
    public void setCanLoading(boolean loading) {
        if (onScrollListener != null) {
            onScrollListener.setCanLoading(loading);
            onScrollListener.setLoading(!loading);
        }
    }

    /**
     * 设置是否需要显示加载更多
     *
     * @param showLoadMore
     */
    public void setShowLoadMore(boolean showLoadMore) {
        this.showLoadMore = showLoadMore;
        setCanLoading(showLoadMore);
    }

    public void footShowProgress() {
        if (mFootViewHolder != null) {
            mFootViewHolder.showProgress();
        }
    }


    /**
     * @param clickable
     */
    public void setFootClickable(boolean clickable) {
        if (mFootViewHolder != null) {
            mFootViewHolder.setClickable(clickable);
        }
    }

    class FootViewHolder extends BaseViewHolder {
        private LinearLayout ll_foot;
        private ProgressBar progressBar;
        private TextView tvLoad;

        public FootViewHolder(View itemView, Context context) {
            super(itemView, context, false);
            ll_foot = $(R.id.ll_foot);
            progressBar = $(R.id.progressBar);
            tvLoad = $(R.id.tvLoad);
        }

        public void setClickable(boolean clickable) {
            ll_foot.setClickable(clickable);
        }

        public void setTvLoadText(String text) {
            tvLoad.setText(text);
        }

        public void showProgress() {
            progressBar.setVisibility(View.VISIBLE);
            tvLoad.setText(mContext.getString(R.string.loading));
        }

        public void showClickText() {
            progressBar.setVisibility(View.GONE);
            tvLoad.setText(mContext.getString(R.string.reload));
        }

        public void showText(String text) {
            progressBar.setVisibility(View.GONE);
            setTvLoadText(text);
        }
    }

    class NotifyObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            if (onScrollListener != null) {
                onScrollListener.setLoading(false);
            }
            if (dataList != null) {
                if (dataList.size() <= 3) {
                    setShowLoadMore(false);
                } else {
                    setShowLoadMore(true);
                }
            }
        }
    }
}
