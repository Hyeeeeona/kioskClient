package com.example.kioskclient;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * A simple {@link Fragment} subclass.
 */
//TODO: 합계를 표기하기위해서는 ListView 대신 RecyclerView를 사용할 필요가 있음. 시간 여유가 있으면 수정할 예정
public class FragmentMenuCart extends Fragment {


    public FragmentMenuCart() {
        // Required empty public constructor
    }

    private CartListViewAdapter adapter;
    private TextView storeName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FragmentCart","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_cart, null);
        View footer = getLayoutInflater().inflate(R.layout.cart_list_footer, null, false) ;


        String Jsonstr = JsonExample.makeJsonStringCartDataExample();
        Log.d("FragmentCart",Jsonstr);

        ListView listview = (ListView) view.findViewById(R.id.listview_cart) ;
        storeName = (TextView) view.findViewById(R.id.menu_cart_store_name);
        Button btnpayment = (Button)footer.findViewById(R.id.btn_payment);
        TextView total = (TextView)footer.findViewById(R.id.cart_list_total);

        // listView에 header, footer 추가.
        listview.addFooterView(footer) ;
        adapter = new CartListViewAdapter() ;
        listview.setAdapter(adapter) ;

        try {
            //JSONObject jsonObject = new JSONObject(Jsonstr);
            JSONObject jsonObject = CartDataFileIO.readCartDataJson(getContext());
            storeName.setText(jsonObject.getString("StoreName"));
            JSONArray jsonArray =  jsonObject.getJSONArray("MenuData");

            for(int i =0 ; i< jsonArray.length();i++){
                JSONObject menu = jsonArray.getJSONObject(i);
                String menuName = menu.getString("MenuName");
                String menuOption = menu.getString("MenuOption");
                int menuCount = menu.getInt("MenuCount");
                int menuCost = menu.getInt("MenuCost");

                adapter.addItem(menuName,menuOption,menuCost, menuCount) ;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 다이얼로그 바디
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getContext());
                // 메세지

                int data = adapter.getTotalCost();

                if (data <= 0){
                    Toast.makeText(getActivity(), "결제할 내역이 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                alert_confirm.setMessage(""+data+"원 결제하시겠습니까?");
                // 확인 버튼 리스너
                alert_confirm.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //결제 후 추가

                        Toast.makeText(getActivity(), "결제 되었습니다. 주문내역을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                });


                alert_confirm.setNegativeButton("취소", null);
                // 다이얼로그 생성
                AlertDialog alert = alert_confirm.create();

                // 아이콘
                //alert.setIcon(R.drawable.ic_launcher);
                // 다이얼로그 타이틀
                alert.setTitle("결제");
                // 다이얼로그 보기
                alert.show();

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        JSONArray jsonArray = adapter.getListData();
        JSONObject jsonObject = CartDataFileIO.makeCartDataJson(storeName.getText().toString(),jsonArray);
        CartDataFileIO.saveCartDataJson(getContext(),jsonObject);
        Log.d("FragmentCart","onDestroyView");
    }
}
