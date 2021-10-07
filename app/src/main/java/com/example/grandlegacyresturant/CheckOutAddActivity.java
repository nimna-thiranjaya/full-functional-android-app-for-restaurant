package com.example.grandlegacyresturant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CheckOutAddActivity extends AppCompatActivity {

    EditText name,add1,add2,city,district,province;
    Button btnAdd,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add2);
        this.setTitle("Add new address");



        name = (EditText)findViewById(R.id.nName);
        add1 = (EditText)findViewById(R.id.nAdd1);
        add2 = (EditText)findViewById(R.id.nAdd2);
        city = (EditText)findViewById(R.id.nCity);
        district = (EditText)findViewById(R.id.nDistrict);
        province = (EditText)findViewById(R.id.nProvince);

        btnAdd= (Button)findViewById(R.id.proceedOrederBtn);
        btnBack= (Button)findViewById(R.id.my_addressbtn);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                clearAll();
                check();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void check() {

        if(!TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(this, "Please provide your name!", Toast.LENGTH_SHORT).show();
        }

    }

    private void insertData()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("add1",add1.getText().toString());
        map.put("add2",add2.getText().toString());
        map.put("city",city.getText().toString());
        map.put("district",district.getText().toString());
        map.put("province",province.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("addresses").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CheckOutAddActivity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CheckOutAddActivity.this, "Error While Added", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void clearAll()
    {
        name.setText("");
        add1.setText("");
        add2.setText("");
        city.setText("");
        district.setText("");
        province.setText("");

    }
}