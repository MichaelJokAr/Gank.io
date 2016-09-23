package org.jokar.gankio.di.module.models;

import org.jokar.gankio.model.event.AddGankModel;
import org.jokar.gankio.model.impl.AddGankModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 2016/9/23.
 */
@Module
public class AddGankModelModule {

    @Provides
    public AddGankModel addGankModelProvider(){
        return new AddGankModelImpl();
    }
}
