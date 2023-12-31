package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.newsapp.fragment.FeedFragment;
import com.example.newsapp.fragment.MessagesFragment;
import com.example.newsapp.fragment.MusicFragment;
import com.example.newsapp.fragment.NewsFragment;
import com.example.newsapp.fragment.Settings;
import com.example.newsapp.fragment.ShowUser;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_main extends AppCompatActivity {

    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
        InitControl();
    }

    private void InitControl() {
        //Global Declaration


        //Inside onCreate()

        sNavigationDrawer = findViewById(R.id.navigationDrawer);

        //Creating a list of menu Items

        List<MenuItem> menuItems = new ArrayList<>();

        //Use the MenuItem given by this library and not the default one.
        //First parameter is the title of the menu item and then the second parameter is the image which will be the background of the menu item.

        menuItems.add(new MenuItem("News",R.drawable.news_bg));
        menuItems.add(new MenuItem("Saved",R.drawable.feed_bg));

        menuItems.add(new MenuItem("User",R.drawable.music_bg));

        menuItems.add(new MenuItem("Settings",R.drawable.message_bg));

        //then add them to navigation drawer

        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass =  NewsFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }



        //Listener to handle the menu item click. It returns the position of the menu item clicked. Based on that you can switch between the fragments.

        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position "+position);

                switch (position){
                    case 0:{
                        fragmentClass = NewsFragment.class;
                        break;
                    }
                    case 1:{
                        fragmentClass = FeedFragment.class;
                        break;
                    }

                    case 2:{
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity_main.this);
                        String check = preferences.getString("Check_Email",null);
                        if(check!=null) {
                            fragmentClass = ShowUser.class;
                            break;
                        }
                        else fragmentClass = MusicFragment.class;
                        break;
                    }
                    case 3:{
                        fragmentClass = Settings.class;
                        break;
                    }

                }

                //Listener for drawer events such as opening and closing.
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State "+newState);
                    }
                });
            }
        });

    }
}