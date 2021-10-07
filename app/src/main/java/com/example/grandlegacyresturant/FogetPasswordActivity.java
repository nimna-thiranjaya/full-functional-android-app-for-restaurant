package com.example.grandlegacyresturant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class FogetPasswordActivity extends AppCompatActivity {
    Button sumbitemail;
    EditText inputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_foget_password);

        inputEmail = findViewById(R.id.forgetpasstxt);
        sumbitemail = findViewById(R.id.button);

        sumbitemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =inputEmail.getText().toString();

                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(FogetPasswordActivity.this, "email send successful", Toast.LENGTH_SHORT).show();
                            Intent i =new Intent(FogetPasswordActivity.this,LoginActivity.class);
                            startActivity(i);

                        }else {
                            Toast.makeText(FogetPasswordActivity.this, "Error"+task.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

    }
}