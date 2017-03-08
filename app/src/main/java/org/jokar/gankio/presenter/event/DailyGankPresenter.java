package org.jokar.gankio.presenter.event;


import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.db.DailyGankDB;

/**
 * Created by JokAr on 2016/9/25.
 */
public interface DailyGankPresenter {

    void requestDailyGank(long time, DailyGankDB dailyGankDB, LifecycleTransformer lifecycleTransformer);
}
