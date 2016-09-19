package org.jokar.gankio.model.rxbus.event;

/**
 * Created by JokAr on 16/9/18.
 */
public class MainViewPagerEvent {
    String type;

    public MainViewPagerEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
