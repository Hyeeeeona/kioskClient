package com.example.kioskclient;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStoreMenu extends Fragment {


    public FragmentStoreMenu() {
        // Required empty public constructor
    }
    private StoreHomeListViewAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_store_menu, null);

        ListView listview = (ListView) view.findViewById(R.id.StoreHomeListView);

        adapter = new StoreHomeListViewAdapter();
        listview.setAdapter(adapter);

        adapter.addItem("1", "아메리카노", "Hot and Ice", 4000, 5000, 6000);
        adapter.addItem("2", "까페라떼", "Hot and Ice", 4000, 5000, 6000);
        adapter.addItem("3", "카라멜 마끼아또", "Hot and Ice", 4000, 5000, 6000);
        adapter.addItem("4", "까페모카", "Hot and Ice", 4000, 5000, 6000);




        return view;


    }

}
