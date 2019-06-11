package com.example.kioskclient;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMenuHome extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button searchBtn, backHomeBtn, deleteBtn;
    ImageButton locationBtn;
    EditText searchText;
    TextView userSearchText, listText, resultNoRest, nowLocation;
    ListView listItems, favoriteItems;
    ListViewSearchAdapter adapter;
    UserLocationUpdate.GeoPoint geoPoint;
    FavoriteListViewAdapter favoriteListViewAdapter;

    private UserLocationUpdate userLocationUpdate;
    private Context mContext;

    public FragmentMenuHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_home, container, false);
        mContext = container.getContext();

        searchBtn = (Button) view.findViewById(R.id.searchBtn);
        backHomeBtn = (Button) view.findViewById(R.id.backHomeBtn);
        deleteBtn = (Button) view.findViewById(R.id.deleteBtn);
        searchText = (EditText) view.findViewById(R.id.searchText);
        listItems = (ListView) view.findViewById(R.id.listItems);
        favoriteItems = (ListView) view.findViewById(R.id.favoriteItems);
        userSearchText = (TextView) view.findViewById(R.id.userSearchText);
        resultNoRest = (TextView) view.findViewById(R.id.resultNoRest);
        listText = (TextView) view.findViewById(R.id.listText);
        locationBtn = (ImageButton) view.findViewById(R.id.now_location);
        nowLocation = (TextView) view.findViewById(R.id.now_location_text);


        if (!((MainActivity)mContext).checkLocationServicesStatus()) {
            ((MainActivity)mContext).showDialogForLocationServiceSetting();
        }else {
            ((MainActivity)mContext).checkRunTimePermission();
        }

        userLocationUpdate = new UserLocationUpdate(mContext);

        adapter = new ListViewSearchAdapter();
        listItems.setAdapter(adapter);

        favoriteListViewAdapter = new FavoriteListViewAdapter();
        favoriteItems.setAdapter(favoriteListViewAdapter);

        for(int i = 0; i < 10; i++){
            adapter.addItem("example XX점", (i+1.0), getResources().getDrawable(R.drawable.star_score), "garlic, fried chicken");
            adapter.addItem("test YY점", (i+1.0), getResources().getDrawable(R.drawable.star_score), "pizza, potato");
            favoriteListViewAdapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px),
                    "매장"+ i, "아메리카노" +i);
        }
        searchText.setText("");

        backHomeBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        listItems.setOnItemClickListener(this);
        favoriteItems.setOnItemClickListener(this);
        locationBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backHomeBtn){
            getActivity().findViewById(R.id.navigation_home).performClick();
        } else if(v.getId() == R.id.deleteBtn){
            // 검색 후 x 버튼 클릭 시
            backHomeBtn.setVisibility(View.VISIBLE);
            userSearchText.setVisibility(View.GONE);
            searchText.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.GONE);
            searchBtn.setVisibility(View.VISIBLE);
            listItems.setVisibility(View.GONE);
            favoriteItems.setVisibility(View.VISIBLE);
            resultNoRest.setVisibility(View.GONE);
            listText.setText("즐겨찾기 목록");

            searchText.setText("");
        } else if(v.getId() == R.id.searchBtn){
            // 검색 버튼 클릭 시
            backHomeBtn.setVisibility(View.GONE);
            userSearchText.setVisibility(View.VISIBLE);
            searchText.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.VISIBLE);
            searchBtn.setVisibility(View.GONE);
            favoriteItems.setVisibility(View.GONE);

            if(searchText.getText().toString().length() > 0 )
                listItems.setFilterText(searchText.getText().toString());
            else
                listItems.clearTextFilter();

            userSearchText.setText(searchText.getText().toString() + "\t\t" + adapter.getCount() + "개");
            listText.setText("결과 목록");

            if(adapter.getCount() > 0) {
                listItems.setVisibility(View.VISIBLE);
                resultNoRest.setVisibility(View.GONE);
            } else {
                listItems.setVisibility(View.GONE);
                resultNoRest.setVisibility(View.VISIBLE);
            }
        } else if(v.getId() == R.id.now_location){
            // 현재 위치 재설정 버튼 클릭 시
            try {
                double latitude = userLocationUpdate.getLatitude();
                double longitude = userLocationUpdate.getLongitude();
                String address = userLocationUpdate.getCurrentAddress(latitude, longitude);
                Toast.makeText(mContext,"현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
                nowLocation.setText(address);
            } catch (IOException e) {
                Toast.makeText(mContext, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //리스트 클릭 시 페이지 이동 -> 각 store로 이동할 수 있도록 작업 필요 / 현재는 fragmentstorehome 페이지가 출력되도로 작업해둠
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FragmentStoreHome fragmentStoreHome = new FragmentStoreHome();
        /* 현재 위치를 기준으로 해당 가게와 거리 계산 / 직선 & 각도를 기준으로 거리 계산
        geoPoint = userLocationUpdate.findGeoPoint("인천 연수구 아카데미로 119");

        Location start = new Location("point A");
        start.setLatitude(35.228545);
        start.setLongitude(128.889352);
        Location end = new Location("point B");
        end.setLatitude(geoPoint.targetLatitude);
        end.setLongitude(geoPoint.targetLongitude);

        float distant = start.distanceTo(end);
        Toast.makeText(mContext, "distant = " + distant, Toast.LENGTH_LONG).show();
        */
        transaction.replace(R.id.linear_layout, fragmentStoreHome);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
