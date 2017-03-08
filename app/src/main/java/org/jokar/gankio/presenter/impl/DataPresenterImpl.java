package org.jokar.gankio.presenter.impl;


import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.db.DataDB;
import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.model.event.DataModel;
import org.jokar.gankio.presenter.event.DataPresenter;
import org.jokar.gankio.view.ui.FragmentView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by JokAr on 16/9/18.
 */
public class DataPresenterImpl implements DataPresenter {

    private DataModel mDataModel;
    private FragmentView mFragmentView;

    @Inject
    public DataPresenterImpl(DataModel dataModel, FragmentView fragmentView) {
        mDataModel = dataModel;
        mFragmentView = fragmentView;
    }

    @Override
    public void request(DataDB dataDB,
                        String type,
                        int count,
                        int page,
                        LifecycleTransformer lifecycleTransformer) {
        mDataModel.request(type, count, page, dataDB, lifecycleTransformer,
                new DataModel.DataCallBack() {
                    @Override
                    public void start(boolean hasLocalData, List<DataEntities> dataEntitiesList) {
                        mFragmentView.showLoadProgress();
                        if (hasLocalData) {
                            mFragmentView.loadStartLocalData(new ArrayList<>(dataEntitiesList));
                        } else {
                            mFragmentView.loadStartNoLocalData();
                        }
                    }

                    @Override
                    public void requestSuccess(ArrayList<DataEntities> dataEntitiesList) {
                        mFragmentView.loadData(dataEntitiesList);

                    }

                    @Override
                    public void requestFail(boolean hasLocalData, Throwable e) {
                        mFragmentView.completeLoadProgress();
                        if (hasLocalData) {
                            mFragmentView.loadNoData(e);
                        } else {
                            mFragmentView.loadNoLoacalData(e);
                        }
                    }

                    @Override
                    public void onCompleted() {
                        mFragmentView.completeLoadProgress();
                    }
                });
    }

    @Override
    public void refrsh(DataDB dataDB, String type, int count, int pageSize, LifecycleTransformer lifecycleTransformer) {
        mDataModel.request(type, count, pageSize, dataDB, lifecycleTransformer,
                new DataModel.DataCallBack() {
                    @Override
                    public void start(boolean hasLocalData, List<DataEntities> dataEntitiesList) {

                    }

                    @Override
                    public void requestSuccess(ArrayList<DataEntities> dataEntitiesList) {

                        mFragmentView.loadData(dataEntitiesList);

                    }

                    @Override
                    public void requestFail(boolean hasLocalData, Throwable e) {
                        mFragmentView.completeLoadProgress();
                        mFragmentView.refreshFail(e);
                    }

                    @Override
                    public void onCompleted() {
                        mFragmentView.completeLoadProgress();
                    }
                });
    }

    @Override
    public void loadMore(DataDB dataDB, String type, int count,
                         int pageSize, LifecycleTransformer lifecycleTransformer) {
        mDataModel.refresh(type, count, pageSize, dataDB, lifecycleTransformer,
                new DataModel.DataCallBack() {
                    @Override
                    public void start(boolean hasLocalData, List<DataEntities> dataEntitiesList) {

                    }

                    @Override
                    public void requestSuccess(ArrayList<DataEntities> dataEntitiesList) {
                        mFragmentView.loadMore(dataEntitiesList);
                    }

                    @Override
                    public void requestFail(boolean hasLocalData, Throwable e) {
                        mFragmentView.loadMoreFail(e);
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
}
