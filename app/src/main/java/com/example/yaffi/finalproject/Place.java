package com.example.yaffi.finalproject;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Place {

    private String name;
    private String address;
    private String location;
    private double distans;
    private String urlPic;
    private String strBit;
    private String id;
    private boolean fav;
    Double latitude,longitude;
    private int lastSearchFlag;
    //===========constractor 1=========
    public Place(JSONObject jsonObject){
        try {
            //this.id=jsonObject.getString("place_id");
            this.name=jsonObject.getString("name");
            latitude=jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
            longitude=jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
            this.location=String.valueOf(latitude)+","+String.valueOf(longitude);
            JSONArray jsonArrayPhotos=jsonObject.getJSONArray("photos");
            JSONObject photo1 = jsonArrayPhotos.getJSONObject(0);
            String strUrl = photo1.getString("photo_reference");
            this.urlPic="https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photoreference=" + strUrl + GoogleAccess.API_KEY;
            this.address=jsonObject.getString("formatted_address");
            if(this.getAddress()== null){
                this.address=jsonObject.getString("vicinity");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //===========constractor 2=========
    public Place(String name, String address, String location, double distans, String urlPic){
        this.name=name;
        this.address=address;
        this.location=location;
        this.distans=distans;
        this.urlPic=urlPic;
    }
    //============getter & setter=========
    public String getAddress() {
        return address;}
    public void setAddress(String address) {
        this.address = address;}
    public double getDistans() {
        return distans;}
    public void setDistans(double distans) {
        this.distans = distans;}
    public String getName() {
        return name;}
    public void setName(String name) {
        this.name = name;}
    public String getUrlPic() {
        return urlPic;}
    public void setUrlPic(String urlPic) {
        this.urlPic = urlPic;}
    public String getLocation() {
        return location;}
    public void setLocation(String location) {
        this.location = location;}
    public String getStrBit() {
        return strBit;}
    public void setStrBit(String strBit) {
        this.strBit = strBit;}
  /*  public String getId() {
        return id;}
    public void setId(String id) {
        this.id = id;}*/
    public boolean isFav() {
        return fav;}
    public void setFav(boolean fav) {
        this.fav = fav;}
    public int getLastSearchFlag() {
        return lastSearchFlag;}
    public void setLastSearchFlag(int lastSearchFlag) {
        this.lastSearchFlag = lastSearchFlag;}
    //=======

    //===//==//==//
    //JSONObject geometryObj=new JSONObject(jsonObject.getString("geometry"));
    //JSONObject locationObj=new JSONObject(geometryObj.getString("location"));
    // JSONObject geometryObj=jsonObject.getJSONObject("geometry");
    //JSONObject locationObj=geometryObj.getJSONObject("location");
    //String lat=locationObj.getString("lat");
    //String lng=locationObj.getString("lng");
    //===//==//==//
}
