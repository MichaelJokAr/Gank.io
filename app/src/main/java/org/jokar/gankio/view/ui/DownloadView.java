package org.jokar.gankio.view.ui;

/**
 * Created by JokAr on 2016/9/24.
 */

public interface DownloadView {

    void success();
    void fail(Throwable e);
}
