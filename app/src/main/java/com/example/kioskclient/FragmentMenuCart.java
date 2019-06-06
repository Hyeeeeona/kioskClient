package com.example.kioskclient;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
//TODO: 합계를 표기하기위해서는 ListView 대신 RecyclerView를 사용할 필요가 있음. 시간 여유가 있으면 수정할 예정
public class FragmentMenuCart extends Fragment {


    public FragmentMenuCart() {
        // Required empty public constructor
    }

    private CartListViewAdapter adapter;
    private TextView storeName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FragmentCart","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_cart, null);
        View footer = getLayoutInflater().inflate(R.layout.cart_list_footer, null, false) ;


        String Jsonstr = JsonExample.makeJsonStringCartDataExample();
        Log.d("FragmentCart",Jsonstr);

        ListView listview = (ListView) view.findViewById(R.id.listview_cart) ;
        storeName = (TextView) view.findViewById(R.id.menu_cart_store_name);
        Button btnpayment = (Button)footer.findViewById(R.id.btn_payment);
        TextView total = (TextView)footer.findViewById(R.id.cart_list_total);

        // listView에 header, footer 추가.
        listview.addFooterView(footer) ;
        adapter = new CartListViewAdapter() ;
        listview.setAdapter(adapter) ;

        try {
            //JSONObject jsonObject = new JSONObject(Jsonstr);
            JSONObject jsonObject = CartDataFileIO.readCartDataJson(getContext());
            storeName.setText(jsonObject.getString("StoreName"));
            JSONArray jsonArray =  jsonObject.getJSONArray("MenuData");

            for(int i =0 ; i< jsonArray.length();i++){
                JSONObject menu = jsonArray.getJSONObject(i);
                String menuName = menu.getString("MenuName");
                String menuOption = menu.getString("MenuOption");
                int menuCount = menu.getInt("MenuCount");
                int menuCost = menu.getInt("MenuCost");

                adapter.addItem(menuName,menuOption,menuCost, menuCount) ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        JSONArray jsonArray = adapter.getListData();
        JSONObject jsonObject = CartDataFileIO.makeCartDataJson(storeName.getText().toString(),jsonArray);
        CartDataFileIO.saveCartDataJson(getContext(),jsonObject);
        Log.d("FragmentCart","onDestroyView");
    }
}
