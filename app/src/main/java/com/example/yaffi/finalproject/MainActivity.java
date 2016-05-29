package com.example.yaffi.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyListFragment.OnItemClickInterface

{
    private static MainActivity mainActivityInstance;
    Cursor c;

    public static MainActivity  getInstace(){
        return mainActivityInstance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityInstance = this;
        c = getContentResolver().query(PlacesContract.Places.CONTENT_URI, null, PlacesContract.Places.LAST_SEARCH + "=3", null, null);

        if (savedInstanceState == null) {
            firstUp();
        } else {
           notFirstUp();
        }
    }

    public void firstUp() {
        if (!isLandLayout()) {
            getSupportFragmentManager().beginTransaction().add(R.id.containerMainFrag, new MyMainFragment()).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.containerListFrag, MyListFragment.newInstance(c)).commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.containerLV, MyListFragment.newInstance(c)).commit();
        }
    }
    public void notFirstUp() {
        if (!isLandLayout()){
            getSupportFragmentManager().beginTransaction().replace(R.id.containerMainFrag, new MyMainFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerListFrag, MyListFragment.newInstance(c)).commit();}
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.containerLV, MyListFragment.newInstance(c)).commit();
        }
    }

    public boolean isLandLayout() {
        View v = (View) findViewById(R.id.landLuyout);
        if (v == null)
            return false;
        return true;
    }

    @Override
    public void mOnItemClick(Place place) {
        Fragment myMapFragment = MyMapFragment.newInstance(place);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!isLandLayout())
            ft.replace(R.id.containerListFrag, myMapFragment).addToBackStack(null);
        else
            ft.replace(R.id.containerMap, myMapFragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
            Toast.makeText(MainActivity.this, "please click again if u want to exit", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().popBackStack();
            Log.wtf("@@@@@@ back stack entry count", "" + getSupportFragmentManager().getBackStackEntryCount());
            notFirstUp();
        } else {//if want exit from app
            Log.wtf("@@@@@@ back stack entry count", "" + getSupportFragmentManager().getBackStackEntryCount()+"exit");
            super.onBackPressed();
            }
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
               // Cursor cursor = getContentResolver().query(PlacesContract.Places.CONTENT_URI, null, PlacesContract.Places.FAVORATE + "=1", null, null);
                //if(!isLandLayout())
                //getSupportFragmentManager().beginTransaction().replace(R.id.fLayout,MyListFragment.newInstance(cursor)).commit();
                return true;
            case R.id.ExitId:
                System.exit(1);
        }
        return false;
    }


}