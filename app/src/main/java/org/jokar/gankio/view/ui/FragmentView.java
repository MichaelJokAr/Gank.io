package org.jokar.gankio.view.ui;

import org.jokar.gankio.model.entities.SearchEntities;

import java.util.List;

/**
 * Created by JokAr on 16/9/17.
 */
public interface FragmentView {

    void getDataSuccess(List<SearchEntities> searchEntities);

    void getDataError(Throwable e,List<SearchEntities> searchEntities);
}
