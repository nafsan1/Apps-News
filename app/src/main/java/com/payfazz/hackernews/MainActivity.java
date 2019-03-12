package com.payfazz.hackernews;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.payfazz.hackernews.fragment.FavFragment;
import com.payfazz.hackernews.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            int id = item.getItemId();
            if (id == R.id.navigation_home){
                fragment = new HomeFragment();
                setActionBar("News");
            }else if (id == R.id.navigation_fav){
                fragment = new FavFragment();
                setActionBar("Favourite News");
            }
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_main, fragment)
                        .commit();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null){
            HomeFragment homeFragment = new HomeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fl_main, homeFragment);
            fragmentTransaction.commit();
            setActionBar("News");
        }
    }
    private void setActionBar(String title){


            ActionBar actionBar = (this).getSupportActionBar();
            if (actionBar != null) {

                // Update title actionbar-nya
                actionBar.setTitle(title);
            }
        }
    }

