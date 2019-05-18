package com.example.kioskclient;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityHome extends AppCompatActivity {
    private TextView mTextMessage;
    private FragmentManager fragmentManager;
    private FragementMenuHome fragmentMenuHome;
    private FragmentMenuSearch fragmentMenuSearch;
    private FragmentMenuCart fragmentMenuCart;
    private FragmentMenuMyPage fragmentMenuMyPage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.linear_layout,fragmentMenuHome).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_search:
                    transaction.replace(R.id.linear_layout,fragmentMenuSearch).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_shopping_cart:
                    transaction.replace(R.id.linear_layout,fragmentMenuCart).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_setting:
                    transaction.replace(R.id.linear_layout,fragmentMenuMyPage).commitAllowingStateLoss();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        fragmentManager = getSupportFragmentManager();
        fragmentMenuHome = new FragementMenuHome();
        fragmentMenuSearch = new FragmentMenuSearch();
        fragmentMenuCart = new FragmentMenuCart();
        fragmentMenuMyPage = new FragmentMenuMyPage();
        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.linear_layout,fragmentMenuHome).commitAllowingStateLoss();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
