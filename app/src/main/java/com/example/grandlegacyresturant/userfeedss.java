package com.example.grandlegacyresturant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.grandlegacyresturant.Adapters.MyUAdapter;
import com.example.grandlegacyresturant.Model.userModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class userfeedss extends AppCompatActivity {


    RecyclerView recyclerView;
    MyUAdapter myUAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_userfeedss);

        recyclerView = (RecyclerView)findViewById(R.id.rvf);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<userModel> options =
                new FirebaseRecyclerOptions.Builder<userModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Ufeeds"),userModel.class)
                        .build();

        myUAdapter = new MyUAdapter(options);
        recyclerView.setAdapter(myUAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        myUAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myUAdapter.stopListening();
    }
}