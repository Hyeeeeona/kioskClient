package com.example.kioskclient;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMenuOrderHistory extends Fragment {


    public FragmentMenuOrderHistory() {
        // Required empty public constructor
    }

    OrderHistoryListViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_order_history, null);
         adapter = new OrderHistoryListViewAdapter();
        ListView listview = (ListView) view.findViewById(R.id.listview_order_history);
        listview.setAdapter(adapter);

        SimpleDateFormat mFormat = new SimpleDateFormat("MM월 dd일 hh시 mm분");


        try {
            JSONArray jsonArray = HistoryDataFileIO.readHistoryDataJson(getContext());
            for(int i =0 ; i< jsonArray.length();i++){
                if(jsonArray.isNull(i)){

                }
                {
                    JSONObject history = jsonArray.getJSONObject(i);
                    int shop_id = history.getInt("shop_id");
                    String shop_name = history.getString("shop_name");
                    String history_menu = history.getString("history_menu");
                    int date = history.getInt("date");
                    Date mDate = new Date(date);

                    adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px),
                            shop_name, history_menu, mFormat.format(mDate));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
/*

        // 데이터 하드코딩
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px),
                "이전 주문 매장 1", "아메리카노 1", "5월 5일");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px),
                "이전 주문 매장 2", "아메리카노 2", "5월 2일");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px),
                "이전 주문 매장 3", "아메리카노 3", "3월 3일");
*/

        return view;
    }

}
