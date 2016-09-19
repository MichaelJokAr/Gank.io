package org.jokar.gankio.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JokAr on 16/9/18.
 */
public class DBhelper extends SQLiteOpenHelper {
    private static final String name = "gankio";
    private static final int version = 2;
    private static volatile DBhelper instance;

    public DBhelper(Context context) {
        super(context, name, null, version);
    }

    private final String SEARCHTABLE = "CREATE TABLE "
            + SearchDB.tableName + " ( "
            + SearchDB.rowGanhuo_id + " TEXT NOT NULL PRIMARY KEY, "
            + SearchDB.rowDesc + " TEXT, "
            + SearchDB.rowPublishedAt + " TEXT, "
            + SearchDB.rowReadability + " TEXT, "
            + SearchDB.rowType + " TEXT NOT NULL, "
            + SearchDB.rowUrl + " TEXT, "
            + SearchDB.rowWho + " TEXT);";

    private final String DATATABLE = "CREATE TABLE "
            + DataDB.tableName + " ( "
            + DataDB.row_id + " TEXT NOT NULL PRIMARY KEY, "
            + DataDB.rowCreatedAt + " TEXT, "
            + DataDB.rowDesc + " TEXT, "
            + DataDB.rowPublishedAt + " TEXT, "
            + DataDB.rowSource + " TEXT, "
            + DataDB.rowType + " TEXT, "
            + DataDB.rowUrl + " TEXT, "
            + DataDB.rowUsed + " INTEGER, "
            + DataDB.rowWho + " TEXT);";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SEARCHTABLE);
        sqLiteDatabase.execSQL(DATATABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,  int oldVersion, int newVersion) {
        if(newVersion == 2){
            sqLiteDatabase.execSQL(DATATABLE);
        }
    }

    public static DBhelper getIntance(Context context) {

        if (instance == null) {

            synchronized (DBhelper.class) {
                if (instance == null) {
                    instance = new DBhelper(context.getApplicationContext());
                }
            }
        }
        return instance;
    }
}
