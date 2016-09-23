package org.jokar.gankio.di.component.preseneter;

import org.jokar.gankio.di.module.models.AddGankModelModule;
import org.jokar.gankio.di.module.view.AddGankViewModel;
import org.jokar.gankio.view.activity.AddGankActivity;

import dagger.Component;

/**
 * Created by JokAr on 2016/9/23.
 */
@Component(modules = {AddGankViewModel.class, AddGankModelModule.class})
public interface AddGanPresneterCom {

    void inject(AddGankActivity activity);
}
