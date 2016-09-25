package org.jokar.gankio.view.ui;

import org.jokar.gankio.model.entities.GankDayEntities;

/**
 * Created by JokAr on 2016/9/25.
 */
public interface DailyGankView {

    void showRequestProgress();

    void compeleteProgress();

    void loadData(GankDayEntities gankDayEntities);

    void showFail(Throwable e);
}
