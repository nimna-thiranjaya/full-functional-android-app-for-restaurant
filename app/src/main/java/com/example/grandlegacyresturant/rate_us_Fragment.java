package com.example.grandlegacyresturant;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link rate_us_Fragment} factory method to
 * create an instance of this fragment.
 */
public class rate_us_Fragment extends Fragment {

    TextView mshowRating;
    private EditText mName, mfeedback;
    private  TextView mrateCount;

    RatingBar ratingbar;
    float rateValue; String temp;
    private Button button, button2;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Users");


    public rate_us_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rate_us_, container, false);
        mrateCount = v.findViewById(R.id.rateCount);
        ratingbar = v.findViewById(R.id.ratingBar);
        mshowRating = v.findViewById(R.id.showRating);

        mName = v.findViewById(R.id.editTextTextPersonName);
        mfeedback= v.findViewById(R.id.feedback);
        button = v.findViewById(R.id.submitBtn);
        button2= v.findViewById(R.id.button2);

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue = ratingBar.getRating();

                if (rateValue <= 1 && rateValue > 0)
                    mrateCount.setText("Bad  " + rateValue + "/5");
                else if (rateValue <= 2 && rateValue > 1)
                    mrateCount.setText("Ok  " + rateValue + "/5");
                else if (rateValue <= 3 && rateValue > 2)
                    mrateCount.setText("Good  " + rateValue + "/5");
                else if (rateValue <= 4 && rateValue > 3)
                    mrateCount.setText("Very Good  " + rateValue + "/5");
                else if (rateValue <= 5 && rateValue > 4)
                    mrateCount.setText("Superb   " + rateValue + "/5");
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextTextPersonName = mName.getText().toString();
                String feedback = mfeedback.getText().toString();
                String rateCount = mrateCount.getText().toString();

                HashMap<String,String> userMap = new HashMap<>();
                userMap.put("name", editTextTextPersonName);
                userMap.put("feedback",feedback);
                userMap.put("rateCount",rateCount);

                root.push().setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Data Inserted", Toast.LENGTH_SHORT).show();
                    }
                });


                temp = mrateCount.getText().toString();
                mshowRating.setText(mName.getText() + "\n\n" + temp + "\n" + mfeedback.getText());
                mfeedback.setText("");
                mName.setText("");
                ratingbar.setRating(0);
                mrateCount.setText("");

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() ,ShowActivity.class));
            }
        });


        return v;
    }
}