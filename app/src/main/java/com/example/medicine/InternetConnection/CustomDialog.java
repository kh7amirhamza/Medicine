package com.example.medicine.InternetConnection;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.example.medicine.R;


public class CustomDialog extends Dialog {
    public CustomDialog(@androidx.annotation.NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_connectivity_dialog);
    }
}
