package org.jokar.gankio.presenter.event;

import java.io.File;

/**
 * Created by JokAr on 2016/9/24.
 */
public interface DownloadPresenter {

    void download(String url, File file);
}
