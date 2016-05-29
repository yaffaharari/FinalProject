package com.example.yaffi.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.BatteryManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends android.content.BroadcastReceiver {


Cursor c;
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(MyListFragment.THE_ACTION)){
            Log.wtf("receiver broadCast", "receiver broadCast");
            if(MainActivity.getInstace()!=null){
                c = context.getContentResolver().query(PlacesContract.Places.CONTENT_URI, null, PlacesContract.Places.LAST_SEARCH + "=3", null, null);
                Log.wtf("hava is instance of avtivity", "yes");
                if(c==null){
                    Toast.makeText(context.getApplicationContext(), "no found values", Toast.LENGTH_SHORT).show();
                    Log.wtf("@@@@@no found values","no found values");
                }
                if(!MainActivity.getInstace().isLandLayout())
                MainActivity.getInstace().getSupportFragmentManager().beginTransaction().replace(R.id.containerListFrag,MyListFragment.newInstance(c)).commitAllowingStateLoss();
                else
                    MainActivity.getInstace().getSupportFragmentManager().beginTransaction().replace(R.id.containerLV,MyListFragment.newInstance(c)).commitAllowingStateLoss();
            }
            else{
                Log.wtf("hava is instance of avtivity","no");
            }
        }

        if(!intent.getAction().equals(MyListFragment.THE_ACTION)){
;
            final String TAG = "TGbattery";
            Log.wtf(TAG, "battery Receiver was called now");

            boolean batteryPowerOn = intent.getAction().equals(Intent.ACTION_POWER_CONNECTED);
            boolean batteryPowerOff = intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED);

            if(batteryPowerOn){
                Toast.makeText(context, "POWER_CONNECTED", Toast.LENGTH_LONG).show();
            }
            if(batteryPowerOff){
                Toast.makeText(context, "POWER_DISCONNECTED", Toast.LENGTH_LONG).show();
            }
        }
    }
}
