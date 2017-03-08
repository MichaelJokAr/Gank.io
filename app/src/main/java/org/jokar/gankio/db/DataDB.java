package org.jokar.gankio.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jokar.gankio.model.entities.DataEntities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by JokAr on 16/9/19.
 */
public class DataDB {
    public static String tableName = "data";
    public static String row_id = "_id";
    public static String rowCreatedAt = "createdAt";
    public static String rowDesc = "desc";
    public static String rowPublishedAt = "publishedAt";
    public static String rowSource = "source";
    public static String rowType = "type";
    public static String rowUrl = "url";
    public static String rowUsed = "used";
    public static String rowWho = "who";


    private DBhelper helper;

    @Inject
    public DataDB(Context context) {
        helper = DBhelper.getIntance(context);
    }


    public void insert(List<DataEntities> dataEntitiesList) {
        DataEntities[] array = new DataEntities[dataEntitiesList.size()];
        dataEntitiesList.toArray(array);
        Flowable.fromArray(array)
                .observeOn(Schedulers.computation())
                .onBackpressureDrop()
                .subscribe(dataEntities -> insert(dataEntities));

    }

    public void insert(DataEntities entitie) {
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

            db.insert(tableName, null, values);
        }
    }

    public List<DataEntities> query(String type) {
        List<DataEntities> dataEntitiesList = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            dataEntitiesList = new ArrayList<>();
            try {

                Cursor cursor = db.query(tableName, null, rowType + " = ? ", new String[]{type},
                        null, null, rowPublishedAt + " desc ", "15");
                while (cursor.moveToNext()) {

                    DataEntities dataEntities = new DataEntities();

                    setData(cursor, dataEntities);

                    dataEntitiesList.add(dataEntities);

                }
            } catch (Exception e) {
                e.printStackTrace();
                dataEntitiesList = new ArrayList<>();
            }
        }
        return dataEntitiesList;
    }

    public List<DataEntities> query() {
        List<DataEntities> dataEntitiesList = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            dataEntitiesList = new ArrayList<>();
            try {
                Cursor cursor = db.query(tableName, null, null, null, null, null,
                        rowPublishedAt + " desc ", "15");
                while (cursor.moveToNext()) {

                    DataEntities dataEntities = new DataEntities();

                    setData(cursor, dataEntities);

                    dataEntitiesList.add(dataEntities);

                }
            } catch (Exception e) {
                e.printStackTrace();
                dataEntitiesList = new ArrayList<>();
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
