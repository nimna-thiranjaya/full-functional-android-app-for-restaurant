package com.example.grandlegacyresturant;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddFoodActivity extends AppCompatActivity {

    EditText name,price,food_description,surl;
    Button btnAdd,btnBack;
    private String saveCurrentDate,saveCurrentTime,foodRandomKey;
    private DatabaseReference FoodRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        this.setTitle("Add new Product");

        name = (EditText)findViewById(R.id.txtFoodName);
        price = (EditText)findViewById(R.id.txtPrice);
        food_description = (EditText)findViewById(R.id.txtfood_description);
        surl = (EditText)findViewById(R.id.txtImageurl);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnBack = (Button)findViewById(R.id.btnBack);

        FoodRef = FirebaseDatabase.getInstance().getReference().child("Foods");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                clearAll();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void insertData()
    {
        storeFoodDetails();
        Map<String,Object>map = new HashMap<>();
        map.put("pid", foodRandomKey);
        map.put("name",name.getText().toString());
        map.put("price",price.getText().toString());
        map.put("food_description",food_description.getText().toString());
        map.put("surl",surl.getText().toString());

        FoodRef.child(foodRandomKey).updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddFoodActivity.this,"Data Inserted Successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AddFoodActivity.this,"Error while Insertion.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void storeFoodDetails() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        foodRandomKey = saveCurrentDate+saveCurrentTime;
    }

    private void clearAll()
    {
        name.setText("");
        price.setText("");
        food_description.setText("");
        surl.setText("");
    }
}