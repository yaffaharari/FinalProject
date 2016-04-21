package com.example.yaffi.finalproject;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GoogleAccess {
    public static String URL1="https://maps.googleapis.com/maps/api/place/textsearch/json?query=";
    public static String KEY_FOR_SEARCH="&key=AIzaSyA__XbxVeWHTcfiR8OZXTKa25Qnc7v4DO4";
    public static String URL2="&sensor=false";

    static String searchPlace(String q)
    {
        q.replace(" ","+");
        String completeURL= URL1+q+URL2+KEY_FOR_SEARCH;
        BufferedReader input = null;
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(completeURL);
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
}

