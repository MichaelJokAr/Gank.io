package org.jokar.gankio.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.R;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.model.entities.ImageSize;
import org.jokar.gankio.utils.image.BitmapSizeTranscoder;
import org.jokar.gankio.utils.image.Imageloader;
import org.jokar.gankio.view.adapter.base.BaseViewHolder;
import org.jokar.gankio.view.adapter.base.LoadMoreAdapter;
import org.jokar.gankio.widget.RelativeTimeTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Adapter for GankioFragment
 * Created by JokAr on 16/9/18.
 */
public class GankioFragmentAdapter extends LoadMoreAdapter<DataEntities> {

    private Context mContext;
    private LayoutInflater mInflater;
    private SimpleDateFormat sdf;
    private String mType;
    private ItemClickListener mClickListener;
    private LifecycleTransformer lifecycleTransformer;

    public GankioFragmentAdapter(Context context,
                                 String type,
                                 ArrayList<DataEntities> mSearchEntitiesList,
                                 LifecycleTransformer<Object> objectLifecycleTransformer) {
        super(context, mSearchEntitiesList, objectLifecycleTransformer);
        mContext = context;
        mType = type;
        lifecycleTransformer = objectLifecycleTransformer;
        mInflater = LayoutInflater.from(mContext);
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }


    @Override
    protected void bindHolder(BaseViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case 0: { //非图片类型
                DataEntities entities = dataList.get(position);

                ViewHolder holder = (ViewHolder) viewHolder;
                holder.setWho(entities.getWho());
                holder.tvDesc.setText(entities.getDesc());
                try {
                    Date date = sdf.parse(entities.getPublishedAt());
                    holder.tvTime.setRefreshTime(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //设置点击事件
                RxView.clicks(holder.ll_continear)
                        .compose(lifecycleTransformer)
                        .throttleFirst(1L, TimeUnit.SECONDS).subscribe(aVoid -> {
                    if (mClickListener != null) {
                        mClickListener.itemClick(entities);
                    }
                });
                break;
            }
            case 3: {
                DataEntities entities = dataList.get(position);

                ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;
                imageViewHolder.setWho(entities.getWho());
                Imageloader.loadImageCenterCrop(mContext, entities.getUrl(),
                        R.mipmap.default_image,
                        imageViewHolder.image);
                //设置点击事件
                RxView.clicks(imageViewHolder.ll_continear)
                        .compose(lifecycleTransformer)
                        .throttleFirst(1L, TimeUnit.SECONDS).subscribe(aVoid -> {
                    if (mClickListener != null) {
                        mClickListener.imageItemClick(entities, imageViewHolder.image);
                    }
                });
                break;
            }
            case 1: { //图片类型
                DataEntities entities = dataList.get(position);

                ImageViewHolder imageViewHolder = (ImageViewHolder) viewHolder;

                imageViewHolder.loadImage(entities.getUrl(), mContext);
                //设置点击事件
                RxView.clicks(imageViewHolder.ll_continear)
                        .compose(lifecycleTransformer)
                        .throttleFirst(1L, TimeUnit.SECONDS).subscribe(aVoid -> {
                    if (mClickListener != null) {
                        mClickListener.imageItemClick(entities, imageViewHolder.image);
                    }
                });
                break;
            }
            case 2: { //all
                DataEntities entities = dataList.get(position);

                AllViewHolder allViewHolder = (AllViewHolder) viewHolder;
                allViewHolder.setWho(entities.getWho());
                allViewHolder.tvDesc.setText(entities.getDesc());
                allViewHolder.tvType.setText(entities.getType());
                try {
                    Date date = sdf.parse(entities.getPublishedAt());
                    allViewHolder.tvTime.setRefreshTime(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //设置点击事件

                RxView.clicks(allViewHolder.ll_continear)
                        .compose(lifecycleTransformer)
                        .throttleFirst(1L, TimeUnit.SECONDS).subscribe(aVoid -> {
                    if (mClickListener != null) {
                        mClickListener.itemClick(entities);
                    }
                });
                break;
            }


        }
    }

    @Override
    protected BaseViewHolder createViewholder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new ViewHolder(mInflater.inflate(R.layout.item_fragment, parent, false),
                        mContext);
            case 1:
                return new ImageViewHolder(mInflater.inflate(R.layout.item_imagefragment, parent,
                        false), mContext);
            case 2:
                return new AllViewHolder(mInflater.inflate(R.layout.item_gan_allfragment, parent,
                        false), mContext);
            case 3:
                return new ImageViewHolder(mInflater.inflate(R.layout.item_all_imagefragment, parent,
                        false), mContext);
        }
        return null;
    }


    @Override
    public int getViewType(int position) {
        DataEntities dataEntities = dataList.get(position);
        if (mType.equals("all")) {
            if (dataEntities.getType().equals("福利")) {
                return 3;
            } else {
                return 2;
            }

        } else if (dataEntities.getType().equals("福利")) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof ImageViewHolder) {
            ImageViewHolder viewHolder = (ImageViewHolder) holder;
            Glide.clear(viewHolder.image);
        }
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        mClickListener = listener;
    }

    public List<DataEntities> getData() {
        return dataList;
    }

    public void setData(List<DataEntities> data) {
        this.dataList = new ArrayList<>(data);
    }

    class ViewHolder extends BaseViewHolder {

        TextView tvDesc;
        RelativeTimeTextView tvTime;
        CardView cardView;
        TextView tvWho;
        LinearLayout ll_continear;

        ViewHolder(View view, Context context) {
            super(view, context, false);
            tvWho = $(R.id.tvWho);
            tvDesc = $(R.id.tvDesc);
            cardView = $(R.id.cardView);
            tvTime = $(R.id.tvTime);
            cardView = $(R.id.cardView);
            ll_continear = $(R.id.ll_continear);
        }

        public void setWho(String who) {
            tvWho.setText(who);
        }
    }

    class ImageViewHolder extends BaseViewHolder {
        ImageView image;
        CardView cardView;
        View itemView;
        TextView tvWho;
        LinearLayout ll_continear;

        public ImageViewHolder(View itemView, Context context) {
            super(itemView, context, false);
            this.itemView = itemView;
            image = $(R.id.image);
            tvWho = $(R.id.tvWho);
            cardView = $(R.id.cardView);
            ll_continear = $(R.id.ll_continear);
        }

        public void loadImage(String url, Context context) {
            Imageloader.loadImage(context, url, R.mipmap.default_image, image);
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .transcode(new BitmapSizeTranscoder(), ImageSize.class)
                    .into(new SimpleTarget<ImageSize>() {
                        @Override
                        public void onResourceReady(ImageSize resource,
                                                    GlideAnimation<? super ImageSize> glideAnimation) {

                            StaggeredGridLayoutManager.LayoutParams layoutParams =
                                    (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
                            int height = resource.getHeight() / 2;

                            layoutParams.height = height;
                            itemView.setLayoutParams(layoutParams);
                        }
                    });
        }

        public void setWho(String who) {
            tvWho.setText(who);
        }
    }


    class AllViewHolder extends BaseViewHolder {

        TextView tvDesc;
        RelativeTimeTextView tvTime;
        TextView tvType;
        TextView tvWho;
        CardView cardView;
        LinearLayout ll_continear;

        AllViewHolder(View view, Context context) {
            super(view, context, false);
            tvWho = $(R.id.tvWho);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            tvType = (TextView) view.findViewById(R.id.tvType);
            tvTime = (RelativeTimeTextView) view.findViewById(R.id.tvTime);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            ll_continear = (LinearLayout) itemView.findViewById(R.id.ll_continear);
        }

        public void setWho(String who) {
            tvWho.setText(who);
        }
    }


    public interface ItemClickListener {
        void itemClick(DataEntities dataEntitie);

        void imageItemClick(DataEntities dataEntitie, ImageView imageView);
    }

}
