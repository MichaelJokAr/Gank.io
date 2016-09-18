package org.jokar.gankio.di.module.view;

import org.jokar.gankio.di.scoped.UserScope;
import org.jokar.gankio.view.ui.FragmentView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JokAr on 16/9/18.
 */
@Module
public class FragmentViewModule {
    FragmentView mFragmentView;

    public FragmentViewModule(FragmentView fragmentView) {
        mFragmentView = fragmentView;
    }

    @Provides
    @UserScope
    public FragmentView fragmentViewProvider(){
        return  mFragmentView;
    }
}
