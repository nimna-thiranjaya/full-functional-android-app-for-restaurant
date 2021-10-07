package com.example.grandlegacyresturant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.grandlegacyresturant.Adapters.FoodAdapter;
import com.example.grandlegacyresturant.Model.FoodModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class FoodActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FoodAdapter foodAdapter;

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_food);
        this.setTitle("Foods");

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FoodModel> options =
                new FirebaseRecyclerOptions.Builder<FoodModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Foods"), FoodModel.class)
                        .build();

        foodAdapter = new FoodAdapter(options);
        recyclerView.setAdapter(foodAdapter);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddFoodActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        foodAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        foodAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch (String str)
    {
        FirebaseRecyclerOptions<FoodModel> options =
                new FirebaseRecyclerOptions.Builder<FoodModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Foods").orderByChild("name").startAt(str).endAt(str+"~"), FoodModel.class)
                        .build();

        foodAdapter = new FoodAdapter(options);
        foodAdapter.startListening();
        recyclerView.setAdapter(foodAdapter);
    }
}