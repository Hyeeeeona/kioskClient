package com.example.kioskclient;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStoreHome extends Fragment {

    private TextView storeName;
    private TextView storeInfoTime;
    private TextView storeInfoPhone;
    private TextView storeInfo;

    private ImageView favorite;
    private int i = 1;

    //서버에서 받는 정보
    private String sv_storeName = "별다방";
    private String sv_store_info_time = "매일 오전 11:00 ~ 오후 11:00";
    private String sv_store_info_phone = "02-123-4567";
    private String sv_store_info = "커피 맛집으로 소문난 별다방입니다!";
    private int store_status = 1; //0 : 영업x, 1 : 영업중
    private Button store_close;

    private View view;
    Integer shop_id;


    public FragmentStoreHome() {
        // Required empty public constructor
    }
    public static FragmentStoreHome newInstance(int shop_id){
        FragmentStoreHome fragment = new FragmentStoreHome();
        Bundle args = new Bundle();
        args.putInt("shop_id", shop_id);
        fragment.setArguments(args);
        return fragment;
    }
    private StoreHomeListViewAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_home, null);

        //매장 기본정보

        storeName = (TextView) view.findViewById(R.id.tv_storeName);
        storeInfoTime = (TextView) view.findViewById(R.id.store_info_time);
        storeInfoPhone = (TextView) view.findViewById(R.id.store_info_phone);
        storeInfo = (TextView) view.findViewById(R.id.store_info);

        storeName.setText(sv_storeName);
        storeInfoTime.setText("* 운영정보 : " + sv_store_info_time);
        storeInfoPhone.setText("* 매장전화번호 : " + sv_store_info_phone);
        storeInfo.setText("* 소개 : " + sv_store_info);

        //매장 오픈 여부
        store_close = (Button) view.findViewById(R.id.btn_storeClose);
        if (store_status == 1) {
            //매장 열였어요
            store_close.setVisibility(view.GONE);
        } else if (store_status == 0) {
            //매장 안열었어요
            view.setVisibility(view.VISIBLE);
        }

        ListView listview = (ListView) view.findViewById(R.id.StoreHomeListView);

        Bundle args = getArguments();
        if(args != null){
            shop_id = args.getInt("shop_id");
            Toast.makeText(getContext(), "shop_id - "+ shop_id, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "shop_id - null", Toast.LENGTH_LONG).show();
        }

        adapter = new StoreHomeListViewAdapter();
        listview.setAdapter(adapter);

        adapter.addItem("1", "아메리카노", "Hot and Ice", 4000, 5000, 6000);
        adapter.addItem("2", "까페라떼", "Hot and Ice", 4500, 5500, 6500);
        adapter.addItem("3", "카라멜 마끼아또", "Hot and Ice", 5000, 6000, 7000);
        adapter.addItem("4", "까페모카", "Hot and Ice", 5000, 6000, 7000);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoreHomeListViewItem item = adapter.getItem(position);
                Toast.makeText(getContext(),item.getMenuName(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), StoreMenu.class);
                intent.putExtra("strName", item.getMenuName());
                intent.putExtra("strTemp", item.getMenuOptionTemp());
                intent.putExtra("cost_s",item.getMenuCost_s());
                intent.putExtra("cost_m",item.getMenuCost_m());
                intent.putExtra("cost_l",item.getMenuCost_l());

                intent.putExtra("shop_name",storeName.getText().toString());
                intent.putExtra("shop_id",shop_id);//임시

                startActivity(intent);


            }
        });
        //favorite click
        favorite = (ImageView) view.findViewById(R.id.store_favorite);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i % 2 == 1) {
                    favorite.setImageResource(R.drawable.star_score);
                    FavoriteDataFileIO.saveAddFavoriteData(getContext(),shop_id);
                    i++;
                } else {
                    favorite.setImageResource(R.drawable.star_empty);
                    FavoriteDataFileIO.deleteFavoriteData(getContext(),shop_id);
                    i--;
                }

            }
        });


        return view;
    }

}
