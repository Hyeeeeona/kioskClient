package com.example.kioskclient;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMenuMyPage extends Fragment {

    private FirebaseAuth mAuth;

    public FragmentMenuMyPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_my_page, null);
        mAuth = FirebaseAuth.getInstance();



        view.findViewById(R.id.favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentFavorite fragmentFavorite = new FragmentFavorite();
                setFragment(fragmentFavorite);
            }
        });

        view.findViewById(R.id.order_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentOrderHistory fragmentOrderHistory = new FragmentOrderHistory();
                setFragment(fragmentOrderHistory);
            }
        });

        view.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }

    private void setFragment(Fragment child) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            transaction.replace(R.id.linear_layout, child);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

}
