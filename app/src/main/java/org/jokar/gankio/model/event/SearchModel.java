package org.jokar.gankio.model.event;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle.LifecycleTransformer;

import org.jokar.gankio.model.entities.SearchEntities;

import java.util.List;


/**
 * Created by JokAr on 16/9/17.
 */
public interface SearchModel {

    interface SearchCallback{
        void start(List<SearchEntities> searchEntities);
        void loadSuccess(List<SearchEntities> searchEntities);
        void loadError(Throwable e,List<SearchEntities> searchEntities);
    }

    void request(String type,
                 int count,
                 int page,
                 LifecycleTransformer lifecycleTransformer,
                 @NonNull SearchCallback callback);
}
