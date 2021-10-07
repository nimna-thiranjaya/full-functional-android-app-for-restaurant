package com.example.grandlegacyresturant;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.grandlegacyresturant.Model.FoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;


public class FoodDetails extends AppCompatActivity {

    private Button addToCartBtn;
    private ImageView productImage;
    //private ElegantNumberButton numberButton;
    private TextView productPrice, productDescription, productName;
    private String foodId;
    TextView value;
    int count=0;
    Button plusButton,minasButton,feedBtn;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        foodId = getIntent().getStringExtra("pid");


        addToCartBtn = (Button) findViewById(R.id.add_products_to_cart_btn);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        productPrice = (TextView) findViewById(R.id.product_price_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productName = (TextView) findViewById(R.id.product_name_details);
        plusButton = (Button) findViewById(R.id.plus_btn);
        minasButton = (Button) findViewById(R.id.minus_btn);
        value = (TextView) findViewById(R.id.show_qty);
        feedBtn = (Button) findViewById(R.id.feedbackbtn);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count ++;
                value.setText("" + count);
            }
        });
        feedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodDetails.this,userfeedss.class);
                startActivity(intent);
            }
        });

        minasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count != 0)  {
                    count--;
                    value.setText("" + count);
                }else {
                    count = 0;
                }
            }
        });

        getFoodDetails(foodId);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addigToCartList();
            }
        });
    }

    private void addigToCartList() {


        DatabaseReference cartListref = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid",foodId);
        cartMap.put("name",productName.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("quantity",value.getText().toString());


        cartListref.child("User View").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Foods").child(foodId)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FoodDetails.this, "Added to cart List", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(FoodDetails.this,profileActivity.class);
                            startActivity(intent);
                        }
                    }
                });

    }

    private void getFoodDetails(String foodId) {
        DatabaseReference foodref = FirebaseDatabase.getInstance().getReference().child("Foods");
        foodref.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    FoodModel foods = snapshot.getValue(FoodModel.class);

                    productName.setText(foods.getName());
                    productPrice.setText(foods.getPrice());
                    productDescription.setText(foods.getFood_description());
                    Picasso.get().load(foods.getSurl()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}


