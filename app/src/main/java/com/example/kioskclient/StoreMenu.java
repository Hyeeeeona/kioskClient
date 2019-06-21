package com.example.kioskclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class StoreMenu extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView StoreName;
    private TextView MenuName;
    private TextView menuCountView;
    private TextView MenuTemp;
    private Button btnMenuCost;
    private Button btnMenuCountUp;
    private Button btnMenuCountDown;

    private RadioGroup RG;
    private RadioGroup RGtemp;
    private RadioButton MenuCost_S;
    private RadioButton MenuCost_M;
    private RadioButton MenuCost_L;
    private RadioButton MenuTemp_H;
    private RadioButton MenuTemp_I;

    private int menuCost_s;
    private int menuCost_m;
    private int menuCost_l;

    private int count;
    private int cost;
    String size;
    int temp;

    private int shop_id;
    private String shop_name;

    private String size_option;
    private String temp_option = "HOT, ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_menu);

        StoreName = (TextView) findViewById(R.id.tv_storeName);

        menuCountView = (TextView) findViewById(R.id.store_menu_count);
        btnMenuCost = (Button) findViewById(R.id.button_menu_add);
        btnMenuCountUp = (Button) findViewById(R.id.store_menu_count_up);
        btnMenuCountDown = (Button) findViewById(R.id.store_menu_count_down);

        btnMenuCountUp.setOnClickListener(this);
        btnMenuCountDown.setOnClickListener(this);
        btnMenuCost.setOnClickListener(this);

        MenuName = (TextView) findViewById(R.id.tv_menuName);
        RG = (RadioGroup) findViewById(R.id.RG);
        RGtemp = findViewById(R.id.RGtemp);

        MenuCost_S = (RadioButton) findViewById(R.id.RBsmall);
        MenuCost_M = (RadioButton) findViewById(R.id.RBmid);
        MenuCost_L = (RadioButton) findViewById(R.id.RBlarge);

        MenuTemp = (TextView)findViewById(R.id.tv_menuTemp);
        MenuTemp_H = findViewById(R.id.RBhot);
        MenuTemp_I = findViewById(R.id.RBiced);

        RadioButton HotB = findViewById(R.id.RBhot);
        RadioButton IceB = findViewById(R.id.RBiced);
        MenuCost_S.setChecked(true);
        MenuTemp_H.setChecked(true);
        RG.setOnCheckedChangeListener(this);

        count = Integer.parseInt(menuCountView.getText().toString());


        //FragmentStoreHome에서 데이터 받아오기

        Intent intent = getIntent();
        String menuName = intent.getStringExtra("strName");
        //String menuTemp = intent.getStringExtra("strTemp");
        //String menuCost_s = intent.getStringExtra("cost_s");

        int intCost_s = 0;
        int intCost_m = 0;
        int intCost_l = 0;
        int hotorcold = 0;

        menuCost_s = (int) intent.getIntExtra("cost_s", intCost_s);
        menuCost_m = (int) intent.getIntExtra("cost_m", intCost_m);
        menuCost_l = (int) intent.getIntExtra("cost_l", intCost_l);
        size = intent.getStringExtra("size");
        hotorcold = (int) intent.getIntExtra("strTemp", hotorcold);
        Log.d("hyeona", "hotorcold : " + hotorcold);
        if (hotorcold == 0) {
            temp_option = "옵션 없음";
            HotB.setVisibility(View.GONE);
            IceB.setVisibility(View.GONE);
        } else if(hotorcold == 1) {
            IceB.setChecked(true);
            temp_option = "ICE";
            HotB.setVisibility(View.GONE);
            IceB.setVisibility(View.VISIBLE);
        } else if (hotorcold == 2) {
            HotB.setChecked(true);
            temp_option = "HOT";
            HotB.setVisibility(View.VISIBLE);
            IceB.setVisibility(View.GONE);
        } else if(hotorcold == 3) {
            temp_option = "HOT";
            HotB.setChecked(true);
            HotB.setVisibility(View.VISIBLE);
            IceB.setVisibility(View.VISIBLE);
        }

        shop_id = intent.getIntExtra("shop_id", 0);
        shop_name = intent.getStringExtra("shop_name");
        StoreName.setText(shop_name);

        MenuName.setText(menuName);
        MenuCost_S.setText(" * "+size+" : " + menuCost_s + "원");
        MenuCost_M.setText(" * M : " + menuCost_m + "원");
        MenuCost_L.setText(" * L : " + menuCost_l + "원");

        cost = menuCost_s;
        btnMenuCost.setText(Integer.toString(count) + "개 담기       " + (count * cost) + "원");
        size_option = size;
    }

    @Override
    public void onClick(View v) {


        if (v == btnMenuCountUp) {

            count += 1;
            menuCountView.setText(Integer.toString(count));

            btnMenuCost.setText(Integer.toString(count) + "개 담기       " + (count * cost) + "원");

        } else if (v == btnMenuCountDown) {
            count -= 1;
            if (count > 0) {
                menuCountView.setText(Integer.toString(count));
                btnMenuCost.setText(Integer.toString(count) + "개 담기       " + (count * cost) + "원");
            } else {
                count = 1;
            }
        } else if (v == btnMenuCost) {
            try {
                JSONObject result = new JSONObject();
                JSONArray resultArray;
                //JSONObject jsonObject = new JSONObject(Jsonstr);
                JSONObject jsonObject = CartDataFileIO.readCartDataJson(StoreMenu.this);
                String storeNameStr = jsonObject.getString("StoreName");

                if (storeNameStr.equals(shop_name)){
                    resultArray =  jsonObject.getJSONArray("MenuData");
                }
                else {
                    resultArray = new JSONArray();
                }

                result.put("StoreName",shop_name);
                result.put("StoreId",shop_id);
                JSONObject menuDataJsonObject = new JSONObject();
                menuDataJsonObject.put("MenuName",MenuName.getText().toString());
                menuDataJsonObject.put("MenuCount",count);
                menuDataJsonObject.put("MenuOption",temp_option );
                menuDataJsonObject.put("MenuCost",cost);
                resultArray.put(menuDataJsonObject);
                result.put("MenuData",resultArray);

                CartDataFileIO.saveCartDataJson(StoreMenu.this,result);

                Log.d("JSON",result.toString());
                onBackPressed();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == R.id.RBsmall) {
            cost = menuCost_s;
            size_option = size;
            btnMenuCost.setText(Integer.toString(count) + "개 담기       " + (count * cost) + "원");
        } else if (checkedId == R.id.RBmid) {
            cost = menuCost_m;
            size_option = "사이즈 : M";
            btnMenuCost.setText(Integer.toString(count) + "개 담기       " + (count * cost) + "원");
        } else if (checkedId == R.id.RBlarge) {
            cost = menuCost_l;
            size_option = "사이즈 : L";
            btnMenuCost.setText(Integer.toString(count) + "개 담기       " + (count * cost) + "원");
        }

        if (checkedId == R.id.RBhot) {
            temp_option = "HOT";
        }
        if(checkedId == R.id.RBiced) {
            temp_option = "ICE";
        }
    }
}
