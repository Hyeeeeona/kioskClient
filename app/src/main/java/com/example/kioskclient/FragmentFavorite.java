package com.example.kioskclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFavorite extends Fragment {

    private NetworkService networkService;
    private FavoriteListViewAdapter adapter;
    public FragmentFavorite() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_favorite, null);

        adapter = new FavoriteListViewAdapter();
        ListView listview = (ListView) view.findViewById(R.id.listview_favorite);
        listview.setAdapter(adapter);

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
                                adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px),
                                        shopinfo.getShopName(), shopinfo.getBusinessHours());

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
        // 데이터 하드코딩
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px),
                "매장 1", "아메리카노 1");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px),
                "매장 2", "아메리카노 2");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px),
                "매장 3", "아메리카노 3");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return view;
    }

}
