package org.jokar.gankio.presenter.event;


import com.trello.rxlifecycle2.LifecycleTransformer;

import org.jokar.gankio.model.config.ConfigPreferences;

/**
 * Created by JokAr on 16/9/10.
 */
public interface SplashViewPresenter {

    void getImage(ConfigPreferences context, LifecycleTransformer transformer);
}
