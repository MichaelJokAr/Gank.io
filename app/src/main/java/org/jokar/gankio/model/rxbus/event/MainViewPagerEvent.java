package org.jokar.gankio.model.rxbus.event;

/**
 * Created by JokAr on 16/9/18.
 */
public class MainViewPagerEvent {
    int index;

    public MainViewPagerEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
