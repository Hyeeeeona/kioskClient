package com.example.kioskclient;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDBData {
    NetworkService networkService;
    Boolean status_ok;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public OrderDBData(){
        networkService = ApplicationController.getInstance().getNetworkService();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public void orderDetailPush(Context context) {
        try {
            JSONObject jsonObject = CartDataFileIO.readCartDataJson(context);
            JSONArray jsonArray = jsonObject.getJSONArray("MenuData");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject menu = jsonArray.getJSONObject(i);
                String menuName = menu.getString("MenuName");
                String menuOption = menu.getString("MenuOption");
                int menuCount = menu.getInt("MenuCount");
                int menuCost = menu.getInt("MenuCost");


                OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
                orderDetailInfo.setId(i);
                orderDetailInfo.setOrder_id(10);
                orderDetailInfo.setMenu_name(menuName);
                orderDetailInfo.setMenu_size(menuOption);
                orderDetailInfo.setQuantity(menuCount);
                orderDetailInfo.setTotal(menuCost * menuCount);

                Call<OrderDetailInfo> postCall = networkService.post_orderdetailinfo(orderDetailInfo);
                postCall.enqueue(new Callback<OrderDetailInfo>() {
                    @Override
                    public void onResponse(Call<OrderDetailInfo> call, Response<OrderDetailInfo> response) {
                        if (response.isSuccessful()) {
                            Log.d("Success:", "데이터 저장 완료");
                        } else {
                            int StatusCode = response.code();
                            Log.i(ApplicationController.TAG, "Status Code:" + StatusCode);
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderDetailInfo> call, Throwable t) {
                        Log.i(ApplicationController.TAG, "Fail Message" + t.getMessage());
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
