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
import androidx.appcompat.widget.Toolbar;

import com.example.medicine.DataType.DoctorProfile;
import com.example.medicine.DataType.PatientProfile;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SetUpProfileActivity extends AppCompatActivity {
    private static final String TAG = "SetUpProfileActivity";

    Button btn_asu_signup;

    EditText edt_asu_name, edt_asu_email, edt_asu_password, edt_asu_repeat_pass, edt_asu_phone_number;

    String phone = "", role = "", token = "";
    ProgressBar asu_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edt_asu_name = findViewById(R.id.edt_asu_name);
        edt_asu_email = findViewById(R.id.edt_asu_email);
        edt_asu_password = findViewById(R.id.edt_asu_password);
        edt_asu_repeat_pass = findViewById(R.id.edt_asu_repeat_pass);
        edt_asu_phone_number = findViewById(R.id.edt_asu_phone_number);

        asu_progressbar = findViewById(R.id.asu_progressbar);

        phone = getIntent().getStringExtra("phone");
        role = getIntent().getStringExtra("role");
        token = getIntent().getStringExtra("token");






        if (phone != null) {
            edt_asu_phone_number.setText(phone);
            edt_asu_phone_number.setFocusable(false);
        }

        btn_asu_signup = findViewById(R.id.btn_asu_signup);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetUpProfileActivity.this, GetPhoneNumberToVerifyActivity.class));
            }
        });

        btn_asu_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (asu_progressbar.getVisibility() == View.VISIBLE) {
                    Toast.makeText(SetUpProfileActivity.this, "Task is in progress", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = edt_asu_name.getText().toString();
                String email = edt_asu_email.getText().toString();
                String password = edt_asu_password.getText().toString();
                String rep_pass = edt_asu_repeat_pass.getText().toString();
                if (name.length() > 0 && email.length() > 0 && password.length() > 0) {
                    if (password.equals(rep_pass)) {

                        if (role.equalsIgnoreCase("3")) {
                            PatientProfile dataType_patient_profile
                                    = new PatientProfile(name, email, password, role);
                            asu_progressbar.setVisibility(View.VISIBLE);
                            setPatientProfile(dataType_patient_profile);
                        }

                    } else {
                        Toast.makeText(SetUpProfileActivity.this, "Password not matched..!", Toast.LENGTH_SHORT).show();
                        edt_asu_repeat_pass.requestFocus();
                    }
                } else {
                    Toast.makeText(SetUpProfileActivity.this, "All data is required...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setPatientProfile(PatientProfile dataType_patient_profile) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequests apiRequests = retrofit.create(ApiRequests.class);

        Call<JsonObject> call = apiRequests.setPatientProfile(token, dataType_patient_profile);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(SetUpProfileActivity.this, "Profile set is successfull\n"+response.body().toString(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: Profile set is successfull: ResponseBody: " + response.body().toString());
                    asu_progressbar.setVisibility(View.GONE);
                    if (token != null) {



                        //Getting patient id...
                        Call<JsonObject> call_patient_details = apiRequests.getPatientDetails(token);
                        call_patient_details.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if (response.isSuccessful()){
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().toString());
                                        JSONObject patientdetailsObject = jsonObject.getJSONObject("patientdetails");

                                        if (token!=null&&patientdetailsObject!=null){
                                            SharedPreferences sp = getSharedPreferences("Authentication", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putString("token",token);
                                            editor.putString("role",role);
                                            editor.putString("patient_id",String.valueOf(patientdetailsObject.getInt("id")));
                                            editor.commit();

                                        }else {
                                            Toast.makeText(SetUpProfileActivity.this, "Token or Phone Number is null", Toast.LENGTH_SHORT).show();
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



                        startActivity(new Intent(SetUpProfileActivity.this, MainActivity.class)
                                    .putExtra("checkedFragment", "profile")
                                    .putExtra("name", edt_asu_name.getText().toString())
                                    .putExtra("email", edt_asu_email.getText().toString())
                                    .putExtra("phone", phone)
                                    .putExtra("role", role)
                                    .putExtra("token", token)
                            );
                    } else {
                        Toast.makeText(SetUpProfileActivity.this, "Token is null", Toast.LENGTH_SHORT).show();
                    }

                    finish();
                } else {
                    asu_progressbar.setVisibility(View.GONE);
                    Toast.makeText(SetUpProfileActivity.this, "Patient profile set Failed", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: " + response.toString());
//                    Log.d(TAG, "onResponse: Profile set Failed: "+ response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                asu_progressbar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }


    private void setDoctorProfile(DoctorProfile dataType_doctor_profile) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequests apiRequests = retrofit.create(ApiRequests.class);

        Call<JsonObject> call = apiRequests.setDoctorProfile(token, dataType_doctor_profile);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SetUpProfileActivity.this, " Doctor profile set is successfull\n" + response.body().toString(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    asu_progressbar.setVisibility(View.GONE);

                    startActivity(new Intent(SetUpProfileActivity.this, MainActivity.class)
                            .putExtra("checkedFragment", "profile")
                            .putExtra("name", edt_asu_name.getText().toString())
                            .putExtra("email", edt_asu_email.getText().toString())
                            .putExtra("phone", edt_asu_phone_number.getText().toString())
                            .putExtra("role", role)
                            .putExtra("token", token)
                    );
                } else {
                    asu_progressbar.setVisibility(View.GONE);
                    Toast.makeText(SetUpProfileActivity.this, "Profile set Failed", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: " + response.toString());
                    Log.d(TAG, "onResponse: Profile set Failed: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                asu_progressbar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
