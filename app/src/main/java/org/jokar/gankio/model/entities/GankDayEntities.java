package org.jokar.gankio.model.entities;

import com.google.gson.annotations.SerializedName;

import org.jokar.gankio.utils.JLog;

import java.util.ArrayList;
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

    private List<Integer> titleIndex;
    private List<DataEntities> mDataEntitiesList;

    public GankDayEntities() {
        mDataEntitiesList = new ArrayList<>();
    }

    public List<DataEntities> getAndroid() {
        return Android;
    }

    public void setAndroid(List<DataEntities> android) {
        Android = android;
        mDataEntitiesList.addAll(Android);
    }

    public List<DataEntities> getiOS() {
        return iOS;
    }

    public void setiOS(List<DataEntities> iOS) {
        this.iOS = iOS;
        mDataEntitiesList.addAll(iOS);
    }

    public List<DataEntities> getVideoRest() {
        return VideoRest;
    }

    public void setVideoRest(List<DataEntities> videoRest) {
        VideoRest = videoRest;
        mDataEntitiesList.addAll(VideoRest);
    }

    public List<DataEntities> getExpandRes() {
        return ExpandRes;
    }

    public void setExpandRes(List<DataEntities> expandRes) {
        ExpandRes = expandRes;
        mDataEntitiesList.addAll(ExpandRes);
    }

    public List<DataEntities> getRecommended() {
        return Recommended;
    }

    public void setRecommended(List<DataEntities> recommended) {
        Recommended = recommended;
        mDataEntitiesList.addAll(Recommended);
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

    /**
     * 判断是否为空
     *
     * @return
     */
    public boolean isNull() {
        if (hasAndroid() || hasExpandRes() || hasIOS() || hasRecommended()
                || hasVideoRest() || hasWelfare()) {
            return false;
        }
        return true;
    }

    public boolean hasAndroid() {
        if (Android == null || Android.size() == 0)
            return false;
        return true;
    }

    public boolean hasIOS() {
        if (iOS == null || iOS.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean hasVideoRest() {
        if (VideoRest == null || VideoRest.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean hasExpandRes() {
        if (ExpandRes == null || ExpandRes.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean hasRecommended() {
        if (Recommended == null || Recommended.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean hasWelfare() {
        if (Welfare == null || Welfare.size() == 0) {
            return false;
        }
        return true;
    }

    public int getCount() {
        int count = 0;
        titleIndex = new ArrayList<>();
        if (hasAndroid()) {
            titleIndex.add(count);
            count += Android.size() + 1;
        }

        if (hasIOS()) {
            titleIndex.add(count);
            count += iOS.size() + 1;
        }

        if (hasRecommended()) {
            titleIndex.add(count);
            count += Recommended.size() + 1;
        }

        if (hasExpandRes()) {
            titleIndex.add(count);
            count += ExpandRes.size() + 1;
        }

        if (hasVideoRest()) {
            titleIndex.add(count);
            count += VideoRest.size() + 1;
        }

        JLog.d(titleIndex.toString());
        return count;
    }

    public List<Integer> getTitleIndex() {
        return titleIndex;
    }

    public List<DataEntities> getDataEntitiesList() {
        if (mDataEntitiesList.size() == 0) {
            addDataEntitiesList();
        }
        return mDataEntitiesList;
    }

    private void addDataEntitiesList() {
        if (hasAndroid()) {
            mDataEntitiesList.addAll(Android);
        }

        if (hasIOS()) {
            mDataEntitiesList.addAll(iOS);
        }

        if (hasExpandRes()) {
            mDataEntitiesList.addAll(ExpandRes);
        }

        if (hasRecommended()) {
            mDataEntitiesList.addAll(Recommended);
        }

        if (hasVideoRest()) {
            mDataEntitiesList.addAll(VideoRest);
        }
    }
}
