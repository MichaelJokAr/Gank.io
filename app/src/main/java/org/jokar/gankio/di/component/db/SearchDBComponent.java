package org.jokar.gankio.di.component.db;

import org.jokar.gankio.db.SearchDB;
import org.jokar.gankio.di.module.db.SearchDBModule;
import org.jokar.gankio.di.scoped.DBScope;

import dagger.Component;

/**
 * Created by JokAr on 16/9/18.
 */
@DBScope
@Component(modules = SearchDBModule.class)
public interface SearchDBComponent {

    SearchDB searchDB();
}
