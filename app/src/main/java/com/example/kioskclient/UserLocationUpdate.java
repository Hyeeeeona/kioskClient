package com.example.kioskclient;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserLocationUpdate extends Service implements LocationListener {

    private Context mContext;
    Location location;
    double latitude, longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    protected LocationManager locationManager;
    UserLocationUpdate.GeoPoint geoPoint;

    public UserLocationUpdate(Context context) {
        // Required empty public constructor
        this.mContext = context;
        getLocation();

    }

    // 특정 위치의 경도, 위도 저장 클래스
    public class GeoPoint {
        double targetLatitude;
        double targetLongitude;

        public GeoPoint(float targetLatitude, float targetLongitude) {

            this.targetLatitude = targetLatitude;
            this.targetLongitude = targetLongitude;
        }
    }
    //현재 위치를 지정하는 함수
    public Location getLocation(){
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            //gps & 네트워크 사용이 가능한지 확인
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            //둘다 사용이 가능하면
            if(isGPSEnabled && isNetworkEnabled){
                //location permission 확인
                int hasFineLocaitonPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);
                int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);

                if(hasFineLocaitonPermission != PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED){
                    return null;
                }

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                if(locationManager != null){
                    //마지막에 검색한 location 가져온다
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(location != null){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }

                if(isGPSEnabled){
                    if(location == null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if(locationManager != null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location != null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }

            } else {
                return null;
            }

        } catch (Exception e){
            Log.d("@@@", ""+e.toString());
        }


        return location;
    }
    //gps 서비스 제거
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(UserLocationUpdate.this);
        }
    }

    // 특정 위치의 위도, 경도를 return하는 함수
    public GeoPoint findGeoPoint(String address) {
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA); //geocoder 생성
        Address addr;
        GeoPoint location = null;
        try {
            List<Address> listAddress = geocoder.getFromLocationName(address, 1); //listAddress에 받아온 주소값 넣어주기
            if (listAddress.size() > 0) { // 주소값이 존재 하면
                addr = listAddress.get(0); // Address형태로
                float targetLatitude = (float) (addr.getLatitude() * 1E6) / 1000000; //lat에 위도 값 받아오기
                float targetLongitude = (float) (addr.getLongitude() * 1E6) / 1000000; //lng에 경도 값 받아오기
                location = new GeoPoint(targetLatitude, targetLongitude);

                Log.i("TTT", "findGeoPoint: "+"주소로부터 취득한 위도 : " + targetLatitude / 1000000 + ", 경도 : " + targetLongitude / 10000000);
                //targetLatitude,Longitude가 소수점없이 받아오기 때문에 나누기를 해줘서 소수점으로 만든다.
            }
        } catch (IOException e) {
            Log.i("TTT", "findGeoPoint: 예외");
            e.printStackTrace();
        }
        return location;
    }
    //현재 위치의 한글 주소를 string으로 리턴하는 함수
    public String getCurrentAddress(double latitude, double longitude) throws IOException {
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);

        List<Address> addressList;

        try{
            addressList = geocoder.getFromLocation(latitude, longitude, 7);
        } catch (IllegalArgumentException e){
            Toast.makeText(mContext, "잘못된 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if(addressList == null || addressList.size() == 0){
            Toast.makeText(mContext, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address mAddress = addressList.get(0);
        StringBuffer strbuf = new StringBuffer();
        String buf;
        for (int i = 0; (buf = mAddress.getAddressLine(i)) != null; i++) {
            strbuf.append(buf + "\n");
        }

        return strbuf.toString();
    }

    public String getDistance(String address){
        /* 현재 위치를 기준으로 해당 가게와 거리 계산 / 직선 & 각도를 기준으로 거리 계산 */
        geoPoint = findGeoPoint(address);

        Location start = new Location("point A");
        start.setLatitude(35);//getLatitude());
        start.setLongitude(120);//getLongitude());
        Location end = new Location("point B");
        end.setLatitude(geoPoint.targetLatitude);
        end.setLongitude(geoPoint.targetLongitude);
        String distance = String.format("%.2f",start.distanceTo(end));
        Toast.makeText(mContext, "distance = " + distance, Toast.LENGTH_LONG).show();

        return distance;
    }

    public double getLatitude()
    {
        if(location != null)
        {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude()
    {
        if(location != null)
        {
            longitude = location.getLongitude();
        }

        return longitude;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
