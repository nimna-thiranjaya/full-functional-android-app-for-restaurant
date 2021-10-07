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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLogin extends AppCompatActivity {
    Button button;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    EditText inputEmail,inputPassword;
    TextView forbtn;
    Button abtnlogin;

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_admin_login);
        inputEmail= findViewById(R.id.email_box);
        inputPassword= findViewById(R.id.password_box);
        progressDialog =new ProgressDialog(this);
        mAuth =FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        abtnlogin= findViewById(R.id.admin_login_btn);
        forbtn = findViewById(R.id.admin_forgetpass_btn);

        forbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminLogin.this,FogetPasswordActivity.class);
                startActivity(intent);
            }
        });

        abtnlogin.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(AdminLogin.this,"Login Successful" +task.getException(), Toast.LENGTH_SHORT).show();
                        sendUserToNextActivity();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(AdminLogin.this, "Login Fail" + task.getException(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(AdminLogin.this, FoodActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}