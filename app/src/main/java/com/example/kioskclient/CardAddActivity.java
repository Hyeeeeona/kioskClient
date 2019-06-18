package com.example.kioskclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardAddActivity extends AppCompatActivity {
    private Spinner spinner;
    private EditText edit1,edit2,edit3,edit4,edit_name;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    Button btnadd;
    String cardName;
    String cardCompany;
    String cardNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_add);

        arrayList = new ArrayList<>();
        arrayList.add("BC카드");
        arrayList.add("신한카드");
        arrayList.add("KB국민카드");
        arrayList.add("삼성카드");
        arrayList.add("현대카드");
        arrayList.add("롯데카드");
        arrayList.add("하나카드");
        arrayList.add("NH농협카드");
        arrayList.add("씨티카드");
        arrayList.add("우리카드");
        arrayList.add("카카오뱅크카드");
        arrayList.add("케이뱅크카드");
        arrayList.add("전북은행카드");
        arrayList.add("새마을금고카드");

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList);

        spinner = (Spinner)findViewById(R.id.card_spinner_company);
        edit1 = findViewById(R.id.card_number1);
        edit2 = findViewById(R.id.card_number2);
        edit3 = findViewById(R.id.card_number3);
        edit4 = findViewById(R.id.card_number4);
        edit_name = findViewById(R.id.card_nickname_edit);
        btnadd = findViewById(R.id.card_btn_add_add);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cardCompany = arrayList.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean status_ok = true;
                cardName = edit_name.getText().toString();
                if(edit1.getText().toString().length() > 4 ||
                        edit2.getText().toString().length() > 4||
                        edit3.getText().toString().length() > 4||
                        edit4.getText().toString().length() > 4){
                    status_ok = false;
                }
                if(status_ok){
                    cardNumber = ""+edit1.getText().toString()+"-"+
                            edit2.getText().toString()+"-"+
                            edit3.getText().toString()+"-"+
                            edit4.getText().toString();
                    JSONArray jsonArray = CardDataFileIO.readCardDataJson(CardAddActivity.this);
                    JSONObject jsonObject = CardDataFileIO.makeCardDataJson(cardCompany,cardName,cardNumber);
                    jsonArray.put(jsonObject);
                    CardDataFileIO.saveCardDataJson(CardAddActivity.this,jsonArray);
                    onBackPressed();
                }
            }
        });
    }
}
