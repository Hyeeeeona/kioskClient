package com.example.kioskclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoreHomeListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<StoreHomeListViewItem> storeHomeListViewItems = new ArrayList<StoreHomeListViewItem>() ;


    private TextView menuNameView;
    private TextView menuOpitonTempView;
    private TextView menuCost_S_View;
    private TextView menuCost_M_View;
    private TextView menuCost_L_View;




    public StoreHomeListViewAdapter() {
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return storeHomeListViewItems.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.store_home_list_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        menuNameView = (TextView) convertView.findViewById(R.id.tv_menuName) ;
        menuOpitonTempView = (TextView) convertView.findViewById(R.id.tv_menuTemp) ;
        menuCost_S_View = (TextView) convertView.findViewById(R.id.tv_menuSize_S) ;
        menuCost_M_View = (TextView) convertView.findViewById(R.id.tv_menuSize_M) ;
        menuCost_L_View = (TextView) convertView.findViewById(R.id.tv_menuSize_L) ;


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final StoreHomeListViewItem listViewItem = storeHomeListViewItems.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        menuNameView.setText(listViewItem.getMenuName());
        menuOpitonTempView.setText(listViewItem.getMenuOptionTemp());
        menuCost_S_View.setText("* S : " +(listViewItem.getMenuCost_s()) + "원");
        menuCost_M_View.setText("* M : " +(listViewItem.getMenuCost_m()) + "원");
        menuCost_L_View.setText("* L : " +(listViewItem.getMenuCost_l()) + "원");



        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public StoreHomeListViewItem getItem(int position) {
        return storeHomeListViewItems.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String id, String strName, String strTemp, int cost_s, int cost_m, int cost_l) {
        StoreHomeListViewItem item = new StoreHomeListViewItem();

        item.setID(id);
        item.setName(strName);
        item.setTemp(strTemp);
        item.setCost_s(cost_s);
        item.setCost_m(cost_m);
        item.setCost_l(cost_l);


        storeHomeListViewItems.add(item);



    }


}