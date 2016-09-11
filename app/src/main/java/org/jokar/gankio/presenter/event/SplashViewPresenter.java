package org.jokar.gankio.presenter.event;

import android.content.Context;

import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * Created by JokAr on 16/9/10.
 */
public interface SplashViewPresenter {

    void getImage(Context context,LifecycleTransformer transformer);
}
