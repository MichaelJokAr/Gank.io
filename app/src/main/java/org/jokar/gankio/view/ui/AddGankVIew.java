package org.jokar.gankio.view.ui;

/**
 * Created by JokAr on 2016/9/23.
 */
public interface AddGankView {

    void showDescEmtyError(String error);

    void showUrlEmtyError();

    void showWhoEmtyError();

    void showTypeEmtyError();

    void showSubmitProgress();

    void compeleteSubmitProgress();

    void showSubmitSuccess(String msg);

    void showSubmiError(Throwable e);
}
