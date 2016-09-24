package org.jokar.gankio.di.component.preseneter;

import org.jokar.gankio.di.module.models.DwonloadModelModule;
import org.jokar.gankio.di.module.view.DownloadViewModule;
import org.jokar.gankio.view.listener.DownloadIntentService;

import dagger.Component;

/**
 * Created by JokAr on 2016/9/24.
 */
@Component(modules = {DwonloadModelModule.class, DownloadViewModule.class})
public interface DownloadPresenterCom {

    void inject(DownloadIntentService service);
}
