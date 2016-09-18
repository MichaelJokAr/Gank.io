package org.jokar.gankio.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jokar.gankio.R;
import org.jokar.gankio.model.entities.SearchEntities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JokAr on 16/9/18.
 */
public class TypeFragmentAdapter extends RecyclerView.Adapter<TypeFragmentAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<SearchEntities> mSearchEntitiesList;
    private   SimpleDateFormat sdf;

    public TypeFragmentAdapter(Context context,List<SearchEntities> mSearchEntitiesList) {
        mContext = context;
        this.mSearchEntitiesList = mSearchEntitiesList;
        mInflater = LayoutInflater.from(mContext);
       sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_fragment, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchEntities entities = mSearchEntitiesList.get(position);
        holder.tvDesc.setText(entities.getDesc());
        holder.tvWho.setText(entities.getWho());
        try {
            sdf.format(sdf.parse(entities.getPublishedAt()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mSearchEntitiesList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvDesc)
        TextView tvDesc;
        @BindView(R.id.tvWho)
        TextView tvWho;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.cardView)
        CardView cardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
