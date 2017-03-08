package org.jokar.gankio.presenter.event;


import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.db.DataDB;

/**
 * Created by JokAr on 16/9/18.
 */
public interface DataPresenter {

    void request(DataDB searchDB,
                 String type,
                 int count,
                 int page,
                 LifecycleTransformer lifecycleTransformer);


    void refrsh(DataDB dataDB,
                String type,
                int count,
                int pageSize,
                LifecycleTransformer lifecycleTransformer);

    void loadMore(DataDB dataDB,
                  String type,
                  int count,
                  int pageSize,
                  LifecycleTransformer lifecycleTransformer);
}
