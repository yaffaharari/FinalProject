package com.example.yaffi.finalproject;


import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyService extends IntentService {

    private static final String TAG = "MyService";

    public static final String SEARCH_KEY="searchKey";
    public static final String SEARCH_BY_TEXT="isCheckBoxChected";
    //public static final String SEARCH_BY_LOCATION="isCheckBoxChected";
    public static final String THE_SENT_OF_BROADCAST1="BROADCAST_SERVICE_FINISH";

    Intent broadcastMessage;
    String json;
    MyContentProvider handler;
    Bitmap bitmap;
    int sizeCompress=100;

    public MyService(){
        super(TAG);
    }
    public MyService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
            broadcastMessage = new Intent(THE_SENT_OF_BROADCAST1);
            String query = intent.getStringExtra(SEARCH_KEY);
            boolean isSearchByText = intent.getBooleanExtra(SEARCH_BY_TEXT, false);
            //handler = new MyContentProvider(getApplicationContext());
            if (isSearchByText) {
                //search by text
                json = GoogleAccess.searchPlace(query);
                try {
                    JSONObject mainJson = new JSONObject(json);
                    JSONArray jsonArray = mainJson.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject currentObj = jsonArray.getJSONObject(i);
                        Place place = new Place(currentObj);
                        if(place.getUrlPic()==null){
                            place.setUrlPic("http://support.sapir.ac.il/files/2014/01/web-google-hangouts-icon.png");
                        }
                        bitmap = GoogleAccess.imgDownload(place.getUrlPic());
                        String strImg = GoogleAccess.encodeBitmapToBase64(bitmap, sizeCompress);
                        place.setStrBit(strImg);
                        //handler.insertPlace(place);
                        ContentValues values=new ContentValues();
                        values.put(PlacesContract.Places.NAME, place.getName());
                        values.put(PlacesContract.Places.ADDRESS,place.getAddress());
                        values.put(PlacesContract.Places.DISTANCE,place.getDistans());
                        values.put(PlacesContract.Places.LOCATION,place.getLocation());
                        values.put(PlacesContract.Places.PHOTO_URL,place.getUrlPic());
                        values.put(PlacesContract.Places.PICTURE,place.getStrBit());
                        getContentResolver().insert(PlacesContract.Places.CONTENT_URI,values);
                    }
                    LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else
                //search by location
                Toast.makeText(getApplicationContext(), "search by location", Toast.LENGTH_LONG).show();
        }
}
