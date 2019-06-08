package com.example.kioskclient;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentStoreHome extends Fragment {


    public FragmentStoreHome() {
        // Required empty public constructor
    }

    private StoreHomeListViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_home, null);

        ListView listview = (ListView) view.findViewById(R.id.StoreHomeListView);

        adapter = new StoreHomeListViewAdapter();
        listview.setAdapter(adapter);

        adapter.addItem("1", "아메리카노", "Hot and Ice", 4000, 5000, 6000);
        adapter.addItem("2", "까페라떼", "Hot and Ice", 4500, 5500, 6500);
        adapter.addItem("3", "카라멜 마끼아또", "Hot and Ice", 5000, 6000, 7000);
        adapter.addItem("4", "까페모카", "Hot and Ice", 5000, 6000, 7000);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StoreHomeListViewItem item = adapter.getItem(position);
                Toast.makeText(getContext(),item.getMenuName(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), StoreMenu.class);
                intent.putExtra("strName", item.getMenuName());
                intent.putExtra("strTemp", item.getMenuOptionTemp());
                intent.putExtra("cost_s",item.getMenuCost_s());
                intent.putExtra("cost_m",item.getMenuCost_m());
                intent.putExtra("cost_l",item.getMenuCost_l());

                startActivity(intent);


            }
        });

        return view;
    }

}
