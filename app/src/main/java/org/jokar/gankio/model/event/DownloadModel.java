package org.jokar.gankio.model.event;

import java.io.File;

/**
 * Created by JokAr on 2016/9/24.
 */
public interface DownloadModel {

    public  interface DownloadCallBack{
        void downloadSuccess();
        void downloadFail(Throwable e);
    }

    void down(String url, File file,DownloadCallBack callBack);
}
