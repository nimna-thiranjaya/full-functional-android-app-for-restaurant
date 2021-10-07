package com.example.grandlegacyresturant;

import com.example.grandlegacyresturant.Model.OrderModel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Delivery_Fragment} factory method to
 * create an instance of this fragment.
 */
public class Delivery_Fragment extends Fragment {

    EditText tipForDp;
    Button addtip_BTN;
    TextView diplaytotal,disName,disAdd;
    private  DatabaseReference mDatabase;
    ProgressBar pd;
    int counter = 0;

    private RecyclerView.LayoutManager layoutManager;
    private TextView orderidtxt,statustxt,totaltxt;

    public Delivery_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_delivery_, container, false);
        tipForDp = (EditText) v.findViewById(R.id.adtip_box);
        addtip_BTN = (Button) v.findViewById(R.id.addtip_btn);
        diplaytotal = (TextView) v.findViewById(R.id.dis_total);
        disAdd = (TextView) v.findViewById(R.id.textView23);
        disName = (TextView) v.findViewById(R.id.textView26);
        tipForDp.getText().toString();

        prog(pd = v.findViewById(R.id.seekBar2));



        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                Object total = map.get("total");
                Object name = map.get("name");
                Object add = map.get("add1");
                Object add2 = map.get("add2");
                Object city = map.get("city");


                diplaytotal.setText(String.valueOf("Your Total Bill :" + total));
                disAdd.setText(String.valueOf(add+","+add2+","+ city));
                disName.setText(String.valueOf(name));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Faild", Toast.LENGTH_SHORT).show();

            }
        });

        addtip_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float tip = Float.parseFloat(tipForDp.getText().toString());

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

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
                        Toast.makeText(getContext(), "Faild", Toast.LENGTH_SHORT).show();

                    }
                });




            }
        });

        return v;

    }

    private void prog(ProgressBar progressBar) {
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run()
            {
                counter++;
                pd.setProgress(counter);

                if(counter == 120){
                    t.cancel();
                }
            }
        };
        t.schedule(tt, 2, 2101*20);
    }


}