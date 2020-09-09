package com.example.medicine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GetPhoneNumberToVerifyActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CheckSignInActi";
    EditText edt_country_code,edt_phone_number;
    Button btn_get_otp;
    private ProgressBar agp_spin_kit_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_get_phone_number_to_verify);
        edt_country_code = findViewById(R.id.edt_country_code);
        edt_phone_number = findViewById(R.id.edt_phone_number);
        btn_get_otp = findViewById(R.id.btn_get_otp);

        agp_spin_kit_progressbar = findViewById(R.id.agp_spin_kit_progressbar);

        edt_country_code.setOnClickListener(this);
        edt_phone_number.setOnClickListener(this);
        btn_get_otp.setOnClickListener(this);

        String phoneNumber = edt_country_code.getText().toString().trim()+edt_phone_number.getText().toString().trim();
        Log.d(TAG, "onClick: phoneNumber: "+phoneNumber);

       // JSONArray s = ["cardio", "micro", "psy"];
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==edt_country_code.getId()){
            edt_country_code.setCursorVisible(true);
        }else if (v.getId()==edt_phone_number.getId()){
            edt_phone_number.setCursorVisible(true);
        }else if (v.getId()==btn_get_otp.getId()){

            String phoneNumber = "0"+edt_phone_number.getText().toString();

            if (edt_country_code!=null&&edt_phone_number.getText().toString().length()==10){
                startActivity(new Intent(GetPhoneNumberToVerifyActivity.this,VerificationActivity.class)
                .putExtra("phoneNumber",phoneNumber));
                finish();

            }
            else {
                Toast.makeText(this, "Please insert valid phone number", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sp = getSharedPreferences("Authentication",Context.MODE_PRIVATE);
        String token = sp.getString("token",null);
        String role = sp.getString("role",null);

             //   startActivity(new Intent(GetPhoneNumberToVerifyActivity.this, HomeDoctorActivityD.class));

            Toast.makeText(this, "User Sign in, Role: "+role, Toast.LENGTH_SHORT).show();

    }
}
