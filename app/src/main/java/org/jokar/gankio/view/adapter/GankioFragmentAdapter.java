package org.jokar.gankio.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jokar.gankio.R;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.widget.RelativeTimeTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JokAr on 16/9/18.
 */
public class GankioFragmentAdapter extends RecyclerView.Adapter<GankioFragmentAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<DataEntities> mSearchEntitiesList;
    private SimpleDateFormat sdf;

    public GankioFragmentAdapter(Context context, List<DataEntities> mSearchEntitiesList) {
        mContext = context;
        this.mSearchEntitiesList = mSearchEntitiesList;
        mInflater = LayoutInflater.from(mContext);
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataEntities entities = mSearchEntitiesList.get(position);
        holder.tvDesc.setText(entities.getDesc());
        holder.tvWho.setText(entities.getWho());
        try {
            Date date = sdf.parse(entities.getPublishedAt());
            holder.tvTime.setRefreshTime(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mSearchEntitiesList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDesc)
        TextView tvDesc;
        @BindView(R.id.tvWho)
        TextView tvWho;
        @BindView(R.id.tvTime)
        RelativeTimeTextView tvTime;
        @BindView(R.id.cardView)
        CardView cardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
