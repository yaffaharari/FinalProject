package com.example.yaffi.finalproject;

import android.annotation.TargetApi;
import android.app.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;


public class MyListFragment extends Fragment implements AdapterView.OnItemClickListener
        ,AdapterView.OnItemLongClickListener
{
    public static final String THE_ACTION = MyService.THE_SENT_OF_BROADCAST1;
    ListView listView;
    Cursor c;

    MyCursorAdapter adapter;
    OnItemClickInterface activ;
    IntentFilter filter;

    public static MyListFragment newInstance(Cursor c) {

        MyListFragment fragment = new MyListFragment();
        fragment.setCursor(c);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.list_fragment, container, false);
        return v;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filter = new IntentFilter(THE_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new MyBroadcastReceiver(), filter);
        listView = (ListView) view.findViewById(R.id.listView);
        //getActivity().getLoaderManager().initLoader(0,null,(android.app.LoaderManager.LoaderCallbacks<Cursor>) this);
        adapter = new MyCursorAdapter(getActivity(), getCursor());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    protected Cursor getCursor() {
        return c;
    }

    private void setCursor( Cursor cursor ) {
        c=cursor;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         c = getActivity().getContentResolver().query(
                Uri.withAppendedPath(PlacesContract.Places.CONTENT_URI,
                        String.valueOf(id)), null, null, null, null);
        if (c.moveToFirst()) {
            String theName = c.getString(c.getColumnIndex(PlacesContract.Places.NAME));
            String theAddress = c.getString(c.getColumnIndex(PlacesContract.Places.ADDRESS));
            String theLocation = c.getString(c.getColumnIndex(PlacesContract.Places.LOCATION));
            double theDistans = c.getDouble(c.getColumnIndex(PlacesContract.Places.DISTANCE));
            String theUrlPic = c.getString(c.getColumnIndex(PlacesContract.Places.PHOTO_URL));
            Place selectedPlace = new Place(theName, theAddress, theLocation, theDistans, theUrlPic);
            activ.mOnItemClick(selectedPlace);
        }
        c.close();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activ = (OnItemClickInterface) activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      /*  if(!getCursor().isClosed())
            getCursor().close();*/
    }

    //===========ContextMenu============
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_manu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.addItemToFav:
                String currentId=String.valueOf(info.id);
                ContentValues values=new ContentValues();
                values.put(PlacesContract.Places.FAVORATE, true);
                int num=getActivity().getContentResolver().update(
                        Uri.withAppendedPath(PlacesContract.Places.CONTENT_URI,currentId),
                        values,
                        null,
                        null);
                Log.wtf("num row that update", String.valueOf(num));
                return true;
            case R.id.sharePlaceWithId:
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.setType("text/plain");
                intent1.putExtra(Intent.EXTRA_TEXT, "The status update text");
                startActivity(Intent.createChooser(intent1, "Select prefered Service"));
                return true;
        }
        return super.onContextItemSelected(item);
    }
    //======end of ContextMenu=========================
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        registerForContextMenu(listView);
        return false;
    }
    public interface OnItemClickInterface {
        void mOnItemClick(Place place);
    }

//=============================MyCursorAdapter class=============================================================
//============================================================================================
    public static class MyCursorAdapter extends CursorAdapter {

        public MyCursorAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View retView = inflater.inflate(R.layout.item_fragment, parent, false);
            return retView;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView name = (TextView) view.findViewById(R.id.nameId);
            ImageButton imageView = (ImageButton) view.findViewById(R.id.imageButton);
            TextView address = (TextView) view.findViewById(R.id.addressId);
            TextView distans = (TextView) view.findViewById(R.id.distansId);

            String strName = cursor.getString(cursor.getColumnIndex(PlacesContract.Places.NAME));
            String strAddress = cursor.getString(cursor.getColumnIndex(PlacesContract.Places.ADDRESS));
            String strDistans = cursor.getString(cursor.getColumnIndex(PlacesContract.Places.DISTANCE));
            String strBit = cursor.getString(cursor.getColumnIndex(PlacesContract.Places.PICTURE));
            Bitmap bmp = GoogleAccess.decodeBase64(strBit);

            name.setText(strName);
            address.setText(strAddress);
            distans.setText(strDistans);
            imageView.setImageBitmap(bmp);
        }
    }
}
