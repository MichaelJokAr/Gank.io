package org.jokar.gankio.di.module.models;

import org.jokar.gankio.model.event.DataModel;
import org.jokar.gankio.model.impl.DataModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 16/9/19.
 */
@Module
public class DataModelModule {

    @Provides
    public DataModel dataModelProvider(){
        return new DataModelImpl();
    }
}
