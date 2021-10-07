package com.example.grandlegacyresturant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.grandlegacyresturant.Adapters.MyAdapter;
import com.example.grandlegacyresturant.Model.Model;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    com.example.grandlegacyresturant.Adapters.MyAdapter MyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"),Model.class)
                        .build();


        MyAdapter = new MyAdapter(options);
        recyclerView.setAdapter(MyAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        MyAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyAdapter.stopListening();
    }
}