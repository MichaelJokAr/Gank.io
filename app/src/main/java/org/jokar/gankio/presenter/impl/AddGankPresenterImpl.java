package org.jokar.gankio.presenter.impl;

import android.text.TextUtils;


import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.model.event.AddGankModel;
import org.jokar.gankio.presenter.event.AddGankPresenter;
import org.jokar.gankio.view.ui.AddGankView;
import org.reactivestreams.Subscriber;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


/**
 * Created by JokAr on 2016/9/23.
 */

public class AddGankPresenterImpl implements AddGankPresenter {

    private AddGankView mAddGankView;
    private AddGankModel mAddGankModel;

    @Inject
    public AddGankPresenterImpl(AddGankView addGankView, AddGankModel addGankModel) {
        mAddGankView = addGankView;
        mAddGankModel = addGankModel;
    }

    @Override
    public void submit(LifecycleTransformer lifecycleTransformer,
                       String url, String desc, String who, String type, boolean debug) {

        Observable.create((ObservableOnSubscribe<Boolean>) subscriber ->{
            subscriber.onNext(debug);
        })
                .filter(aBoolean -> {
                    //检查类型
                    if (TextUtils.isEmpty(type)) {
                        mAddGankView.showTypeEmtyError();
                        return false;
                    }
                    return true;
                })
                .filter(aBoolean -> {
                    //检查描述
                    if (TextUtils.isEmpty(desc)) {
                        mAddGankView.showDescEmtyError("对干货的内容描述不能为空");
                        return false;
                    } else if (desc.length() < 5) {
                        mAddGankView.showDescEmtyError("对干货的内容描述至少得5个字");
                        return false;
                    }
                    return true;
                })
                .filter(aBoolean -> {
                    //检查url
                    if (TextUtils.isEmpty(url)) {
                        mAddGankView.showUrlEmtyError();
                        return false;
                    }
                    return true;
                })
                .filter(aBoolean -> {
                    //检查id
                    if (TextUtils.isEmpty(who)) {
                        mAddGankView.showWhoEmtyError();
                        return false;
                    }
                    return true;
                })
                .subscribe(aBoolean -> {

                    mAddGankModel.addGank(lifecycleTransformer, url, desc, who, type, debug,
                            new AddGankModel.AddGankCallBack() {
                                @Override
                                public void onStart() {
                                    mAddGankView.showSubmitProgress();
                                }

                                @Override
                                public void success(String msg) {
                                    mAddGankView.showSubmitSuccess(msg);
                                }

                                @Override
                                public void fail(Throwable e) {
                                    mAddGankView.compeleteSubmitProgress();
                                    mAddGankView.showSubmiError(e);
                                }

                                @Override
                                public void compelete() {
                                    mAddGankView.compeleteSubmitProgress();
                                }
                            });
                });


    }
}
