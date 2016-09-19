package org.jokar.gankio.presenter.event;

import com.trello.rxlifecycle.LifecycleTransformer;

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
}
