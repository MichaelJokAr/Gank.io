package org.jokar.gankio.presenter.impl;

import com.trello.rxlifecycle.LifecycleTransformer;

import org.jokar.gankio.db.SearchDB;
import org.jokar.gankio.model.entities.SearchEntities;
import org.jokar.gankio.model.event.SearchModel;
import org.jokar.gankio.presenter.event.SearchPresenter;
import org.jokar.gankio.utils.JLog;
import org.jokar.gankio.view.ui.FragmentView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by JokAr on 16/9/18.
 */
public class SearchPresenterImpl implements SearchPresenter {

    private SearchModel mSearchModel;
    private FragmentView mFragmentView;


    @Inject
    public SearchPresenterImpl(SearchModel searchModel, FragmentView fragmentView) {
        mSearchModel = searchModel;
        mFragmentView = fragmentView;
    }

    @Override
    public void request(SearchDB searchDB,
                        String type,
                        int count,
                        int page,
                        LifecycleTransformer lifecycleTransformer) {
        mSearchModel.request(searchDB,type,count,page,lifecycleTransformer,
                new SearchModel.SearchCallback(){

                    @Override
                    public void start(boolean hasLocalData, List<SearchEntities> searchEntities) {
                        mFragmentView.showLoadProgress();
                        if(hasLocalData){
                            mFragmentView.loadStartLocalData(searchEntities);
                        }else {
                            mFragmentView.loadStartNoLocalData();
                        }
                    }

                    @Override
                    public void loadSuccess(List<SearchEntities> searchEntities) {
                        mFragmentView.completeLoadProgress();
                        mFragmentView.loadData(searchEntities);
                    }

                    @Override
                    public void loadError(Throwable e, boolean hasLocalData) {
                        JLog.e(e);
                        mFragmentView.completeLoadProgress();
                        if(hasLocalData){
                            mFragmentView.loadNoData(e);
                        }else {
                            mFragmentView.loadNoLoacalData(e);
                        }
                    }
                });
    }
}
