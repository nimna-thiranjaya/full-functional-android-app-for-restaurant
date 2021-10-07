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
import android.widget.TextView;
import android.widget.Toast;

import com.example.grandlegacyresturant.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    Button button;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    EditText  inputEmail,inputPassword;
    TextView forbtn;
    Button btnlogin;

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);
        inputEmail= findViewById(R.id.email_box);
        inputPassword= findViewById(R.id.password_box);
        progressDialog =new ProgressDialog(this);
        mAuth =FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        btnlogin= findViewById(R.id.admin_login_btn);
        forbtn = findViewById(R.id.admin_forgetpass_btn);




        button = findViewById(R.id.l_create_btn);
        button.setOnClickListener(v -> openCreateAccount());

        forbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,FogetPasswordActivity.class);
                startActivity(intent);
            }
        });
        
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });


        
    }

    private void performLogin() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if(!email.matches(emailPattern)){
            inputEmail.setError("Enter correct Email");
        }else if(password.isEmpty() || password.length()<6){
            inputPassword.setError("Enter password");
        }else {
            progressDialog.setMessage("Please wait while Login");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(LoginActivity.this,"Login Successful" +task.getException(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginActivity.this, "Login Fail" + task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

}
    public void openCreateAccount(){
        Intent intent =new Intent(this,signupActivity.class);
        startActivity(intent);
    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(LoginActivity.this,profileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}