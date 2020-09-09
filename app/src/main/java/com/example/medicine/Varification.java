package com.example.medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Varification extends AppCompatActivity {

    private EditText otpCode;
    private Button otpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varification);

        otpCode = (EditText)findViewById(R.id.varificationOTPID);
        otpButton = (Button)findViewById(R.id.varificationBtnID);

        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpButton.setEnabled(false);
                otpButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CCD7DD")));
                otpButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#979797")));

                Intent intent = new Intent(Varification.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        otpCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }





            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(otpCode.getText())){
                    if (otpCode.getText().length() == 4){
                        otpButton.setEnabled(true);
                        otpButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4286F5")));
                        otpButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));

                    }else {
                        otpButton.setEnabled(false);
                        otpButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CCD7DD")));
                        otpButton.setTextColor(ColorStateList.valueOf(Color.parseColor("#979797")));

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}