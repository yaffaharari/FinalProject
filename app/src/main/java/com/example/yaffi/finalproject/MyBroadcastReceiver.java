package com.example.yaffi.finalproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.widget.Toast;


public class MyBroadcastReceiver extends android.content.BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(MyListFragment.THE_ACTION)){
            Log.wtf("fffffffffffffff", "dddddddddddddddddddddd");
            //context.getContentResolver().delete(Con)
            /*Fragment listFrag=new MyListFragment();
            Intent i=new Intent("yaffa");
            context.sendBroadcast(i);*/
            if(MainActivity.getInstace()!=null){
                Log.wtf("rrrrrrrrrrrrrrrrrrrrrrrrrrrr","rrrrrrrrrrrrrrrrrrrrrrrr");
             //   MainActivity.getInstace().getSupportFragmentManager().beginTransaction().replace(R.id.mainFLayout,new MyListFragment()).commitNowAllowingStateLoss();
            }
            else{
                Log.wtf("dddddddddddd","yyyyyyyyyyyyyyyyyyyyyy");
            }
        }
    }
}
