package com.example.medicine;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicine.InternetConnection.ConnectivityDetection;
import com.google.android.material.button.MaterialButton;


public class PreviewActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialButton ap_login,ap_register;
    ConnectivityDetection connectivityDetection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_preview);

        ap_login = findViewById(R.id.ap_login);
        ap_register = findViewById(R.id.ap_register);

        connectivityDetection = new ConnectivityDetection((PreviewActivity.this));

        ap_login.setOnClickListener(this);
        ap_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ap_login: {
                startActivity(new Intent(PreviewActivity.this, LogInActivity.class));
                break;
            }
            case R.id.ap_register: {
                startActivity(new Intent(PreviewActivity.this, GetPhoneNumberToVerifyActivity.class));
                break;
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        //for connetivity detection
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityDetection, filter);



        SharedPreferences sp = getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        String token = sp.getString("token",null);
        String role = sp.getString("role",null);
        if (role!=null && token!=null) {
          if (role.equalsIgnoreCase("3")) {
                startActivity(new Intent(PreviewActivity.this, MainActivity.class));
                finish();
            }
            Toast.makeText(this, "User Sign in, Role: " + role, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "user not sign in", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(connectivityDetection);
    }
}
