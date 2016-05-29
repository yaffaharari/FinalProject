package com.example.yaffi.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements MyListFragment.OnItemClickInterface{

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        cursor = getContentResolver().query(PlacesContract.Places.CONTENT_URI, null, PlacesContract.Places.FAVORATE + "=1", null, null);
        getSupportFragmentManager().beginTransaction().add(R.id.containerListView, MyListFragment.newInstance(cursor)).commit();

    }
    @Override
    public void mOnItemClick(Place place) {
        Fragment myMapFragment = MyMapFragment.newInstance(place);
        getSupportFragmentManager().beginTransaction().replace(R.id.containerListView, myMapFragment).commit();
    }

    //==========OptionsMenu===========
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.favouriteId:
                Intent intent=new Intent(this,Main2Activity.class);
                startActivity(intent);
                return true;
            case R.id.deleteAllFavId:
                int row =getContentResolver().delete(PlacesContract.Places.CONTENT_URI, PlacesContract.Places.FAVORATE + "=1", null);
                Toast.makeText(Main2Activity.this, "All entries deleted successfully" + row + "row delete", Toast.LENGTH_SHORT).show();
                Log.wtf("@@@@@@@@@@row delete", String.valueOf(row));
                return true;
        }
        return false;
    }
}
