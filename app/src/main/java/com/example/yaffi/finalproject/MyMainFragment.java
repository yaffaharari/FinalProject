package com.example.yaffi.finalproject;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import java.util.List;


public class MyMainFragment extends Fragment {

    EditText searchET;
    Button searchBtn;
    Intent intent;
    String strSearch;
    CheckBox checkBox;

    Location location;
    Criteria criteria;
    LocationManager locationManager;
    String provider;

    Double latitude, longitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        searchET = (EditText) v.findViewById(R.id.searchET);
        searchBtn = (Button) v.findViewById(R.id.searchBtn);
        checkBox = (CheckBox) v.findViewById(R.id.cbId);
        //buidGoogleApiClient();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strSearch = searchET.getText().toString();
                location = searchByLcn();
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                intent = new Intent(getActivity(), MyService.class);
                intent.putExtra(MyService.KEYWORD, strSearch);
                intent.putExtra(MyService.EXSTRA_DATA_LATITUDE, latitude);
                intent.putExtra(MyService.EXSTRA_DATA_LONGITUDE, longitude);
                if (checkBox.isChecked()) {
                    intent.putExtra(MyService.SEARCH_BY_TEXT, false);//SEARCH_BY_LOCATION
                } else//SEARCH_BY_TEXT
                    intent.putExtra(MyService.SEARCH_BY_TEXT, true);
                ContentValues values=new ContentValues();
                values.put(PlacesContract.Places.LAST_SEARCH,2);
                getActivity().getContentResolver().update(PlacesContract.Places.CONTENT_URI, values, PlacesContract.Places.FAVORATE +"=1",null);
                int rowDalet=getActivity().getContentResolver().delete(PlacesContract.Places.CONTENT_URI, PlacesContract.Places.LAST_SEARCH +"=3", null);
                Log.wtf("row that dalete",String.valueOf(rowDalet));
                getActivity().startService(intent);
            }
        });
    }

    private Location searchByLcn() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            Log.wtf("last known location, provider:" + provider, "location: %s" + l);
            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                Log.wtf("found best last known location: %s"+l, "kkkk");
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }

}
/*
        Log.wtf("ff", provider);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return locationManager.getLastKnownLocation(provider);
    }
}

/*if(provider==null){
        List<String> lProviders = locationManager.getProviders(false);
        for(int i = 0; i < lProviders.size(); i++){
        Log.d("LocationActivity", lProviders.get(i));
        provider=lProviders.get(i);
        break;
        }
        }*/
