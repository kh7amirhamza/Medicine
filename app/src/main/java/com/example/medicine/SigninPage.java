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

public class SigninPage extends AppCompatActivity {

    private Button signinBtn;
    private EditText mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_page);

        signinBtn = (Button)findViewById(R.id.signinBtnID);
        mobileNumber = (EditText)findViewById(R.id.signinNumberID);

        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinBtn.setEnabled(false);
                signinBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CCD7DD")));
                signinBtn.setTextColor(ColorStateList.valueOf(Color.parseColor("#979797")));
                signinBtn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#979797")));

                Intent intent = new Intent(SigninPage.this,Varification.class);
                startActivity(intent);
                finish();
            }
        });

        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(mobileNumber.getText())){
                    signinBtn.setEnabled(true);
                    signinBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4286F5")));
                    signinBtn.setTextColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    signinBtn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                }else {
                    signinBtn.setEnabled(false);
                    signinBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#CCD7DD")));
                    signinBtn.setTextColor(ColorStateList.valueOf(Color.parseColor("#979797")));
                    signinBtn.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#979797")));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}