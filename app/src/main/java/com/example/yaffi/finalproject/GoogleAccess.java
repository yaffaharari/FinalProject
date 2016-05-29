package com.example.yaffi.finalproject;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GoogleAccess {

    //for search by text ======================================================
    public static String URL1="https://maps.googleapis.com/maps/api/place/textsearch/json?query=";
    public static String API_KEY="&key=AIzaSyA__XbxVeWHTcfiR8OZXTKa25Qnc7v4DO4";
    public static String URL2="&sensor=false";
    //=============================================================================
    //for search by text + location==============================================
    public static String URL11="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    public static String URL21="&radius=10000&types=restaurant|cafe|food|&name=";
    //================================================================================
    public static String searchPlace(URL url)
    {
        BufferedReader input = null;
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        try {
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                return null;
            }
            input = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));
            String line="";
            while ((line=input.readLine())!=null){
                response.append(line+"\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                connection.disconnect();
            }
        }
        return response.toString();
    }

    public static Bitmap imgDownload(String strUrl){
        strUrl.replace(" ", "+");
        URL url = null;
        try {
            url = new URL(strUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String encodeBitmapToBase64(Bitmap bitmap, int sizeCompress) {
        return Base64.encodeToString(compressBitmap(bitmap, sizeCompress), Base64.DEFAULT);
    }

    public static byte[]compressBitmap(Bitmap bmp, int compress){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, compress, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    public static String searchByText(String q){
        q.replace(" ","+");
        String completeURL= URL1+q+URL2+API_KEY;
        Log.wtf("search by text",completeURL);
        try {
            URL url = new URL(completeURL);
            String strResult=searchPlace(url);
            return strResult;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String searchPlaceByTextAndLoc(String q,double lat,double lon){
        String completeURL=URL11+lat+","+lon+URL21+q+API_KEY;
        Log.wtf("search by loc+text",completeURL);
        completeURL.replace(" ","+");
        try {
            URL url = new URL(completeURL);
            String strResult=searchPlace(url);
            return strResult;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static double haversine(double lat1, double lng1, double lat2, double lng2) {
        int r = 6371; // average radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = r * c;
        return d;
    }

}

