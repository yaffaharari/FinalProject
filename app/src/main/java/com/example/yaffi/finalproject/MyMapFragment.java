package com.example.yaffi.finalproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMapFragment extends Fragment implements OnMapReadyCallback {



    public static final String pName = "placeName";
    public static final String pAddress = "placeAddress";
    public static final String pLocation = "placeLocation";
    public static final String pDistans = "placeDistans";
    private GoogleMap mGoogleMap;

    int maxValue=2;
    String currentName ,currentLocation;
    String [] latLon=new String[maxValue];
    double lat, lon;
    SupportMapFragment mapFragment;

    public static MyMapFragment newInstance(Place p) {

        Bundle args = new Bundle();
        args.putString(pName, p.getName());
        args.putString(pAddress, p.getAddress());
        args.putString(pLocation, p.getLocation());
        args.putDouble(pDistans, p.getDistans());
        MyMapFragment fMapWithPlace = new MyMapFragment();
        fMapWithPlace.setArguments(args);

        return fMapWithPlace;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arg=getArguments();

        currentName= arg.getString(pName);
        //String currentAddress=arg.getString(pAddress);
        currentLocation=arg.getString(pLocation);
        //double currentDistans=arg.getDouble(pDistans);
        latLon=currentLocation.split(",");
        lat=Double.parseDouble(latLon[0]);
        lon=Double.parseDouble(latLon[1]);
        mapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;
        mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lon))
                .title(currentName));
        MarkerOptions m=new MarkerOptions();
        //CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon), 15);
        //mGoogleMap.moveCamera(update);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 15));
    }

}

