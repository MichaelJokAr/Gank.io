package org.jokar.gankio.view.ui;

import org.jokar.gankio.model.entities.SearchEntities;

import java.util.List;

/**
 * Created by JokAr on 16/9/17.
 */
public interface FragmentView {

    void showLoadProgress();

    void completeLoadProgress();

    void loadStartLocalData(List<SearchEntities> searchEntities);

    void loadStartNoLocalData();

    void loadNoLoacalData(Throwable e);

    void loadNoData(Throwable e);

    void loadData(List<SearchEntities> searchEntities);
}
