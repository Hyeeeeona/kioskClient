package com.example.kioskclient;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FragmentManager fragmentManager;
    private FragmentMenuHome fragmentMenuHome;
    private FragmentMenuCart fragmentMenuCart;
    private FragmentMenuMyPage fragmentMenuMyPage;
    private FragmentMenuOrderHistory fragmentMenuOrderHistory;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.linear_layout,fragmentMenuHome);
                    /*transaction.show(fragmentMenuHome);
                    transaction.hide(fragmentMenuCart);
                    transaction.hide(fragmentMenuMyPage);
                    transaction.hide(fragmentMenuOrderHistory);*/
                    transaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_order_history:
                    transaction.replace(R.id.linear_layout,fragmentMenuOrderHistory);
                    /*transaction.hide(fragmentMenuHome);
                    transaction.hide(fragmentMenuCart);
                    transaction.hide(fragmentMenuMyPage);
                    transaction.show(fragmentMenuOrderHistory);*/
                    transaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_shopping_cart:
                    transaction.replace(R.id.linear_layout,fragmentMenuCart);
                    /*transaction.hide(fragmentMenuHome);
                    transaction.show(fragmentMenuCart);
                    transaction.hide(fragmentMenuMyPage);
                    transaction.hide(fragmentMenuOrderHistory);*/
                    transaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_setting:
                    transaction.replace(R.id.linear_layout,fragmentMenuMyPage);
                   /* transaction.hide(fragmentMenuHome);
                    transaction.hide(fragmentMenuCart);
                    transaction.show(fragmentMenuMyPage);
                    transaction.hide(fragmentMenuOrderHistory);*/
                    transaction.commitAllowingStateLoss();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        fragmentManager = getSupportFragmentManager();
        fragmentMenuHome = new FragmentMenuHome();
        fragmentMenuCart = new FragmentMenuCart();
        fragmentMenuMyPage = new FragmentMenuMyPage();
        fragmentMenuOrderHistory = new FragmentMenuOrderHistory();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        /*transaction.add(R.id.linear_layout, fragmentMenuHome);
        transaction.add(R.id.linear_layout, fragmentMenuCart);
        transaction.add(R.id.linear_layout, fragmentMenuMyPage);
        transaction.add(R.id.linear_layout, fragmentMenuOrderHistory);
        transaction.hide(fragmentMenuCart);
        transaction.hide(fragmentMenuMyPage);
        transaction.hide(fragmentMenuOrderHistory);*/

        transaction.replace(R.id.linear_layout,fragmentMenuHome);
        transaction.commitAllowingStateLoss();
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    @Override
    public void onStart() {
        super.onStart();

        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(MainActivity.this, "현재 로그인된 사용자 : " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        }
    }
}