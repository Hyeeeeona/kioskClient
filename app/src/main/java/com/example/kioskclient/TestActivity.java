package com.example.kioskclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

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
        application.buildNetworkService( "076150d9.ngrok.io");
        networkService = ApplicationController.getInstance().getNetworkService();

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<List<ShopInfo>> getCall = networkService.get_shopinfo();
                getCall.enqueue(new Callback<List<ShopInfo>>() {
                    @Override
                    public void onFailure(Call<List<ShopInfo>> call, Throwable t) {
                        Log.d("debugging", "Fail Message : " + t.getMessage());
                    }

                    @Override
                    public void onResponse(Call<List<ShopInfo>> call, Response<List<ShopInfo>> response) {
                        Boolean isExist = false;
                        if (response.isSuccessful()) {
                            List<ShopInfo> shopinfoList = response.body();
                            for (ShopInfo shopinfo : shopinfoList ) {
                                    Log.d("debugging", "찾았당");
                                    JSONObject jsonObject = ShopInfoDataFileIO.makeShopInfoDataJson(shopinfo);
                                    Log.d("Tag",jsonObject.toString());
                                    ShopInfoDataFileIO.saveShopInfoDataJson(getApplicationContext(), jsonObject);
                                    isExist = true;
                                    break;
                            }
                        } else {
                            Log.d("debugging", "Error Message : " + response.errorBody());
                        }
                    }
                });
            }
        });

    }
}
