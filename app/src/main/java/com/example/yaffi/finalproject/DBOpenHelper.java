package com.example.yaffi.finalproject;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql =
                " CREATE TABLE " + PlacesContract.Places.TABLE_NAME
                        + "("
                        + PlacesContract.Places._ID + " INTEGER PRIMARY KEY autoincrement ,"
                        + PlacesContract.Places.NAME + " TEXT ,"
                        + PlacesContract.Places.ADDRESS + " TEXT ,"
                        + PlacesContract.Places.DISTANCE + " REAL ,"
                        + PlacesContract.Places.LOCATION + " TEXT ,"
                        + PlacesContract.Places.PHOTO_URL + " TEXT ,"
                        + PlacesContract.Places.PICTURE + " TEXT "
                        + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql= "DROP TABLE IF EXISTS " + PlacesContract.Places.TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}

