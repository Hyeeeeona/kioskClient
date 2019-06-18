package com.example.kioskclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

public class CardDataActivity extends AppCompatActivity {

    ListView listView;
    CardListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);
    }

    @Override
    protected void onStart() {
        super.onStart();

        listView = findViewById(R.id.card_list_view);
        Button btn_add = findViewById(R.id.card_btn_add);
        adapter = new CardListViewAdapter();
        listView.setAdapter(adapter);

        try {
            JSONArray jsonArray = CardDataFileIO.readCardDataJson(this);
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.isNull(i)) {
                    Log.d("JSON",jsonArray.toString());
                } else {
                    JSONObject card = jsonArray.getJSONObject(i);
                    String cardCompany = card.getString("cardCompany");
                    String cardNumber = card.getString("cardNumber");
                    String cardNickName = card.getString("cardNickName");

                    adapter.addItem(cardNickName, cardCompany, cardNumber);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardDataActivity.this, CardAddActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JSONArray jsonArray = adapter.getListData();
        CardDataFileIO.saveCardDataJson(CardDataActivity.this, jsonArray);
    }
}
