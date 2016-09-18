package org.jokar.gankio.di.module.models;

import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.model.event.SearchModel;
import org.jokar.gankio.model.impl.SearchModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 16/9/18.
 */
@Module
public class SearchModelModule {

    @Provides
    @UserScope
    public SearchModel searchModelProvider(){
        return new SearchModelImpl();
    }
}
