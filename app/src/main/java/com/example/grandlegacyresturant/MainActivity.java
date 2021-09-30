package com.example.grandlegacyresturant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button,admin_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        button =(Button) findViewById(R.id.r_start_btn);
        admin_btn = (Button) findViewById(R.id.login_Admin_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityLogin();
            }
        });

        admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityAdminLogin();
            }
        });





    }

    private void openActivityAdminLogin() {
        Intent intent =new Intent(this,AdminLoginActivity.class);
        startActivity(intent);
    }

    public void openActivityLogin(){
        Intent intent =new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}