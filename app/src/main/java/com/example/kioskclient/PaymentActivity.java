package com.example.kioskclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    private TextView storeName;
    private TextView menu;
    private TextView totalCost;
    private int int_total;
    private Button payBtn;
    private NetworkService networkService;
    Boolean status_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        networkService = ApplicationController.getInstance().getNetworkService();

        storeName = findViewById(R.id.payment_store_name);
        menu = findViewById(R.id.payment_menu_name);
        totalCost = findViewById(R.id.payment_cost);
        payBtn = findViewById(R.id.payment_button);

        status_ok = false;
        final Intent intent = getIntent();
        storeName.setText(intent.getStringExtra("StoreName"));
        menu.setText(intent.getStringExtra("FirstMenu")+"외 " + intent.getIntExtra("menuCount",0)+"개");
        int_total = intent.getIntExtra("TotalCost",0);
        totalCost.setText(""+int_total+"원");
        payBtn.setText("결제 : "+int_total);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(PaymentActivity.this);

                int data = int_total;

                if (data <= 0){
                    Toast.makeText(PaymentActivity.this, "결제할 내역이 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                alert_confirm.setMessage(""+data+"원 결제하시겠습니까?");
                alert_confirm.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //확인 눌렀을 때

                        try {
                            JSONObject jsonObject = CartDataFileIO.readCartDataJson(PaymentActivity.this);
                            JSONArray jsonArray =  jsonObject.getJSONArray("MenuData");

                            for(int i =0 ; i< jsonArray.length();i++){
                                JSONObject menu = jsonArray.getJSONObject(i);
                                String menuName = menu.getString("MenuName");
                                String menuOption = menu.getString("MenuOption");
                                int menuCount = menu.getInt("MenuCount");
                                int menuCost = menu.getInt("MenuCost");


                                OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
                                orderDetailInfo.setOrder_id(10);
                                orderDetailInfo.setMenu_name(menuName);
                                orderDetailInfo.setMenu_size(menuOption);
                                orderDetailInfo.setQuantity(menuCount);
                                orderDetailInfo.setTotal(menuCost*menuCount);

                                Call<OrderDetailInfo> postCall = networkService.post_orderdetailinfo(orderDetailInfo);
                                postCall.enqueue(new Callback<OrderDetailInfo>() {
                                    @Override
                                    public void onResponse(Call<OrderDetailInfo> call, Response<OrderDetailInfo> response) {

                                        if(response.isSuccessful()){
                                            status_ok = true;

                                            Log.d("Success:","데이터 저장 완료"+status_ok);
                                        } else {
                                            int StatusCode = response.code();
                                            Log.i (ApplicationController.TAG,"Status Code:"+StatusCode);
                                            status_ok = false;
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<OrderDetailInfo> call, Throwable t) {
                                        Log.i(ApplicationController.TAG,"Fail Message"+t.getMessage());
                                        status_ok = false;
                                    }
                                });
                                onBackPressed();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                alert_confirm.setNegativeButton("취소", null);
                AlertDialog alert = alert_confirm.create();
                alert.setTitle("결제");
                alert.show();
            }
        });

    }
}
