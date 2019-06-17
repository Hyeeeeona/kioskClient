package com.example.kioskclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CardDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);

        ListView listView = findViewById(R.id.card_list_view);
        CardListViewAdapter adapter = new CardListViewAdapter() ;
        listView.setAdapter(adapter) ;

        try {
            //JSONObject jsonObject = new JSONObject(Jsonstr);
            JSONArray jsonArray = CardDataFileIO.readCardDataJson(this);
            for(int i =0 ; i< jsonArray.length();i++){
                JSONObject card = jsonArray.getJSONObject(i);
                String cardCompany = card.getString("cardCompany");
                String cardNumber = card.getString("cardNumber");
                String cardNickName = card.getString("cardNickName");

                adapter.addItem(cardNickName,cardCompany,cardNumber) ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
