package org.jokar.gankio.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jokar.gankio.model.entities.SearchEntities;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by JokAr on 16/9/18.
 */
public class SearchDB {

    public static String tableName = "search";
    public static String rowGanhuo_id = "ganhuo_id";
    public static String rowDesc = "desc";
    public static String rowPublishedAt = "publishedAt";
    public static String rowReadability = "readability";
    public static String rowType = "type";
    public static String rowUrl = "url";
    public static String rowWho = "who";

    private DBhelper helper;

    @Inject
    public SearchDB(Context context) {
        helper = DBhelper.getIntance(context);
    }

    public void insert(List<SearchEntities> searchEntities) {
        SearchEntities[] objects = new SearchEntities[searchEntities.size()];
        searchEntities.toArray(objects);
       Flowable.fromArray(objects)
               . observeOn(Schedulers.computation())
                .onBackpressureDrop()
                .subscribe(this::insert);

    }

    public void insert(SearchEntities searchEntities) {
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()) {

            ContentValues values = new ContentValues();
            values.put(rowGanhuo_id, searchEntities.getGanhuo_id());
            values.put(rowDesc, searchEntities.getDesc());
            values.put(rowPublishedAt, searchEntities.getPublishedAt());
            values.put(rowReadability, "");
            values.put(rowType, searchEntities.getType());
            values.put(rowUrl, searchEntities.getUrl());
            values.put(rowWho, searchEntities.getWho());
            db.insert(tableName, null, values);

        }
    }


    public List<SearchEntities> query(String type) {
        ArrayList<SearchEntities> searchEntities = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            searchEntities = new ArrayList<>();
            Cursor cursor = db.query(tableName, null, rowType + " = ? ", new String[]{type},
                    null, null, rowPublishedAt + " desc ", "15");
            while (cursor.moveToNext()) {
                SearchEntities searchEntitie = new SearchEntities();
                setData(cursor, searchEntitie);

                searchEntities.add(searchEntitie);
            }
        }

        return searchEntities;
    }

    public List<SearchEntities> queryAll() {
        ArrayList<SearchEntities> searchEntities = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            searchEntities = new ArrayList<>();
            Cursor cursor = db.query(tableName, null, null, null,
                    null, null, rowPublishedAt + " desc ", "15");
            while (cursor.moveToNext()) {
                SearchEntities searchEntitie = new SearchEntities();
                setData(cursor, searchEntitie);

                searchEntities.add(searchEntitie);
            }
        }

        return searchEntities;
    }

    private void setData(Cursor cursor, SearchEntities searchEntitie) {
        searchEntitie.setGanhuo_id(cursor.getString(cursor.getColumnIndex(rowGanhuo_id)));
        searchEntitie.setDesc(cursor.getString(cursor.getColumnIndex(rowDesc)));
        searchEntitie.setPublishedAt(cursor.getString(cursor.getColumnIndex(rowPublishedAt)));
        searchEntitie.setReadability(cursor.getString(cursor.getColumnIndex(rowReadability)));
        searchEntitie.setType(cursor.getString(cursor.getColumnIndex(rowType)));
        searchEntitie.setUrl(cursor.getString(cursor.getColumnIndex(rowUrl)));
        searchEntitie.setWho(cursor.getString(cursor.getColumnIndex(rowWho)));
    }

}
