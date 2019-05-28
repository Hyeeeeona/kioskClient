package com.example.kioskclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMenuOrderHistory extends Fragment {


    public FragmentMenuOrderHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_menu_order_history, null);

        FavoriteListViewAdapter adapter = new FavoriteListViewAdapter() ;
        ListView listview = (ListView) view.findViewById(R.id.listview_order_history) ;
        listview.setAdapter(adapter) ;

        // 데이터 하드코딩
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px),
                "이전 주문 매장 1", "아메리카노 1") ;
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px),
                "이전 주문 매장 2", "아메리카노 2") ;
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_baseline_add_shopping_cart_24px),
                "이전 주문 매장 3", "아메리카노 3") ;

        return view;
    }

}