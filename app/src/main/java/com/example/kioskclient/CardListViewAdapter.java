package com.example.kioskclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.ArrayList;

public class CardListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<CardListViewItem> cardListViewItems = new ArrayList<CardListViewItem>() ;

    private TextView cardNameView;
    private TextView cardNumberView;
    private TextView cardCompanyView;
    private CheckBox checkBox;

    public CardListViewAdapter() {
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return cardListViewItems.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.card_list_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        cardNameView = (TextView) convertView.findViewById(R.id.card_item_name) ;
        cardNumberView = (TextView) convertView.findViewById(R.id.card_item_number) ;
        cardCompanyView = (TextView) convertView.findViewById(R.id.card_item_company);
        checkBox = (CheckBox)convertView.findViewById(R.id.card_item_checkbox);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        CardListViewItem listViewItem = cardListViewItems.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        String cardNameStr = listViewItem.getCardName();
        cardNameView.setText(cardNameStr == ""?"카드" + Integer.toString(pos):cardNameStr);
        cardNumberView.setText(listViewItem.getCardNumber());
        cardCompanyView.setText(listViewItem.getCardCompany());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public CardListViewItem getItem(int position) {
        return cardListViewItems.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String cardName, String cardCompany, String cardNumber) {
        CardListViewItem item = new CardListViewItem();

        item.setName(cardName);
        item.setCompany(cardCompany);
        item.setNumber(cardNumber);
        cardListViewItems.add(item);
    }

    public void delete(int i){
        cardListViewItems.remove(i);
    }

    public JSONArray getListData(){
        JSONArray result = new JSONArray();
        for(CardListViewItem item : cardListViewItems){
            result.put(CardDataFileIO.makeCardDataJson(item.getCardCompany(),item.getCardName(), item.getCardNumber()));
        }
        return result;
    }


}