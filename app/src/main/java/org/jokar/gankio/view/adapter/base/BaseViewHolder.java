package org.jokar.gankio.view.adapter.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.jokar.gankio.R;

/**
 * Created by JokAr on 2017/3/8.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public View itemView;

    public BaseViewHolder(View view, Context context, boolean addBG) {
        super(view);
        this.itemView = view;
        //setBackground
        if(addBG) {
            int[] attrs = new int[]{R.attr.selectableItemBackground};
            TypedArray typedArray = context.obtainStyledAttributes(attrs);
            int backgroundResource = typedArray.getResourceId(0, 0);
            this.itemView.setBackgroundResource(backgroundResource);
        }
    }

    public <T extends View> T $(@NonNull @IdRes int id) {

        return (T) itemView.findViewById(id);
    }

    public View getItemView() {
        return itemView;
    }
}
