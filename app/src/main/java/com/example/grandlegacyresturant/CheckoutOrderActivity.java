package com.example.grandlegacyresturant;
import com.example.grandlegacyresturant.Holder.RandomString;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class CheckoutOrderActivity extends AppCompatActivity {
    Button myaddbtn;
    EditText nametxt,add1txt,add2txt,citytxt,districttxt,provincetxt;
    Button prOderBtn;
    private String totalAmount = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_checkout_order);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Tota; price ="+totalAmount, Toast.LENGTH_SHORT).show();

        prOderBtn = findViewById(R.id.proceedOrederBtn);
        myaddbtn = findViewById(R.id.my_addressbtn);
        nametxt =findViewById(R.id.nName);
        add1txt=findViewById(R.id.nAdd1);
        add2txt=findViewById(R.id.nAdd2);
        citytxt=findViewById(R.id.nCity);
        districttxt=findViewById(R.id.nDistrict);
        provincetxt=findViewById(R.id.nProvince);

        prOderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
        myaddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckoutOrderActivity.this,CheckOutActivity.class);
                startActivity(i);
            }
        });
    }
    private void Check() {
        if(TextUtils.isEmpty(nametxt.getText().toString())){
            Toast.makeText(this, "Please Provide Your Name", Toast.LENGTH_SHORT).show();
        }else {
            ConfirmOrder();
        }
    }
    private void ConfirmOrder() {

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        RandomString randomString= new RandomString();
        String result=randomString.generateAlpahaNumber(8);

        HashMap<String,Object> orderMap = new HashMap<>();
        orderMap.put("total",String.valueOf(totalAmount));
        orderMap.put("oid","ORD"+result);
        orderMap.put("name",nametxt.getText().toString());
        orderMap.put("add1",add1txt.getText().toString());
        orderMap.put("add2",add2txt.getText().toString());
        orderMap.put("city",citytxt.getText().toString());
        orderMap.put("district",districttxt.getText().toString());
        orderMap.put("province",provincetxt.getText().toString());
        orderMap.put("status","Delivery in Progress");

        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(CheckoutOrderActivity.this, "Your Order Has Been Placed successfully", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(CheckoutOrderActivity.this,profileActivity.class);
                                        i.addFlags(i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });

    }
}