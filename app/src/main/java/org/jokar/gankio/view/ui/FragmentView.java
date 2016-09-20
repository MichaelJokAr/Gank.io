package org.jokar.gankio.view.ui;

import org.jokar.gankio.model.entities.DataEntities;

import java.util.List;

/**
 * Created by JokAr on 16/9/17.
 */
public interface FragmentView {

    void showLoadProgress();

    void completeLoadProgress();

    void loadStartLocalData(List<DataEntities> searchEntities);

    void loadStartNoLocalData();

    void loadNoLoacalData(Throwable e);

    void loadNoData(Throwable e);

    void loadData(List<DataEntities> dataEntitiesList);

    void refreshFail(Throwable e);

    void loadMore(List<DataEntities> dataEntitiesList);

    void loadMoreFail(Throwable e);
}
