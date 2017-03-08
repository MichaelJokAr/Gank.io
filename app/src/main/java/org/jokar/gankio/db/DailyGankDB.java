package org.jokar.gankio.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jokar.gankio.model.entities.DataEntities;
import org.jokar.gankio.model.entities.GankDayEntities;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by JokAr on 2016/9/24.
 */
public class DailyGankDB {
    public static String tableName = "dailygank";
    public static String row_id = "_id";
    public static String rowCreatedAt = "createdAt";
    public static String rowDesc = "desc";
    public static String rowPublishedAt = "publishedAt";
    public static String rowSource = "source";
    public static String rowType = "type";
    public static String rowUrl = "url";
    public static String rowUsed = "used";
    public static String rowWho = "who";
    public static String rowDay = "day";

    private DBhelper helper;

    @Inject
    public DailyGankDB(Context context) {
        helper = DBhelper.getIntance(context);
    }

    /**
     * 保存每日干货
     * @param dayEntities
     */
    public void insert(GankDayEntities dayEntities) {
        String day = dayEntities.getDay();
        List<DataEntities> android = dayEntities.getAndroid();
        if (android != null && android.size() > 0) {
            insert(android, day);
        }

        List<DataEntities> iOS = dayEntities.getiOS();
        if (iOS != null && iOS.size() > 0) {
            insert(iOS, day);
        }

        List<DataEntities> videoRest = dayEntities.getVideoRest();
        if (videoRest != null && videoRest.size() > 0) {
            insert(videoRest, day);
        }

        List<DataEntities> expandRes = dayEntities.getExpandRes();
        if (expandRes != null && expandRes.size() > 0) {
            insert(expandRes, day);
        }

        List<DataEntities> recommended = dayEntities.getRecommended();
        if (recommended != null && recommended.size() > 0) {
            insert(recommended, day);
        }

        List<DataEntities> welfare = dayEntities.getWelfare();
        if (welfare != null && welfare.size() > 0) {
            insert(welfare, day);
        }
    }

    public void insert(List<DataEntities> dataEntities, String day) {
        DataEntities[] objects = new DataEntities[dataEntities.size()];
        dataEntities.toArray(objects);
        Flowable.fromArray(objects)
                .observeOn(Schedulers.computation())
                .subscribe(dataEntitie -> insert(dataEntitie, day));

    }

    public void insert(DataEntities entitie, String day) {
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()) {

            ContentValues values = new ContentValues();
            values.put(row_id, entitie.get_id());
            values.put(rowCreatedAt, entitie.getCreatedAt());
            values.put(rowDesc, entitie.getDesc());
            values.put(rowPublishedAt, entitie.getPublishedAt());
            values.put(rowSource, entitie.getSource());
            values.put(rowType, entitie.getType());
            values.put(rowUrl, entitie.getUrl());
            values.put(rowUsed, entitie.isUsed() ? 1 : 0);
            values.put(rowWho, entitie.getWho());
            values.put(rowDay, day);
            db.insert(tableName, null, values);
        }
    }

    /**
     * 查询是否有每日干货
     * @param day
     * @return
     */
    public boolean hasDailyGank(String day) {
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query(tableName, null, rowDay + " = ? ", new String[]{day}, null, null, null);
            if (cursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查询每日干货
     * @param day
     * @return
     */
    public GankDayEntities query(String day) {
        GankDayEntities gankDayEntities = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {

            if (hasDailyGank(day)) {
                gankDayEntities = new GankDayEntities();

                //福利
                List<DataEntities> Welfare = queryType(day, "福利", db);
                if (Welfare != null && Welfare.size() > 0) {
                    gankDayEntities.setWelfare(Welfare);
                }

                //android
                List<DataEntities> android = queryType(day, "Android", db);
                if (android != null && android.size() > 0) {
                    gankDayEntities.setAndroid(android);
                }
                //iOS
                List<DataEntities> ios = queryType(day, "iOS", db);
                if (ios != null && ios.size() > 0) {
                    gankDayEntities.setiOS(ios);
                }

                //拓展资源
                List<DataEntities> ExpandRes = queryType(day, "拓展资源", db);
                if (ExpandRes != null && ExpandRes.size() > 0) {
                    gankDayEntities.setExpandRes(ExpandRes);
                }
                //瞎推荐
                List<DataEntities> Recommended = queryType(day, "瞎推荐", db);
                if (Recommended != null && Recommended.size() > 0) {
                    gankDayEntities.setRecommended(Recommended);
                }

                //休息视频
                List<DataEntities> VideoRest = queryType(day, "休息视频", db);
                if (VideoRest != null && VideoRest.size() > 0) {
                    gankDayEntities.setVideoRest(VideoRest);
                }
                gankDayEntities.setDay(day);
            }
        }
        return gankDayEntities;
    }

    private List<DataEntities> queryType(String day, String type, SQLiteDatabase db) {
        Cursor cursor = db.query(tableName, null, rowDay + " = ? and " + rowType + " = ?",
                new String[]{day, type}, null, null, null);
        List<DataEntities> dataEntitiesList = null;
        if (cursor.getCount() > 0) {
            dataEntitiesList = new ArrayList<>();
            while (cursor.moveToNext()) {
                DataEntities dataEntities = new DataEntities();

                setData(cursor, dataEntities);

                dataEntitiesList.add(dataEntities);
            }
        }
        return dataEntitiesList;
    }

    private void setData(Cursor cursor, DataEntities dataEntities) {
        dataEntities.set_id(cursor.getString(cursor.getColumnIndex(row_id)));
        dataEntities.setCreatedAt(cursor.getString(cursor.getColumnIndex(rowCreatedAt)));
        dataEntities.setDesc(cursor.getString(cursor.getColumnIndex(rowDesc)));
        dataEntities.setPublishedAt(cursor.getString(cursor.getColumnIndex(rowPublishedAt)));
        dataEntities.setSource(cursor.getString(cursor.getColumnIndex(rowSource)));
        dataEntities.setType(cursor.getString(cursor.getColumnIndex(rowType)));
        dataEntities.setUrl(cursor.getString(cursor.getColumnIndex(rowUrl)));
        dataEntities.setUsed(cursor.getInt(cursor.getColumnIndex(rowUsed)) == 0 ? false : true);
        dataEntities.setWho(cursor.getString(cursor.getColumnIndex(rowWho)));
    }


}
