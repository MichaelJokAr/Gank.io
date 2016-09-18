package org.jokar.gankio.di.component.app;

import org.jokar.gankio.db.SearchDB;
import org.jokar.gankio.di.component.AppComponent;
import org.jokar.gankio.di.module.db.SearchDBModule;
import org.jokar.gankio.di.scoped.DBScope;
import org.jokar.gankio.view.fragment.AndroidFragment;

import dagger.Component;

/**
 * Created by JokAr on 16/9/18.
 */
@DBScope
@Component(modules = SearchDBModule.class)
public interface SearchDBComponent {

    SearchDB searchDB();
}
