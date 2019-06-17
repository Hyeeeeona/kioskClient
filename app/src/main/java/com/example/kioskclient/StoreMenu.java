package com.example.kioskclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class StoreMenu extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView StoreName;
    private TextView MenuName;
    private TextView menuCountView;
    private Button btnMenuCost;
    private Button btnMenuCountUp;
    private Button btnMenuCountDown;

    private RadioGroup RG;
    private RadioButton MenuCost_S;
    private RadioButton MenuCost_M;
    private RadioButton MenuCost_L;

    private int menuCost_s;
    private int menuCost_m;
    private int menuCost_l;

    private int count;
    private int cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_menu);

        StoreName = (TextView)findViewById(R.id.tv_storeName);

        menuCountView = (TextView) findViewById(R.id.store_menu_count);
        btnMenuCost = (Button) findViewById(R.id.button);
        btnMenuCountUp = (Button) findViewById(R.id.store_menu_count_up);
        btnMenuCountDown = (Button) findViewById(R.id.store_menu_count_down);

        btnMenuCountUp.setOnClickListener(this);
        btnMenuCountDown.setOnClickListener(this);
        btnMenuCost.setOnClickListener(this);

        MenuName = (TextView) findViewById(R.id.tv_menuName);
        RG = (RadioGroup) findViewById(R.id.RG);
        MenuCost_S = (RadioButton) findViewById(R.id.RBsmall);
        MenuCost_M = (RadioButton) findViewById(R.id.RBmid);
        MenuCost_L = (RadioButton) findViewById(R.id.RBlarge);

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

        menuCost_s = (int) intent.getIntExtra("cost_s", intCost_s);
        menuCost_m = (int) intent.getIntExtra("cost_m", intCost_m);
        menuCost_l = (int) intent.getIntExtra("cost_l", intCost_l);


        MenuName.setText(menuName);
        MenuCost_S.setText(" * S : " + menuCost_s + "원");
        MenuCost_M.setText(" * M : " + menuCost_m + "원");
        MenuCost_L.setText(" * L : " + menuCost_l + "원");


    }

    @Override
    public void onClick(View v) {


        if (v == btnMenuCountUp) {

            count += 1;
            menuCountView.setText(Integer.toString(count));

            btnMenuCost.setText(Integer.toString(count) + "개 담기       " + (count * cost) +"원");

        } else if (v == btnMenuCountDown){
            count -= 1;
            if(count > 0){
                menuCountView.setText(Integer.toString(count));
                btnMenuCost.setText(Integer.toString(count) + "개 담기       " + (count * cost) +"원");
            } else {
                count = 1;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if(checkedId == R.id.RBsmall){
            cost = menuCost_s;
            btnMenuCost.setText(Integer.toString(count) + "개 담기       " + (count * cost) +"원");
        } else if(checkedId == R.id.RBmid){
            cost = menuCost_m;
            btnMenuCost.setText(Integer.toString(count) + "개 담기       " + (count * cost) +"원");
        } else if(checkedId == R.id.RBlarge){
            cost = menuCost_l;
            btnMenuCost.setText(Integer.toString(count) + "개 담기       " + (count * cost) +"원");
        }

    }

}
