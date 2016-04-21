package com.example.yaffi.finalproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.service.carrier.CarrierMessagingService;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class MyMainFragment extends Fragment implements com.google.android.gms.location.LocationListener, GoogleApiClient.OnConnectionFailedListener {

    EditText searchET;
    Button searchBtn;
    Intent intent;
    String strSearch;
    CheckBox checkBox;
    GoogleApiClient mGoogleApiClient;

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
        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSearch = searchET.getText().toString();
                intent = new Intent(getActivity(), MyService.class);
                intent.putExtra(MyService.SEARCH_KEY, strSearch);
                if (checkBox.isChecked()) {
                    intent.putExtra(MyService.SEARCH_BY_TEXT, false);//SEARCH_BY_LOCATION
//=======================permisstion check====
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        Log.wtf("have the permisstion","have the permisstion");
                        return;
                    }
 //======================if permisstion checked===========
                    PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                            .getCurrentPlace(mGoogleApiClient, null);
                    Log.wtf("wwwwww","wwwwwwww");
                    result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                        @Override
                        public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                            for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                                Log.wtf("kkkk", String.format("Place '%s' has likelihood: %g",
                                        placeLikelihood.getPlace().getName(),
                                        placeLikelihood.getLikelihood()));
                            }
                            likelyPlaces.release();
                        }
                    });
//============================end of current place===========
                }
                else
                    intent.putExtra(MyService.SEARCH_BY_TEXT,true);

               // getActivity().getContentResolver().delete(PlacesContract.Places.CONTENT_URI,null,null);
              //  getActivity().startService(intent);
        }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        Double latitude= location.getLatitude();
        Double longitude= location.getLongitude();
        Log.wtf("kkkkkkkkkk", "sssssssssss");
        //getActivity().startService(intent);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "faild to connection", Toast.LENGTH_SHORT).show();
    }
}

