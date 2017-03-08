package org.jokar.gankio.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.jakewharton.rxbinding.view.RxView;
//import com.trello.rxlifecycle.LifecycleTransformer;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.R;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.model.entities.GankDayEntities;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by JokAr on 2016/9/25.
 */

public class DailyGankAdapter extends RecyclerView.Adapter<DailyGankAdapter.DailyGankViewHolder> {

    private Context mContext;
    private GankDayEntities mGankDayEntities;
    private LayoutInflater mInflater;
    private ItemClickListener mListener;
    private List<DataEntities> mDataEntitiesList;
    private LifecycleTransformer mLifecycleTransformer;

    public DailyGankAdapter(Context context, GankDayEntities gankDayEntities,
                            LifecycleTransformer<Object> objectLifecycleTransformer) {
        mContext = context;
        mGankDayEntities = gankDayEntities;
        mLifecycleTransformer = objectLifecycleTransformer;
        mInflater = LayoutInflater.from(mContext);


    }

    @Override
    public DailyGankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new TitleViewHolder(mInflater.inflate(R.layout.item_dailygank1, parent, false));
            case 1:
                return new DescViewHolder(mInflater.inflate(R.layout.item_dailygank2, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(DailyGankViewHolder holder, int position) {
        mDataEntitiesList = mGankDayEntities.getDataEntitiesList();

        switch (getItemViewType(position)) {
            case 0: {
                DataEntities dataEntities = mDataEntitiesList.get(getPosition(position + 1));
                TitleViewHolder viewHolder = (TitleViewHolder) holder;
                viewHolder.type.setText(dataEntities.getType());
                break;
            }
            case 1: {
                DataEntities dataEntities = mDataEntitiesList.get(getPosition(position));
                DescViewHolder viewHolder = (DescViewHolder) holder;
                viewHolder.tv_desc.setText(dataEntities.getDesc());
                //点击事件
                RxView.clicks(viewHolder.ll_gank)
                        .compose(mLifecycleTransformer)
                        .throttleFirst(1L, TimeUnit.SECONDS).subscribe(aVoid -> {
                    if (mListener != null) {
                        mListener.itemClick(dataEntities);
                    }
                });
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mGankDayEntities.getCount();
    }


    public void setListener(ItemClickListener listener) {
        mListener = listener;
    }

    private int getPosition(int position) {
        List<Integer> titleIndex = mGankDayEntities.getTitleIndex();
        if (titleIndex.size() == 1) {
            return position - 1;
        } else if (position > titleIndex.get(titleIndex.size() - 1)) {
            return position - titleIndex.size();
        } else {

            return position - binarySearch(titleIndex, position, 0, titleIndex.size() - 1);
        }
    }

    /**
     * 二分递归查找
     *
     * @param dataset
     * @param data
     * @param beginIndex
     * @param endIndex
     * @return 返回data在集合里最接近的大数小标
     */
    private int binarySearch(List<Integer> dataset, int data,
                             int beginIndex, int endIndex) {

        int midIndex = (beginIndex + endIndex) / 2;

        if (data < dataset.get(midIndex)) {
            if (data > dataset.get(midIndex - 1))
                return midIndex;
            return binarySearch(dataset, data, beginIndex, midIndex - 1);
        } else if (data > dataset.get(midIndex)) {
            return binarySearch(dataset, data, midIndex + 1, endIndex);
        } else {
            return midIndex;
        }
    }

    @Override
    public int getItemViewType(int position) {
        List<Integer> titleIndex = mGankDayEntities.getTitleIndex();

        if (titleIndex.contains(position)) {

            return 0;
        } else {
            return 1;
        }
    }

    class DailyGankViewHolder extends RecyclerView.ViewHolder {

        public DailyGankViewHolder(View itemView) {
            super(itemView);
        }
    }

    class TitleViewHolder extends DailyGankViewHolder {
        private TextView type;

        public TitleViewHolder(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.tv_type);
        }
    }

    class DescViewHolder extends DailyGankViewHolder {

        LinearLayout ll_gank;
        TextView tv_desc;

        public DescViewHolder(View itemView) {
            super(itemView);
            ll_gank = (LinearLayout) itemView.findViewById(R.id.ll_gank);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
        }
    }

    public interface ItemClickListener {

        void itemClick(DataEntities dataEntities);
    }
}
