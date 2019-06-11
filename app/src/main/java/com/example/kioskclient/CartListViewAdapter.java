package com.example.kioskclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class CartListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<CartListViewItem> cartListViewItems = new ArrayList<CartListViewItem>() ;

    private TextView menuNameView;
    private TextView menuCostView;
    private TextView menuOptionView;
    private TextView menuCountView;
    public CartListViewAdapter() {
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return cartListViewItems.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cart_list_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        menuNameView = (TextView) convertView.findViewById(R.id.cart_item_menu_name) ;
        menuCostView = (TextView) convertView.findViewById(R.id.cart_item_menu_cost) ;
        menuOptionView = (TextView) convertView.findViewById(R.id.cart_item_menu_option);
        menuCountView = (TextView) convertView.findViewById(R.id.cart_item_menu_count) ;

        Button btnDelete = (Button) convertView.findViewById(R.id.cart_item_menu_delete);
        Button btnMenuCountUp = (Button)convertView.findViewById(R.id.cart_item_menu_count_up);
        Button btnMenuCountDown = (Button)convertView.findViewById(R.id.cart_item_menu_count_down);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        CartListViewItem listViewItem = cartListViewItems.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        menuNameView.setText(listViewItem.getMenuName());
        menuCostView.setText(""+(listViewItem.getMenuCost()*listViewItem.getMenuCount()) + "원");
        menuCountView.setText(""+listViewItem.getMenuCount());
        menuOptionView.setText(listViewItem.getMenuOption());


        btnMenuCountUp.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                CartListViewItem listViewItem = cartListViewItems.get(pos);
                int count = listViewItem.getMenuCount();
                count += 1;
                listViewItem.setCount(count);
                menuCountView.setText(""+listViewItem.getMenuCount());
                menuCostView.setText(""+(listViewItem.getMenuCost()*listViewItem.getMenuCount()) + "원");
                notifyDataSetChanged();
            }
        });

        btnMenuCountDown.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                CartListViewItem listViewItem = cartListViewItems.get(pos);
                int count = listViewItem.getMenuCount();
                count -= 1;
                if(count > 0) {
                    listViewItem.setCount(count);
                    menuCountView.setText(Integer.toString(listViewItem.getMenuCount()));
                    menuCostView.setText(""+(listViewItem.getMenuCost()*listViewItem.getMenuCount()) + "원");
                    notifyDataSetChanged();
                }
            }
        });

        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cartListViewItems.remove(pos);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public CartListViewItem getItem(int position) {
        return cartListViewItems.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String menuName, String menuOption, int menuCost, int menuNum) {
        CartListViewItem item = new CartListViewItem();

        item.setName(menuName);
        item.setCost(menuCost);
        item.setCount(menuNum);
        item.setOption(menuOption);

        cartListViewItems.add(item);
    }

    public int getTotalCost(){
        int result = 0;
        for(CartListViewItem item : cartListViewItems){
            result += item.getMenuCost()*item.getMenuCount();
        }
        return result;
    }

    public JSONArray getListData(){
        JSONArray result = new JSONArray();
        for(CartListViewItem item : cartListViewItems){
            JSONObject menuDataJsonObject = new JSONObject();
            try {
                menuDataJsonObject.put("MenuName",item.getMenuName());
                menuDataJsonObject.put("MenuCount",item.getMenuCount());
                menuDataJsonObject.put("MenuOption",item.getMenuOption());
                menuDataJsonObject.put("MenuCost",item.getMenuCost());
                result.put(menuDataJsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}