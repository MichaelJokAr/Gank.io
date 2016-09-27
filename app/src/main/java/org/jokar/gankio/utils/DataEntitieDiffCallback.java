package org.jokar.gankio.utils;

import android.support.v7.util.DiffUtil;

import org.jokar.gankio.model.entities.DataEntities;

import java.util.List;

/**
 * Created by JokAr on 16/9/21.
 */

public class DataEntitieDiffCallback extends DiffUtil.Callback {
    private List<DataEntities> oldDataEntitiesList;
    private List<DataEntities> newDataEntitiesList;

    public DataEntitieDiffCallback(List<DataEntities> oldDataEntitiesList,
                                   List<DataEntities> newDataEntitiesList) {
        this.oldDataEntitiesList = oldDataEntitiesList;
        this.newDataEntitiesList = newDataEntitiesList;
    }

    @Override
    public int getOldListSize() {
        return oldDataEntitiesList != null ? oldDataEntitiesList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newDataEntitiesList != null ? newDataEntitiesList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldDataEntitiesList.get(oldItemPosition).get_id()
                .equals(newDataEntitiesList.get(newItemPosition).get_id());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldDataEntitiesList.get(oldItemPosition).
                equals(newDataEntitiesList.get(newItemPosition));
    }


}
