package com.example.yaffi.finalproject;


        import android.content.Context;
        import android.content.Intent;
        import android.os.Build;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.os.Bundle;
        import android.support.v4.app.ListFragment;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.ContextMenu;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;

        import com.google.android.gms.common.api.GoogleApiClient;

        import java.lang.reflect.Field;
        import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements MyListFragment.OnItemClickInterface

{
    Boolean isFirstUp;
    GoogleApiClient client;

    private static MainActivity mainActivityRunningInstance;

    public static MainActivity  getInstace(){
        return mainActivityRunningInstance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isLargLayout()) {
            isFirstUp=true;
            mainActivityRunningInstance=this;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.mainFLayout, new MyListFragment());
            ft.commit();
        } else {
           /* ft=getFragmentManager().beginTransaction();
            ft.add(R.id.rightFrgmentUpInLarg,new MyMainFragment()).addToBackStack(null);
            ft.add(R.id.rightFrgmentDownInLarg,new MyListFragment()).addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();*/
        }
    }

    public boolean isLargLayout() {
        View v = (View) findViewById(R.id.largLayout);
        if (v == null) {
            return false;
        }
        return true;
    }

    @Override
    public void mOnItemClick(Place place) {
        Fragment myMapFragment = MyMapFragment.newInstance(place);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFLayout, myMapFragment).addToBackStack(null);
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
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
            case R.id.search_placeId:
                Fragment mainFrag=new MyMainFragment();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction().replace(R.id.mainFLayout, mainFrag);
                ft.commit();
                return true;
            case R.id.favouriteId:
                return true;
            case R.id.recent_searchId:
        }
        return false;
    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        invokeFragmentManagerNoteStateNotSaved();
    }*/

  /*  @SuppressWarnings({ "rawtypes", "unchecked" })
    private void invokeFragmentManagerNoteStateNotSaved() {
        *//**
 * For post-Honeycomb devices
 *//*
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        try {
            Class cls = getClass();
            do {
                cls = cls.getSuperclass();
            } while (!"Activity".equals(cls.getSimpleName()));
            Field fragmentMgrField = cls.getDeclaredField("mFragments");
            fragmentMgrField.setAccessible(true);

            Object fragmentMgr = fragmentMgrField.get(this);
            cls = fragmentMgr.getClass();

            Method noteStateNotSavedMethod = cls.getDeclaredMethod("noteStateNotSaved", new Class[] {});
            noteStateNotSavedMethod.invoke(fragmentMgr, new Object[] {});
            Log.d("DLOutState", "Successful call for noteStateNotSaved!!!");
        } catch (Exception ex) {
            Log.e("DLOutState", "Exception on worka FM.noteStateNotSaved", ex);
        }
    }*/

}