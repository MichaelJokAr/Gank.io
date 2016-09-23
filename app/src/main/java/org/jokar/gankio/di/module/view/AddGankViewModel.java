package org.jokar.gankio.di.module.view;

import org.jokar.gankio.view.ui.AddGankView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 2016/9/23.
 */
@Module
public class AddGankViewModel {
    private AddGankView mAddGankView;

    public AddGankViewModel(AddGankView addGankView) {
        mAddGankView = addGankView;
    }

    @Provides
    public AddGankView addGankViewProvider(){
        return mAddGankView;
    }
}
