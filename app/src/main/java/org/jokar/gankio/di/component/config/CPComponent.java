package org.jokar.gankio.di.component.config;


import org.jokar.gankio.di.module.config.CPModule;
import org.jokar.gankio.model.config.ConfigPreferences;

import dagger.Component;

/**
 * Created by JokAr on 16/9/16.
 */
@Component(modules = {CPModule.class})
public interface CPComponent {

   ConfigPreferences config();
}
