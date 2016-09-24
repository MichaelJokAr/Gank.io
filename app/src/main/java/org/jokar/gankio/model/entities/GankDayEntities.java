package org.jokar.gankio.model.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 每日干货
 * Created by JokAr on 2016/9/24.
 */
public class GankDayEntities {

    @SerializedName("Android")
    private List<DataEntities> Android;
    @SerializedName("iOS")
    private List<DataEntities> iOS;
    @SerializedName("休息视频")
    private List<DataEntities> VideoRest;
    @SerializedName("拓展资源")
    private List<DataEntities> ExpandRes;
    @SerializedName("瞎推荐")
    private List<DataEntities> Recommended;
    @SerializedName("福利")
    private List<DataEntities> Welfare;

    private String day;

    public List<DataEntities> getAndroid() {
        return Android;
    }

    public void setAndroid(List<DataEntities> android) {
        Android = android;
    }

    public List<DataEntities> getiOS() {
        return iOS;
    }

    public void setiOS(List<DataEntities> iOS) {
        this.iOS = iOS;
    }

    public List<DataEntities> getVideoRest() {
        return VideoRest;
    }

    public void setVideoRest(List<DataEntities> videoRest) {
        VideoRest = videoRest;
    }

    public List<DataEntities> getExpandRes() {
        return ExpandRes;
    }

    public void setExpandRes(List<DataEntities> expandRes) {
        ExpandRes = expandRes;
    }

    public List<DataEntities> getRecommended() {
        return Recommended;
    }

    public void setRecommended(List<DataEntities> recommended) {
        Recommended = recommended;
    }

    public List<DataEntities> getWelfare() {
        return Welfare;
    }

    public void setWelfare(List<DataEntities> welfare) {
        Welfare = welfare;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "GankDayEntities{" +
                "Android=" + Android +
                ", iOS=" + iOS +
                ", VideoRest=" + VideoRest +
                ", ExpandRes=" + ExpandRes +
                ", Recommended=" + Recommended +
                ", Welfare=" + Welfare +
                '}';
    }
}
