package org.jokar.gankio.model.event;


import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.db.DailyGankDB;
import org.jokar.gankio.model.entities.GankDayEntities;

/**
 * Created by JokAr on 2016/9/24.
 */
public interface DailyGankModel {

    interface DailyGankCallback {
        void start();

        void requestSuccess(GankDayEntities gankDayEntities);

        void compelete();

        void requestFail(Throwable e);
    }

    void request(LifecycleTransformer lifecycleTransformer,
                 DailyGankDB dailyGankDB,
                 String year,
                 String month,
                 String day,
                 DailyGankCallback callback);

}
