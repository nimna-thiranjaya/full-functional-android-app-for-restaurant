package com.example.grandlegacyresturant;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import com.example.grandlegacyresturant.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;


public class signupActivity extends AppCompatActivity {

    EditText inputFname,inputLname,inputEmail,inputUsername,inputpNo,inputPassword,inputCpassword;
    Button btnRegister;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase db;
    DatabaseReference user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);

        inputFname = findViewById(R.id.s_fname_box);
        inputLname = findViewById(R.id.s_lname_box);
        inputEmail = findViewById(R.id.s_email_box);
        inputUsername= findViewById(R.id.s_un_box);
        inputpNo= findViewById(R.id.s_pNo_box);
        inputPassword = findViewById(R.id.s_pass_box);
        inputCpassword = findViewById(R.id.s_cpass_box);
        progressDialog =new ProgressDialog(this);
        mAuth =FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        btnRegister = findViewById(R.id.s_submit_btn);
        user = db.getReference("User");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAuth();
            }
        });

    }

    private void PerformAuth() {
        String fname = inputFname.getText().toString();
        String lname = inputLname.getText().toString();
        String email = inputEmail.getText().toString();
        String userName = inputUsername.getText().toString();
        String dob = inputpNo.getText().toString();
        String password = inputPassword.getText().toString();
        String cpassword = inputCpassword.getText().toString();

        if(!email.matches(emailPattern)){
            inputEmail.setError("Enter correct Email");
        }else if(password.isEmpty() || password.length()<6){
            inputPassword.setError("Enter proper password");
        }else if(!password.equals(cpassword)){
            inputCpassword.setError("password not match");
        }else {
            progressDialog.setMessage("Please wait while Registration");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();


                        //save data to db
                        User newUser = new User();
                        newUser.setFname(inputFname.getText().toString());
                        newUser.setLname(inputLname.getText().toString());
                        newUser.setEmail(inputEmail.getText().toString());
                        newUser.setUserName(inputUsername.getText().toString());
                        newUser.setPno(inputpNo.getText().toString());

                        user.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(newUser)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        Toast.makeText(signupActivity.this,"Register Successful" +task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(signupActivity.this,"Registration Fail" +task.getException(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(signupActivity.this,"Registration Fail" +task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(signupActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}