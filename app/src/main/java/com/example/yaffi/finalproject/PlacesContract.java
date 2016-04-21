package com.example.yaffi.finalproject;

import android.net.Uri;

public class PlacesContract {

    public static final int DB_VERTION = 1;
    public static final String DB_NAME = "places.db";

    public static class Places{

        public static String TABLE_NAME = "places";
        public static String _ID = "_id";
        public static String NAME = "name";
        public static String ADDRESS = "address";
        public static String DISTANCE = "distance";
        public static String LOCATION = "location";
        public static String PHOTO_URL = "photoUrl";
        public static String PICTURE = "picture";

        public static final int STATUS_DIR = 1;
        public static final int STATUS_ITEM_ID = 2;

        public static final String AUTHORITY = "com.example.yaffi.finalproj";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    }


}
