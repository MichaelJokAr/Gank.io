package org.jokar.gankio.di.module.view;

import org.jokar.gankio.view.ui.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 2016/9/25.
 */
@Module
public class MainViewModule {

    private MainView mMainView;

    public MainViewModule(MainView mainView) {
        mMainView = mainView;
    }

    @Provides
    public MainView mainViewProvider(){
        return mMainView;
    }
}
