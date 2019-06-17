package com.example.kioskclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    private NetworkService networkService;
    private TextView textView;
    private Button btnGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        textView = findViewById(R.id.testTextView);
        btnGet = findViewById(R.id.testbtn_get);
        ApplicationController application = ApplicationController.getInstance();
        application.buildNetworkService("10.0.2.2", 8000);
        networkService = ApplicationController.getInstance().getNetworkService();
/*
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<ShopInfo>> shopInfoCall = networkService.get_shopinfo();
                shopInfoCall.enqueue(new Callback<List<ShopInfo>>() {
                    @Override
                    public void onResponse(Call<List<ShopInfo>> call, Response<List<ShopInfo>> response) {
                        if(response.isSuccessful()){
                            List<ShopInfo> shopInfoList = response.body();

                            String shopinfo_txt = "";
                            for(ShopInfo shopInfo: shopInfoList){
                                shopinfo_txt += "매장이름: "+ shopInfo.getShopName() + "\n";
                                shopinfo_txt += "매장전화번호: "+ shopInfo.getShopTel() + "\n";

                            }
                            textView.setText(shopinfo_txt);
                        }else {
                            int StatusCode = response.code();
                            Log.i(ApplicationController.TAG,"Status Code :"+StatusCode);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ShopInfo>> call, Throwable t) {
                        Log.i(ApplicationController.TAG,"Fail Message :" + t.getMessage());
                    }
                });
            }
        });
*/
    }
}
