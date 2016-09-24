package org.jokar.gankio.view.listener;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;


import org.jokar.gankio.Constants;
import org.jokar.gankio.di.component.preseneter.DaggerDownloadPresenterCom;
import org.jokar.gankio.di.module.models.DwonloadModelModule;
import org.jokar.gankio.di.module.view.DownloadViewModule;
import org.jokar.gankio.model.network.down.DownloadProgressListener;
import org.jokar.gankio.presenter.impl.DownloadPresenterImpl;
import org.jokar.gankio.utils.JLog;
import org.jokar.gankio.utils.JToast;
import org.jokar.gankio.view.ui.DownloadView;

import java.io.File;

import javax.inject.Inject;

/**
 * 下载图片
 * Created by JokAr on 2016/9/24.
 */
public class DownloadIntentService extends IntentService implements DownloadProgressListener, DownloadView {


    @Inject
    DownloadPresenterImpl mPresenter;

    public DownloadIntentService() {
        super("DownloadIntentService");

        DaggerDownloadPresenterCom.builder()
                .downloadViewModule(new DownloadViewModule(this))
                .dwonloadModelModule(new DwonloadModelModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getStringExtra("url");
        String path = Environment.getExternalStorageDirectory().getPath()
                + Constants.imagePath
                + url.substring(url.lastIndexOf("/"));
        mPresenter.download(url, new File(path));

    }

    @Override
    public void update(long bytesRead, long contentLength, boolean done) {
        JLog.d("done: " + done + "  " + bytesRead + "/" + contentLength);
    }

    @Override
    public void success() {
        JToast.Toast(getApplicationContext(),"下载成功");
    }

    @Override
    public void fail(Throwable e) {
        JToast.Toast(getApplicationContext(),"下载失败: "+e.getMessage());
    }
}
