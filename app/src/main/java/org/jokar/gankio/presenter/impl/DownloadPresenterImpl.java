package org.jokar.gankio.presenter.impl;

import org.jokar.gankio.model.event.DownloadModel;
import org.jokar.gankio.presenter.event.DownloadPresenter;
import org.jokar.gankio.view.ui.DownloadView;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by JokAr on 2016/9/24.
 */
public class DownloadPresenterImpl implements DownloadPresenter {

    private DownloadModel mDownloadModel;
    private DownloadView mDownloadView;

    @Inject
    public DownloadPresenterImpl(DownloadModel downloadModel, DownloadView downloadView) {
        mDownloadModel = downloadModel;
        mDownloadView = downloadView;
    }

    @Override
    public void download(String url, File file) {
        mDownloadModel.down(url, file, new DownloadModel.DownloadCallBack() {
            @Override
            public void downloadSuccess() {
                mDownloadView.success();
            }

            @Override
            public void downloadFail(Throwable e) {
                mDownloadView.fail(e);
            }
        });
    }
}
