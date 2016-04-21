package com.example.yaffi.finalproject;

import android.annotation.TargetApi;
import android.app.Activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

import java.lang.reflect.Field;

public class MyListFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String THE_ACTION=MyService.THE_SENT_OF_BROADCAST1;
    ListView listView;
    Cursor c;

    MyCursorAdapter adapter;
    OnItemClickInterface activ;
    IntentFilter filter;

    public static final String SEARCH_EXISTS="searchExsits";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.list_fragment,container,false);
        return v;
    }

   /*  public static MyListFragment newInstance(Boolean isSearchExists) {

        Bundle args = new Bundle();
        args.putBoolean(SEARCH_EXISTS, isSearchExists);
        MyListFragment fragment = new MyListFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=(ListView)view.findViewById(R.id.listView);
        //Boolean searchExists=getArguments().getBoolean(SEARCH_EXISTS);
        //c=getActivity().getContentResolver().query(PlacesContract.Places.CONTENT_URI, null, null,null, null);
        refresh();
        filter = new IntentFilter(THE_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new MyBroadcastReceiver(), filter);
        }
    public void refresh(){
        c=getActivity().getContentResolver().query(PlacesContract.Places.CONTENT_URI, null, null,null, null);
        adapter = new MyCursorAdapter(getActivity(), c);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        c.moveToPosition(position);
        String theName = c.getString(c.getColumnIndex(PlacesContract.Places.NAME));
        String theAddress = c.getString(c.getColumnIndex(PlacesContract.Places.ADDRESS));
        String theLocation = c.getString(c.getColumnIndex(PlacesContract.Places.LOCATION));
        double theDistans = c.getDouble(c.getColumnIndex(PlacesContract.Places.DISTANCE));
        String theUrlPic = c.getString(c.getColumnIndex(PlacesContract.Places.PHOTO_URL));
        c.close();
        Place p = new Place(theName, theAddress, theLocation, theDistans, theUrlPic);
        activ.mOnItemClick(p);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activ=(OnItemClickInterface)activity;
    }

   /* @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }*/

    public interface OnItemClickInterface{
        void mOnItemClick(Place place);
    }

    class MyCursorAdapter extends CursorAdapter {

        public MyCursorAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater= LayoutInflater.from(getActivity());
            View retView=inflater.inflate(R.layout.item_fragment,parent,false);
            return retView;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView name=(TextView) view.findViewById(R.id.nameId);
            ImageButton imageView=(ImageButton)view.findViewById(R.id.imageButton);
            TextView address=(TextView)view.findViewById(R.id.addressId);
            TextView distans=(TextView)view.findViewById(R.id.distansId);

            String strName= cursor.getString(cursor.getColumnIndex(PlacesContract.Places.NAME));
            String strAddress=cursor.getString(cursor.getColumnIndex(PlacesContract.Places.ADDRESS));
            String strDistans=cursor.getString(cursor.getColumnIndex(PlacesContract.Places.DISTANCE));
            String strBit=cursor.getString(cursor.getColumnIndex(PlacesContract.Places.PICTURE));
            Bitmap bmp=GoogleAccess.decodeBase64(strBit);

            name.setText(strName);
            address.setText(strAddress);
            distans.setText(strDistans);
            imageView.setImageBitmap(bmp);
        }
    }
  /*    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MyListFragment.THE_ACTION)){
                Log.wtf("fffffffffffffff", "dddddddddddddddddddddd");
                //context.getContentResolver().delete(Con)
                *//*ft=getChildFragmentManager().beginTransaction();
                ft.replace(R.id.mainFLayout,new ListFragment());
                ft.commit();*//*
                //c=getActivity().getContentResolver().query(PlacesContract.Places.CONTENT_URI, null, null,null, null);
                //refresh();
            }
        }
    }*/
}

