package org.jokar.gankio.model.impl;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle.LifecycleTransformer;

import org.jokar.gankio.app.GankioApplication;
import org.jokar.gankio.db.SearchDB;
import org.jokar.gankio.di.component.network.DaggerSearchComponent;
import org.jokar.gankio.di.module.network.SearchModule;
import org.jokar.gankio.model.entities.SearchEntities;
import org.jokar.gankio.model.event.SearchModel;
import org.jokar.gankio.model.network.result.HttpResultFunc;
import org.jokar.gankio.model.network.services.SearchService;
import org.jokar.gankio.utils.Schedulers;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static org.jokar.gankio.utils.Preconditions.checkNotNull;

/**
 * Created by JokAr on 16/9/17.
 */
public class SearchModelImpl implements SearchModel {

    @Inject
    Retrofit mRetrofit;
    @Inject
    SearchService mSearchService;

    public SearchModelImpl() {
        DaggerSearchComponent.builder()
                .netComponent(GankioApplication.getNetComponent())
                .searchModule(new SearchModule())
                .build()
                .inject(this);
    }

    @Override
    public void request(final SearchDB searchDB, String type, int count, int page,
                        LifecycleTransformer lifecycleTransformer,
                        @NonNull final SearchCallback callback) {
        checkNotNull(callback);

        List<SearchEntities> searchEntitiesList = searchDB.query(type);
        final boolean hasLocalData = searchEntitiesList ==null ? false:true;
        callback.start(hasLocalData,searchEntitiesList);

        mSearchService.search(type, count, page)
                .compose(lifecycleTransformer)
                .compose(Schedulers.applySchedulersIO())
                .map(new HttpResultFunc())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SearchEntities>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.loadError(e,hasLocalData);
                    }

                    @Override
                    public void onNext(List<SearchEntities> searchEntities) {
                        searchDB.insert(searchEntities);
                        callback.loadSuccess(searchEntities);
                    }

                });
    }
}
