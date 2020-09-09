package com.example.medicine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class Fragment_Doctor_Profile extends Fragment {
    private static final String TAG = "Fragment_Doctor_Profile";
    ProgressBar fdp_progressbar;
    String role = null, token = null;
    TextView fdp_txtv_name, fdp_txtv_phone,fdp_txtv_edit,fdp_txtv_email,fdp_txtv_gender,fdp_txtv_location,fdp_txtv_age,fdp_txtv_designation,fdp_txtv_institute,fdp_txtv_department;

    public Fragment_Doctor_Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor__profile, container, false);


        role = getActivity().getIntent().getStringExtra("role");
        token = getActivity().getIntent().getStringExtra("token");

        fdp_txtv_name = view.findViewById(R.id.fdp_name);
        fdp_txtv_phone = view.findViewById(R.id.fdp_phone);
        fdp_txtv_edit = view.findViewById(R.id.fdp_txtv_edit);
        fdp_txtv_email = view.findViewById(R.id.fdp_txtv_email);
        fdp_txtv_gender = view.findViewById(R.id.fdp_txtv_gender);
        fdp_txtv_location = view.findViewById(R.id.fdp_txtv_location);
        fdp_txtv_age = view.findViewById(R.id.fdp_txtv_age);
        fdp_txtv_designation = view.findViewById(R.id.fdp_txtv_designation);
        fdp_txtv_institute = view.findViewById(R.id.fdp_txtv_institute);
        fdp_txtv_department = view.findViewById(R.id.fdp_txtv_department);

        fdp_progressbar = view.findViewById(R.id.fdp_progressbar);


        if (token == null || role == null) {
            SharedPreferences sp = getContext().getSharedPreferences("Authentication", Context.MODE_PRIVATE);
            token = sp.getString("token", null);
            role = sp.getString("role", null);
        }

        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRequests apiRequests = retrofit.create(ApiRequests.class);

        Call<JsonObject> call = apiRequests.getDoctorDetails(token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jo = new JSONObject(response.body().toString());
                        JSONObject jsonObject = jo.getJSONObject("doctordetails");
                        Log.d(TAG, "onResponse: Doctor Details jsonObject: " + jsonObject.toString());
                        // if (jsonObject.getString("qualifications").equalsIgnoreCase("")) {

                        fdp_txtv_name.setText(jsonObject.getString("name"));
                        fdp_txtv_phone.setText(jsonObject.getString("phone"));
                        fdp_txtv_email.setText(jsonObject.getString("email"));
                        fdp_txtv_gender.setText(jsonObject.getString("gender"));
                        fdp_txtv_location.setText(jsonObject.getString("location"));
                        fdp_txtv_age.setText(jsonObject.getString("age"));
                        fdp_txtv_designation.setText(jsonObject.getString("designation"));
                        fdp_txtv_institute.setText(jsonObject.getString("institute"));
                        fdp_txtv_department.setText(jsonObject.getString("department"));


                        JSONArray jsonArray = jsonObject.getJSONArray("schedule");
                        JSONObject schedule1 = jsonArray.getJSONObject(0);
                        JSONObject schedule2 = jsonArray.getJSONObject(1);
                        JSONObject schedule3 = jsonArray.getJSONObject(2);

                        /*doc_schedule1 = "Day: " + schedule1.getString("schedule_day") + ", S.Time: " + schedule1.getString("start_time") + ", E.Time: " + schedule1.getString("end_time") + ", Status: " + schedule1.getString("status");
                        doc_schedule2 = "Day: " + schedule2.getString("schedule_day") + ", Time: " + schedule2.getString("start_time") + ", E.Time: " + schedule2.getString("end_time") + ", Status: " + schedule2.getString("status");
                        doc_schedule3 = "Day: " + schedule3.getString("schedule_day") + ", Time: " + schedule3.getString("start_time") + ", E.Time: " + schedule3.getString("end_time") + ", Status: " + schedule3.getString("status");
*/

/*
                        fp_txtv_edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //Toast.makeText(getContext(), "Clicked: 2", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getContext(), EditDoctorDetailsActivity.class)
                                        .putExtra("token", token));

                            }
                        });*/

                        // }
                        fdp_progressbar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    fdp_progressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                fdp_progressbar.setVisibility(View.GONE);
            }
        });
















        return view;
    }

}
