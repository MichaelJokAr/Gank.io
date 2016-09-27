package org.jokar.gankio.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding.view.RxView;

import org.jokar.gankio.R;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.widget.RelativeTimeTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.functions.Action1;


/**
 * Created by JokAr on 16/9/18.
 */
public class GankioFragmentAdapter extends RecyclerView.Adapter<GankioFragmentAdapter.GankViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<DataEntities> mSearchEntitiesList;
    private SimpleDateFormat sdf;
    private String mType;
    public FootViewHolder mFootViewHolder;
    private ItemClickListener mClickListener;

    public GankioFragmentAdapter(Context context, String type,
                                 List<DataEntities> mSearchEntitiesList) {
        mContext = context;
        mType = type;
        this.mSearchEntitiesList = mSearchEntitiesList;
        mInflater = LayoutInflater.from(mContext);
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }

    @Override
    public GankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new ViewHolder(mInflater.inflate(R.layout.item_fragment, parent, false));
            case 1:
                return new ImageViewHolder(mInflater.inflate(R.layout.item_imagefragment, parent, false));
            case 2:
                return new AllViewHolder(mInflater.inflate(R.layout.item_gan_allfragment, parent, false));
            case 3: {
                mFootViewHolder = new FootViewHolder(mInflater.inflate(R.layout.item_footview, parent, false));
                return mFootViewHolder;
            }
            case 4:
                return new ImageViewHolder(mInflater.inflate(R.layout.item_all_imagefragment, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(GankViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 0: {//非图片类型
                DataEntities entities = mSearchEntitiesList.get(position);
                holder.setWho(entities.getWho());
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.tvDesc.setText(entities.getDesc());
                try {
                    Date date = sdf.parse(entities.getPublishedAt());
                    viewHolder.tvTime.setRefreshTime(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //设置点击事件
                RxView.clicks(viewHolder.ll_continear).subscribe(aVoid -> {
                    if (mClickListener != null) {
                        mClickListener.itemClick(entities);
                    }
                });
                break;
            }
            case 1:
            case 4: { //图片类型
                DataEntities entities = mSearchEntitiesList.get(position);
                holder.setWho(entities.getWho());
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                imageViewHolder.tvTime.setText(entities.getDesc());
                imageViewHolder.loadImage(entities.getUrl(), mContext);
                //设置点击事件
                RxView.clicks(imageViewHolder.ll_continear).subscribe(aVoid -> {
                    if (mClickListener != null) {
                        mClickListener.imageItemClick(entities, imageViewHolder.image);
                    }
                });
                break;
            }
            case 2: {//all
                DataEntities entities = mSearchEntitiesList.get(position);
                holder.setWho(entities.getWho());
                AllViewHolder allViewHolder = (AllViewHolder) holder;
                allViewHolder.tvDesc.setText(entities.getDesc());
                allViewHolder.tvType.setText(entities.getType());
                try {
                    Date date = sdf.parse(entities.getPublishedAt());
                    allViewHolder.tvTime.setRefreshTime(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //设置点击事件

                RxView.clicks(allViewHolder.ll_continear).subscribe(aVoid -> {
                    if (mClickListener != null) {
                        mClickListener.itemClick(entities);
                    }
                });
                break;
            }

            case 3: {//footView
                //设置点击事件
                RxView.clicks(mFootViewHolder.ll_foot).subscribe(aVoid -> {
                    if (mClickListener != null) {
                        mFootViewHolder.showProgress();
                        mClickListener.footViewClick();
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return mSearchEntitiesList != null ? mSearchEntitiesList.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return 3;
        } else {
            DataEntities dataEntities = mSearchEntitiesList.get(position);
            if (mType.equals("all")) {
                if (dataEntities.getType().equals("福利")) {
                    return 4;
                } else {
                    return 2;
                }

            } else if (dataEntities.getType().equals("福利")) {
                return 1;
            } else {
                return 0;
            }
        }


    }

    public void setOnItemClickListener(ItemClickListener listener) {
        mClickListener = listener;
    }

    public List<DataEntities> getData() {
        return mSearchEntitiesList;
    }

    public void setData(List<DataEntities> data) {
        this.mSearchEntitiesList = new ArrayList<>(data);
    }

    class ViewHolder extends GankViewHolder {

        TextView tvDesc;
        RelativeTimeTextView tvTime;

        ViewHolder(View view) {
            super(view);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            tvTime = (RelativeTimeTextView) view.findViewById(R.id.tvTime);
            cardView = (CardView) view.findViewById(R.id.cardView);
            ll_continear = (LinearLayout) itemView.findViewById(R.id.ll_continear);
        }
    }

    class ImageViewHolder extends GankViewHolder {
        ImageView image;
        TextView tvTime;

        public ImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            ll_continear = (LinearLayout) itemView.findViewById(R.id.ll_continear);
        }

        public void loadImage(String url, Context context) {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.mipmap.default_image)
                    .error(R.mipmap.default_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image);
        }
    }

    class GankViewHolder extends RecyclerView.ViewHolder {

        TextView tvWho;
        CardView cardView;
        LinearLayout ll_continear;

        public GankViewHolder(View itemView) {
            super(itemView);
            tvWho = (TextView) itemView.findViewById(R.id.tvWho);

        }

        public void setWho(String who) {
            tvWho.setText(who);
        }
    }

    class AllViewHolder extends GankViewHolder {

        TextView tvDesc;
        RelativeTimeTextView tvTime;
        CardView cardView;
        TextView tvType;

        AllViewHolder(View view) {
            super(view);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
            tvType = (TextView) view.findViewById(R.id.tvType);
            tvTime = (RelativeTimeTextView) view.findViewById(R.id.tvTime);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            ll_continear = (LinearLayout) itemView.findViewById(R.id.ll_continear);
        }
    }

    class FootViewHolder extends GankViewHolder {
        private LinearLayout ll_foot;
        private ProgressBar progressBar;
        private TextView tvLoad;

        public FootViewHolder(View itemView) {
            super(itemView);
            ll_foot = (LinearLayout) itemView.findViewById(R.id.ll_foot);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            tvLoad = (TextView) itemView.findViewById(R.id.tvLoad);
//            progressBar.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        }

        public void setClickable(boolean clickable) {
            ll_foot.setClickable(clickable);
        }

        public void showProgress() {
            progressBar.setVisibility(View.VISIBLE);
            tvLoad.setText(mContext.getString(R.string.loading));
        }

        public void showClickText() {
            progressBar.setVisibility(View.GONE);
            tvLoad.setText(mContext.getString(R.string.reload));
        }
    }


    public void footShowClickText() {
        mFootViewHolder.showClickText();
    }

    public void footShowProgress() {
        mFootViewHolder.showProgress();
    }

    public void setFootClickable(boolean clickable) {
        mFootViewHolder.setClickable(clickable);
    }

    public interface ItemClickListener {
        void itemClick(DataEntities dataEntitie);

        void footViewClick();

        void imageItemClick(DataEntities dataEntitie, ImageView imageView);
    }

}
