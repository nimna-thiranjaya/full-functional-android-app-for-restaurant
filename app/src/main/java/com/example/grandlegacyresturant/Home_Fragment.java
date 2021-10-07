package com.example.grandlegacyresturant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grandlegacyresturant.Adapters.HomeAdapter;
import com.example.grandlegacyresturant.Model.FoodModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_Fragment} factory method to
 * create an instance of this fragment.
 */
public class Home_Fragment extends Fragment {


    RecyclerView recyclerView;
    HomeAdapter homeAdapter;

    public Home_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.rv1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<FoodModel> options = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Foods"), FoodModel.class)
                .build();
        homeAdapter = new HomeAdapter(options);
        recyclerView.setAdapter(homeAdapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        homeAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        homeAdapter.stopListening();
    }

}