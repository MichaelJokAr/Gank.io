package org.jokar.gankio.presenter.event;

import com.trello.rxlifecycle.LifecycleTransformer;

import org.jokar.gankio.db.SearchDB;

/**
 * Created by JokAr on 16/9/18.
 */
public interface SearchPresenter {

    void request(SearchDB searchDB,
                 String type,
                 int count,
                 int page,
                 LifecycleTransformer lifecycleTransformer);
}
