package org.jokar.gankio.di.module.view;

import org.jokar.gankio.view.ui.DownloadView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 2016/9/24.
 */
@Module
public class DownloadViewModule {

    private DownloadView mDownloadView;

    public DownloadViewModule(DownloadView downloadView) {
        mDownloadView = downloadView;
    }

    @Provides
    public DownloadView downloadViewProvider(){
        return mDownloadView;
    }
}
