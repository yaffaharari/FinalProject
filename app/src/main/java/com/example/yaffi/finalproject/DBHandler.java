package com.example.yaffi.finalproject;/*
package com.example.yaffi.finalproj;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHandler {

    private DBOpenHelper dbhelper;

    public DBHandler(Context context) {
        dbhelper = new DBOpenHelper(context, PlacesContract.DB_NAME,null,PlacesContract.DB_VERTION);
    }

    SQLiteDatabase db;

    public  void insertPlace(Place place){
        db = dbhelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(PlacesContract.Places.NAME, place.getName());
        contentValues.put(PlacesContract.Places.ADDRESS,place.getAddress());
        contentValues.put(PlacesContract.Places.DISTANCE,place.getDistans());
        contentValues.put(PlacesContract.Places.LOCATION,place.getLocation());
        contentValues.put(PlacesContract.Places.PHOTO_URL,place.getUrlPic());
        contentValues.put(PlacesContract.Places.PICTURE,place.getStrBit());
        db.insert(PlacesContract.Places.TABLE_NAME,null,contentValues);

    }
    public Cursor getAllPlaces(){
        db=dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + PlacesContract.Places.TABLE_NAME , null);
        return cursor;
    }


}



*/
