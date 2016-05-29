package com.example.yaffi.finalproject;


import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MyService extends IntentService {

    private static final String TAG = "MyService";

    public static final String KEYWORD="keyword";
    public static final String SEARCH_BY_TEXT="isCheckBoxChected";
    public static final String THE_SENT_OF_BROADCAST1="BROADCAST_SERVICE_FINISH";
    public static final String EXSTRA_DATA_LATITUDE="latitude";
    public static final String EXSTRA_DATA_LONGITUDE="longitude";
    Double currentLatitude,currentLongitude,placeLat,placeLong;
    Intent broadcastMessage;
    String strJson;
    Bitmap bitmap;
    int searchFlag=3,sizeCompress=100;
    Place place;


    public MyService(){
        super(TAG);
    }
    public MyService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

            broadcastMessage = new Intent(THE_SENT_OF_BROADCAST1);
            Bundle exstras=intent.getExtras();
            String keyword = exstras.getString(KEYWORD);
            currentLatitude = exstras.getDouble(EXSTRA_DATA_LATITUDE);
            currentLongitude = exstras.getDouble(EXSTRA_DATA_LONGITUDE);
            boolean isSearchByText = intent.getBooleanExtra(SEARCH_BY_TEXT, false);

            if (isSearchByText) {
                strJson = GoogleAccess.searchByText(keyword);
                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastMessage);
                createPlaceFromJson(strJson);
            } else{//search by location+text
                strJson = GoogleAccess.searchPlaceByTextAndLoc(keyword, currentLatitude, currentLongitude);
                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastMessage);
                createPlaceFromJson(strJson);
            }
        }

    private void createPlaceFromJson(String currentJson) {
        try {
           JSONObject mainJson = new JSONObject(currentJson);
           JSONArray  jsonArray = mainJson.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currentObj = jsonArray.getJSONObject(i);
                place = new Place(currentObj);
                if(place.getAddress()==null)
                    place.setAddress(currentObj.getString("vicinity"));
               if (place.getUrlPic() == null)
                   place.setUrlPic("http://www.blanco-adv.co.il/img/social/instagram.png");
                bitmap = GoogleAccess.imgDownload(place.getUrlPic());
                String strImg = GoogleAccess.encodeBitmapToBase64(bitmap, sizeCompress);
                place.setStrBit(strImg);
                place.setFav(false);
                place.setLastSearchFlag(searchFlag);
                String placeLocation=place.getLocation();
                String []latLon=placeLocation.split(",");
                placeLat=Double.parseDouble(latLon[0]);
                placeLong=Double.parseDouble(latLon[1]);
                Double theDistans=GoogleAccess.haversine(placeLat,placeLong,currentLatitude,currentLongitude);
                place.setDistans(theDistans);
                insertPlaceToDB(place);
            }
            //LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void insertPlaceToDB(Place place) {
        ContentValues values=new ContentValues();
        values.put(PlacesContract.Places.NAME, place.getName());
        values.put(PlacesContract.Places.ADDRESS,place.getAddress());
        values.put(PlacesContract.Places.DISTANCE,place.getDistans());
        values.put(PlacesContract.Places.LOCATION,place.getLocation());
        values.put(PlacesContract.Places.PHOTO_URL,place.getUrlPic());
        values.put(PlacesContract.Places.PICTURE,place.getStrBit());
        values.put(PlacesContract.Places.FAVORATE,place.isFav());
        values.put(PlacesContract.Places.LAST_SEARCH,place.getLastSearchFlag());

        getContentResolver().insert(PlacesContract.Places.CONTENT_URI,values);
    }
}
