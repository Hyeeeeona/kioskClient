package com.example.kioskclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMenuSearch extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button searchBtn, backHomeBtn, deleteBtn;
    EditText searchText;
    TextView userSearchText, searchInMsg, resultNoRest;
    ListView listItems;
    ListViewSearchAdapter adapter;
    public FragmentMenuSearch() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_search, container, false);

        searchBtn = (Button) view.findViewById(R.id.searchBtn);
        backHomeBtn = (Button) view.findViewById(R.id.backHomeBtn);
        deleteBtn = (Button) view.findViewById(R.id.deleteBtn);
        searchText = (EditText) view.findViewById(R.id.searchText);
        listItems = (ListView) view.findViewById(R.id.listItems);
        userSearchText = (TextView) view.findViewById(R.id.userSearchText);
        searchInMsg = (TextView) view.findViewById(R.id.searchInMsg);
        resultNoRest = (TextView) view.findViewById(R.id.resultNoRest);

        adapter = new ListViewSearchAdapter();
        listItems.setAdapter(adapter);

        for(int i = 0; i < 10; i++){
            adapter.addItem("example XX점", (i+1.0), getResources().getDrawable(R.drawable.star_score), "garlic, fried chicken");
            adapter.addItem("test YY점", (i+1.0), getResources().getDrawable(R.drawable.star_score), "pizza, potato");
        }

        backHomeBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        listItems.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.backHomeBtn){
            getActivity().findViewById(R.id.navigation_home).performClick();
        } else if(v.getId() == R.id.deleteBtn){
            backHomeBtn.setVisibility(View.VISIBLE);
            userSearchText.setVisibility(View.GONE);
            searchText.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.GONE);
            searchBtn.setVisibility(View.VISIBLE);
            listItems.setVisibility(View.GONE);
            resultNoRest.setVisibility(View.GONE);
            searchInMsg.setVisibility(View.VISIBLE);

            searchText.setText("");
        } else if(v.getId() == R.id.searchBtn){
            backHomeBtn.setVisibility(View.GONE);
            userSearchText.setVisibility(View.VISIBLE);
            searchText.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.VISIBLE);
            searchBtn.setVisibility(View.GONE);
            searchInMsg.setVisibility(View.GONE);

            if(searchText.getText().toString().length() > 0 )
                listItems.setFilterText(searchText.getText().toString());
            else
                listItems.clearTextFilter();

            userSearchText.setText(searchText.getText().toString() + "\t\t" + adapter.getCount() + "개");

            if(adapter.getCount() > 0) {
                listItems.setVisibility(View.VISIBLE);
                resultNoRest.setVisibility(View.GONE);
            } else {
                listItems.setVisibility(View.GONE);
                resultNoRest.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getActivity().findViewById(R.id.navigation_shopping_cart).performClick();
    }
}
