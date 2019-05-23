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
public class FragmentMenuCart extends Fragment {


    public FragmentMenuCart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_cart, null);

        CartListViewAdapter adapter = new CartListViewAdapter() ;

        ListView listview = (ListView) view.findViewById(R.id.listview_cart) ;
        listview.setAdapter(adapter) ;


        adapter.addItem("아메리카노",6000, 1) ;
        adapter.addItem("아메리카노",5000, 2) ;
        adapter.addItem("아메리카노",4500, 3) ;
        adapter.addItem("아메리카노",3000, 4) ;

        return view;
    }

}
