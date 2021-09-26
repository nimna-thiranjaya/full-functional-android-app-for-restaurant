package com.example.grandlegacyresturant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DeliveryDetailsShow extends AppCompatActivity {

    EditText tipForDp;
    Button addtip_BTN;
    TextView diplaytotal,disName,disAdd;
    private  DatabaseReference mDatabase;
    ProgressBar pd;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_delivery_details_show);

        tipForDp = (EditText) findViewById(R.id.adtip_box);
        addtip_BTN = (Button) findViewById(R.id.addtip_btn);
        diplaytotal = (TextView) findViewById(R.id.dis_total);
        disAdd = (TextView) findViewById(R.id.textView23);
        disName = (TextView) findViewById(R.id.textView26);
        tipForDp.getText().toString();
        prog();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Order");

        mDatabase.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                                                Object total = map.get("total");
                                                Object name = map.get("name");
                                                Object add = map.get("address");


                                                diplaytotal.setText(String.valueOf("Your Total Bill :" +total));
                                                disAdd.setText(String.valueOf(add));
                                                disName.setText(String.valueOf(name));

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Toast.makeText(DeliveryDetailsShow.this, "Faild", Toast.LENGTH_SHORT).show();

                                            }
                                        });

        addtip_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float tip = Float.parseFloat(tipForDp.getText().toString());

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Order");

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                        Object total = map.get("total");
                        float pValue = Float.parseFloat(String.valueOf(total));

                        float netTotal = pValue + tip;
                        diplaytotal.setText(String.valueOf("Your Total Bill :" +netTotal));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DeliveryDetailsShow.this, "Faild", Toast.LENGTH_SHORT).show();

                    }
                });




            }
        });

    }

    private void prog() {
        pd = findViewById(R.id.seekBar2);
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run()
            {
                counter++;
                pd.setProgress(counter);

                if(counter == 144000){
                    t.cancel();
                }
            }
        };
        t.schedule(tt, 0, 144000);
    }
}