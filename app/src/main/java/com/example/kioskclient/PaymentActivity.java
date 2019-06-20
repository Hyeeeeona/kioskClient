package com.example.kioskclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    private TextView storeName;
    private TextView menuView;
    private TextView totalCost;
    private int int_total;
    private Button payBtn;
    EditText editPhone;
    int res_time;
    Spinner timeSpin, cardSpin;
    private NetworkService networkService;
    Boolean status_ok;
    int shop_id = 0;
    ArrayList<String> timearrayList;
    ArrayAdapter<String> timearrayAdapter;
    ArrayList<String> cardarrayList;
    ArrayAdapter<String> cardarrayAdapter;

    int order_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        networkService = ApplicationController.getInstance().getNetworkService();

        storeName = findViewById(R.id.payment_store_name);
        menuView = findViewById(R.id.payment_menu_name);
        totalCost = findViewById(R.id.payment_cost);
        payBtn = findViewById(R.id.payment_button);
        editPhone = findViewById(R.id.payment_phone);

        status_ok = false;
        final Intent intent = getIntent();
        storeName.setText(intent.getStringExtra("StoreName"));
        menuView.setText(intent.getStringExtra("FirstMenu") + "외 " + (intent.getIntExtra("menuCount", 0) - 1) + "개");
        int_total = intent.getIntExtra("TotalCost", 0);
        shop_id = intent.getIntExtra("Store_id", 0);
        Log.d("shop_id","payment"+shop_id);
        totalCost.setText("" + int_total + "원");
        payBtn.setText("결제 : " + int_total);


        cardSpin = findViewById(R.id.payment_spinner);
        cardarrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = CardDataFileIO.readCardDataJson(this);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject card = jsonArray.getJSONObject(i);
                String cardNickName = card.getString("cardNickName");
                Log.d("card", cardNickName);
                cardarrayList.add(cardNickName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cardarrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                cardarrayList);
        cardSpin.setAdapter(cardarrayAdapter);

        timeSpin = findViewById(R.id.reservation_spinner);
        timearrayList = new ArrayList<>();
        timearrayList.add("0분");
        timearrayList.add("15분");
        timearrayList.add("30분");
        timearrayList.add("45분");
        timearrayList.add("60분");
        timearrayList.add("90분");
        timearrayList.add("120분");
        timearrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                timearrayList);

        timeSpin.setAdapter(timearrayAdapter);
        timeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str = timearrayList.get(i);
                str = str.replaceAll("분", "");
                str = str.replaceAll(" ", "");
                str = str.replaceAll("\n", "");
                res_time = Integer.parseInt(str);
                Log.d("숫자", "" + res_time);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(PaymentActivity.this);

                int data = int_total;

                if (data <= 0) {
                    Toast.makeText(PaymentActivity.this, "결제할 내역이 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                alert_confirm.setMessage("" + data + "원 결제하시겠습니까?");
                alert_confirm.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //확인 눌렀을 때

                        //OrderInfo 전송
                        FirebaseAuth mAuth;
                        FirebaseUser currentUser;
                        mAuth = FirebaseAuth.getInstance();
                        currentUser = mAuth.getCurrentUser();

                        String name = currentUser.getEmail();
                        String phone = editPhone.getText().toString();

                        final OrderInfo orderInfo = new OrderInfo(order_id, name.substring(0, 9), phone, res_time, shop_id, int_total);

                        Log.d("Time", "order_time : " + orderInfo.getOrder_time());


                        Call<OrderInfo> orderpostCall = networkService.post_orderinfo(orderInfo);
                        orderpostCall.enqueue(new Callback<OrderInfo>() {
                            @Override
                            public void onResponse(Call<OrderInfo> call, Response<OrderInfo> response) {
                                if (response.isSuccessful()) {
                                    status_ok = true;
                                    OrderInfo orderInfo1 = response.body();
                                    Log.d("Success:", "데이터 저장 완료" + status_ok);
                                    Log.d("order_id", "" + orderInfo1.getOrderId());
                                    order_id = orderInfo1.getOrderId();


                                    JSONObject jsonObject = CartDataFileIO.readCartDataJson(PaymentActivity.this);
                                    JSONArray jsonArray = null;
                                    try {
                                        jsonArray = jsonObject.getJSONArray("MenuData");


                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject menu = jsonArray.getJSONObject(i);
                                            String menuName = menu.getString("MenuName");
                                            String menuOption = menu.getString("MenuOption");
                                            int menuCount = menu.getInt("MenuCount");
                                            int menuCost = menu.getInt("MenuCost");

                                            OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
                                            orderDetailInfo.setOrder_id(order_id);
                                            Log.d("orderid", "" + orderDetailInfo.getOrder_id());
                                            orderDetailInfo.setMenu_name(menuName);
                                            orderDetailInfo.setMenu_size(menuOption);
                                            orderDetailInfo.setQuantity(menuCount);
                                            orderDetailInfo.setTotal(menuCost * menuCount);

                                            Call<OrderDetailInfo> postCall = networkService.post_orderdetailinfo(orderDetailInfo);
                                            postCall.enqueue(new Callback<OrderDetailInfo>() {
                                                @Override
                                                public void onResponse(Call<OrderDetailInfo> call, Response<OrderDetailInfo> response) {
                                                    if (response.isSuccessful()) {
                                                        status_ok = true;
                                                        Log.d("Success:", "데이터 저장 완료" + status_ok);


                                                        CartDataFileIO.saveCartEmptyData(PaymentActivity.this);
                                                        HistoryDataFileIO.saveAddHistoryDataJson(PaymentActivity.this,
                                                                HistoryDataFileIO.makeHistoryDataJson(shop_id, storeName.getText().toString(), menuView.getText().toString(), System.currentTimeMillis()));

                                                    } else {
                                                        int StatusCode = response.code();
                                                        Log.i(ApplicationController.TAG, "Status Code:" + StatusCode);
                                                        status_ok = false;
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<OrderDetailInfo> call, Throwable t) {
                                                    Log.i(ApplicationController.TAG, "Fail Message" + t.getMessage());
                                                    status_ok = false;
                                                }
                                            });
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    int StatusCode = response.code();
                                    Log.i(ApplicationController.TAG, "Status Code:" + StatusCode);
                                    status_ok = false;
                                }
                            }

                            @Override
                            public void onFailure(Call<OrderInfo> call, Throwable t) {
                                Log.i(ApplicationController.TAG, "Fail Message" + t.getMessage());
                                status_ok = false;
                            }
                        });
                        onBackPressed();
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
