package com.example.grandlegacyresturant;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.grandlegacyresturant.Model.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class profileUpdateActivity extends AppCompatActivity {

    Button saveBtn,cancelBtn;
    EditText displayFname,displayLname,displayEmail,displayUsername,displaypNo;
    FirebaseUser mUser;
    FirebaseAuth mAuth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_profile_update);

        mAuth = FirebaseAuth.getInstance();

        displayFname = findViewById(R.id.update_fname);
        displayLname = findViewById(R.id.update_lname);
        displayEmail = findViewById(R.id.update_email);
        displayUsername = findViewById(R.id.update_username);
        displaypNo = findViewById(R.id.update_pno);
        saveBtn = findViewById(R.id.up_save_btn);

       displayUsername.setText(GlobalVal.currentUser.getUserName());
        displayFname.setText(GlobalVal.currentUser.getFname());
        displayLname .setText(GlobalVal.currentUser.getLname());
        displaypNo.setText(GlobalVal.currentUser.getPno());
        displayEmail.setText(GlobalVal.currentUser.getEmail());


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = displayFname.getText().toString().trim();
                String lName = displayLname.getText().toString().trim();
                String uName = displayUsername.getText().toString().trim();
                String pNo = displaypNo.getText().toString().trim();
                String email = displayEmail.getText().toString().trim();

                UpdateFirebaseData(fName,lName,uName,pNo,email);

                mUser = FirebaseAuth.getInstance().getCurrentUser();

            }
        });


    }

    private void UpdateFirebaseData(String fName, String lName, String uName, String pNo, String email) {
        ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(mAuth.getCurrentUser().getUid());
        Map<String,Object> updates = new HashMap<String,Object>();
        updates.put("fname",fName);
        updates.put("lname",lName);
        updates.put("email",email);
        updates.put("pno",pNo);
        updates.put("userName",uName);


        ref.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(profileUpdateActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    sendUserToNextActivity();
                    FirebaseDatabase.getInstance().getReference("User")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    GlobalVal.currentUser = snapshot.getValue(User.class);
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });

                }else {
                    progressDialog.dismiss();
                    Toast.makeText(profileUpdateActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(profileUpdateActivity.this,profileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}