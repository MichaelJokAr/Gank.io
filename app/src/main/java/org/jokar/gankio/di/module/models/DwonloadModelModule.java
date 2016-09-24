package org.jokar.gankio.di.module.models;

import org.jokar.gankio.model.event.DownloadModel;
import org.jokar.gankio.model.impl.DownloadModelImpl;
import org.jokar.gankio.model.network.down.DownloadProgressListener;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 2016/9/24.
 */
@Module
public class DwonloadModelModule {

    private DownloadProgressListener mListener;

    public DwonloadModelModule(DownloadProgressListener listener) {
        mListener = listener;
    }

    @Provides
    public DownloadModel downloadModelProvider(){
        return new DownloadModelImpl(mListener);
    }
}
