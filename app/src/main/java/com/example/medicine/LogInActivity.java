package com.example.medicine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.medicine.DataType.LogIn;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.medicine.DataType.LogIn;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity {
    private static final String TAG = "LogInActivity";

    Retrofit retrofit;
    ApiRequests apiRequests;
    TextInputEditText ali_tiedt_email,ali_tiedt_password;

    Button btn_ali_login;
    private ProgressBar al_spin_kit_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.al_toolbar);
        setSupportActionBar(toolbar);

        btn_ali_login = findViewById(R.id.btn_ali_login);
        ali_tiedt_email = findViewById(R.id.ali_tiedt_email);
        ali_tiedt_password = findViewById(R.id.ali_tiedt_password);

        al_spin_kit_progressbar = findViewById(R.id.al_spin_kit_progressbar);
        FadingCircle fadingCircle = new FadingCircle();
        al_spin_kit_progressbar.setIndeterminateDrawable(fadingCircle);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiRequests = retrofit.create(ApiRequests.class);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this,PreviewActivity.class));
            }
        });

        btn_ali_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LogInActivity.this, "login Clicked", Toast.LENGTH_SHORT).show();

                String email = ali_tiedt_email.getText().toString();
                String pass = ali_tiedt_password.getText().toString();
                LogIn dataType_login = new LogIn(email,pass);

                if (email!=null&&pass!=null){

                    if (apiRequests!=null){
                        al_spin_kit_progressbar.setVisibility(View.VISIBLE);

                        Call<JsonObject> call = apiRequests.postLogin(dataType_login);
                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                al_spin_kit_progressbar.setVisibility(View.GONE);
                                if (response.isSuccessful()){
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().toString());
                                        String token = "Bearer "+jsonObject.getString("token");
                                        JSONObject userObject = jsonObject.getJSONObject("user");
                                        String phoneNumber = userObject.getString("phone");
                                        String role = userObject.getString("role");

                                        Log.d(TAG, "onResponse: jsonObject: "+ jsonObject.toString());
                                        Log.d(TAG, "onResponse: phone: "+ phoneNumber);
                                        Log.d(TAG, "onResponse: role: "+ role);
                                        Log.d(TAG, "onResponse: token: "+ token);


                                        //Getting patient id...
                                        Call<JsonObject> call_patient_details = apiRequests.getPatientDetails(token);
                                        call_patient_details.enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                if (response.isSuccessful()){
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response.body().toString());
                                                        JSONObject patientdetailsObject = jsonObject.getJSONObject("patientdetails");

                                                        if (token!=null&&userObject!=null){
                                                            SharedPreferences sp = getSharedPreferences("Authentication", Context.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = sp.edit();
                                                            editor.putString("token",token);
                                                            editor.putString("role",role);
                                                            editor.putString("patient_id",String.valueOf(patientdetailsObject.getInt("id")));
                                                            editor.commit();

                                                        }else {
                                                            Toast.makeText(LogInActivity.this, "Token or Phone Number is null", Toast.LENGTH_SHORT).show();
                                                            Log.d(TAG, "onResponse: "+"Patient Details: Token or Phone Number is null\"");
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }else {
                                                    Toast.makeText(getApplicationContext(),"Patient Details: Response Failed",Toast.LENGTH_SHORT);
                                                    Log.d(TAG, "onResponse: "+"Patient Details: Response Failed: "+response.errorBody());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(),"Patient Details: Failed",Toast.LENGTH_SHORT);
                                                Log.d(TAG, "onFailure: "+"PatientDetails: Failed");
                                            }
                                        });


                                        startActivity(new Intent(LogInActivity.this, MainActivity.class)
                                                .putExtra("phone", phoneNumber)
                                                .putExtra("role", role)
                                                .putExtra("token", token)
                                                .putExtra("checkedFragment", "profile")
                                        );


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Toast.makeText(LogInActivity.this, "Response Failed: "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onResponse: Response Failed: "+ response.errorBody().toString());
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                al_spin_kit_progressbar.setVisibility(View.GONE);
                                Toast.makeText(LogInActivity.this, "Failed: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse: Failed: "+ t.getMessage().toString());
                            }
                        });

                    }else {
                        Toast.makeText(LogInActivity.this, "Api Requests is null", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(LogInActivity.this, "email or password is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
