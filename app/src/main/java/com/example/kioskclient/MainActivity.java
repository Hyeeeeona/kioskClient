package com.example.kioskclient;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FragmentManager fragmentManager;
    private FragmentMenuHome fragmentMenuHome;
    private FragmentMenuCart fragmentMenuCart;
    private FragmentMenuMyPage fragmentMenuMyPage;
    private FragmentMenuOrderHistory fragmentMenuOrderHistory;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


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


        ApplicationController application = ApplicationController.getInstance();
        application.buildNetworkService( "10.0.2.2", 8000);

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

    // 앱 실행 시 gps 서비스 활성화를 위해 출력되는 dialog
    public void showDialogForLocationServiceSetting(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("위치 서비스 활성화 필요");
        builder.setMessage("위치 서비스가 비활성화 되어 있습니다."+ "\n" + "위치 설정을 변경할까요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent callGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                //맨 하단의 onActivityResult에 결과 전송
                startActivityForResult(callGPS, GPS_ENABLE_REQUEST_CODE);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    // 현재 location service와 gps 서비스가 실행되고 있는지 확인
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //현재 런타임 퍼미션 확인을 위함 함수
    public void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)

            // 3.  위치 값을 가져올 수 있음

        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);

            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    // startActivityForResult 함수 실행 시 resultCode를 받아서 상태 확인하는 부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

}