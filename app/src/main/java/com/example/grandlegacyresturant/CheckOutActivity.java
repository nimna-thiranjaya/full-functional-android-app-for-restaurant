package com.example.grandlegacyresturant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.grandlegacyresturant.Adapters.CheckOutAdapter;
import com.example.grandlegacyresturant.Model.CheckOutModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CheckOutActivity extends AppCompatActivity {
        public   String totalamount;
    RecyclerView recyclerView;
    CheckOutAdapter checkOutAdapter;


    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        this.setTitle("Checkout");

        totalamount = getIntent().getStringExtra("Total Price");


        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CheckOutModel> options =
                new FirebaseRecyclerOptions.Builder<CheckOutModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("addresses").child(FirebaseAuth.getInstance().getCurrentUser().getUid()), CheckOutModel.class)
                        .build();

        checkOutAdapter = new CheckOutAdapter(options);
        recyclerView.setAdapter(checkOutAdapter);



        floatingActionButton=(FloatingActionButton)findViewById(R.id.RfloatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CheckOutAddActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkOutAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        checkOutAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tetSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                tetSearch(query);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
    private void tetSearch(String str)
    {
        FirebaseRecyclerOptions <CheckOutModel> options =
                new FirebaseRecyclerOptions.Builder<CheckOutModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("addresses").orderByChild("name").startAt(str).endAt(str+"~"), CheckOutModel.class)
                        .build();

        checkOutAdapter = new CheckOutAdapter(options);
        checkOutAdapter.startListening();
        recyclerView.setAdapter(checkOutAdapter);

    }
}