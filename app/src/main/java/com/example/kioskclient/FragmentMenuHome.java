package com.example.kioskclient;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMenuHome extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    Button searchBtn, backHomeBtn, deleteBtn;
    ImageButton locationBtn;
    EditText searchText;
    TextView userSearchText, listText, resultNoRest, nowLocation;
    ListView listItems, favoriteItems;
    ListViewSearchAdapter adapter;
    FavoriteListViewAdapter favoriteListViewAdapter;
    private NetworkService networkService;

    private UserLocationUpdate userLocationUpdate;
    private Context mContext;
    private boolean lastItemVisibleFlag = false;
    private int visibleItem = 0;
    private int page = 0;
    private final int OFFSET = 5;
    private ProgressBar progressBar;
    private boolean mLockListView = false;

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
        progressBar = (ProgressBar)view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);


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
        searchText.setText("");

        JSONArray jsonArray = FavoriteDataFileIO.readFavoriteDataJson(getContext());

        networkService = ApplicationController.getInstance().getNetworkService();
        for (int i = 0; i < jsonArray.length(); i++) {

            if (jsonArray.isNull(i)) {

            } else {
                try {
                    JSONObject jsonObject;
                    jsonObject = jsonArray.getJSONObject(i);
                    int store_id = jsonObject.getInt("store_id");

                    Call<ShopInfo> getCall = networkService.get_pk_shopinfo(store_id);

                    getCall.enqueue(new Callback<ShopInfo>() {
                        @Override
                        public void onResponse(Call<ShopInfo> call, Response<ShopInfo> response) {
                            if (response.isSuccessful()) {
                                ShopInfo shopinfo = response.body();
                                favoriteListViewAdapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px), shopinfo.getShopName(), shopinfo.getBusinessHours(), shopinfo.getShopId());
                            }
                        }

                        @Override
                        public void onFailure(Call<ShopInfo> call, Throwable t) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        favoriteListViewAdapter.notifyDataSetChanged();

        Call<List<ShopInfo>> getCall = networkService.get_shopinfo();
        getCall.enqueue(new Callback<List<ShopInfo>>() {
            @Override
            public void onFailure(Call<List<ShopInfo>> call, Throwable t) {
                Log.d("debugging", "Fail Message : " + t.getMessage());
            }

            @Override
            public void onResponse(Call<List<ShopInfo>> call, Response<List<ShopInfo>> response) {
                if (response.isSuccessful()) {
                    List<ShopInfo> shopinfoList = response.body();
                    for (ShopInfo shopinfo : shopinfoList ) {
                        JSONObject jsonObject = ShopInfoDataFileIO.makeShopInfoDataJson(shopinfo);
                        ShopInfoDataFileIO.saveShopInfoDataJson(mContext, jsonObject);

                        Toast.makeText(mContext, "distance = "+ userLocationUpdate.getDistance(shopinfo.getShopAddress()), Toast.LENGTH_LONG).show();
                        adapter.addItem(shopinfo.getShopName(),0, userLocationUpdate.getDistance(shopinfo.getShopAddress()),getResources().getDrawable(R.drawable.now_location), shopinfo.getIntroduction(), shopinfo.getShopId());
                    }
                } else {
                    Log.d("debugging", "Error Message : " + response.errorBody());
                }
            }
        });

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
            listItems.setOnScrollListener(this);
            getItem();

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
        int shop_id = 0;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FragmentStoreHome fragmentStoreHome = new FragmentStoreHome();
        if(listItems.getVisibility() == View.VISIBLE)
            shop_id = adapter.getItemShopId(position);
        else
            shop_id = favoriteListViewAdapter.getItemShopId(position);
        transaction.replace(R.id.linear_layout, fragmentStoreHome.newInstance(shop_id));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLockListView == false){
            progressBar.setVisibility(View.VISIBLE);
            getItem();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }

    private void getItem() {
        mLockListView = true;
        if (lastItemVisibleFlag) {
            for (int i = 0; i < 5; i++) {


            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    page++;
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    mLockListView = false;
                }
            }, 500);
        }
    }

}
