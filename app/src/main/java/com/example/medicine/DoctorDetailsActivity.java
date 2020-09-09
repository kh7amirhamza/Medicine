package com.example.medicine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoctorDetailsActivity extends AppCompatActivity {

    private static final String TAG = "DoctorDetailsActivity";

    TextView add_txtv_doctor_name,add_txtv_doctor_category,add_txtv_doctor_department,add_txtv_doctor_designation,
            add_txtv_doctor_institute,add_txtv_consultation_fee,add_txtv_doctor_other_details;

    RecyclerView add_recy_doctor_schedule;
    int doctorId = 0;
    String jsonResponse=null,token = null,catName;

    MaterialButton add_btn_place_appointment;
    ScrollView add_scrollview;
    private MaterialToolbar materialToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        materialToolbar = findViewById(R.id.add_toolbar);
        setSupportActionBar(materialToolbar);
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        add_txtv_doctor_name = findViewById(R.id.add_txtv_doctor_name);
        add_txtv_doctor_category = findViewById(R.id.add_txtv_doctor_category);
        add_txtv_doctor_department = findViewById(R.id.add_txtv_doctor_department);
        add_txtv_doctor_designation = findViewById(R.id.add_txtv_doctor_designation);
        add_txtv_doctor_institute = findViewById(R.id.add_txtv_doctor_institute);
        add_txtv_consultation_fee = findViewById(R.id.add_txtv_consultation_fee);
        add_txtv_doctor_other_details = findViewById(R.id.add_txtv_doctor_other_details);

        add_btn_place_appointment = findViewById(R.id.add_btn_place_appointment);

        add_recy_doctor_schedule = findViewById(R.id.add_recy_doctor_schedule);
        //add_recy_doctor_schedule.setHasFixedSize(true);
        add_recy_doctor_schedule.setLayoutManager(new LinearLayoutManager(this));

        add_scrollview = findViewById(R.id.add_scrollview);
        add_scrollview.smoothScrollTo(0,100);



        jsonResponse = getIntent().getStringExtra("jsonResponse");
        Log.d(TAG, "onCreate: doctorJson: "+ jsonResponse);
        doctorId = getIntent().getIntExtra("doctorId",0);
        token = getIntent().getStringExtra("token");
        catName = getIntent().getStringExtra("catName");

        if (jsonResponse!=null){
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                JSONArray jsonArray = jsonObject.getJSONArray("all_doctor");
                Log.d(TAG, "onResponse: jsonArrayLength: " + jsonArray.length());
                if (doctorId==0){
                    Toast.makeText(this, "DoctorID  is 0", Toast.LENGTH_SHORT).show();
                    return;
                }
            JSONObject doctor_object = jsonArray.getJSONObject(doctorId-1);

                DataType_AllDoctorDetails.Rating rating = getRating(doctor_object);
                List<DataType_AllDoctorDetails.Schedule_position> schedulePositionList = getSchedulePositions(doctor_object);
                DataType_AllDoctorDetails.Schedule schedule = new DataType_AllDoctorDetails.Schedule(schedulePositionList);

                DataType_AllDoctorDetails doctorDetails = new DataType_AllDoctorDetails(
                        String.valueOf(doctor_object.getInt("id")),
                        doctor_object.getString("p_photo")
                        , doctor_object.getString("user_id")
                        , doctor_object.getString("doctor_category_id")
                        , doctor_object.getString("gender")
                        , doctor_object.getString("location")
                        , doctor_object.getString("age")
                        , doctor_object.getString("qualifications")
                        , doctor_object.getString("specialty")
                        , doctor_object.getString("language_spoken")
                        , doctor_object.getString("designation")
                        , doctor_object.getString("institute")
                        , doctor_object.getString("department")
                        , doctor_object.getString("created_at")
                        , doctor_object.getString("updated_at")
                        , rating
                        ,schedule);


                add_txtv_doctor_category.setText(catName);
                add_txtv_doctor_designation.setText(doctorDetails.getDesignation());
                add_txtv_doctor_institute.setText(doctorDetails.getInstitute());
                add_txtv_doctor_department.setText(doctorDetails.getDepartment());


                Adapter_Doctor_Schedule adapter_doctor_schedule = new Adapter_Doctor_Schedule(getApplicationContext(),schedule);
                add_recy_doctor_schedule.setAdapter(adapter_doctor_schedule);


                add_btn_place_appointment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(DoctorDetailsActivity.this,PlaceAppointmentActivity.class)
                                .putExtra("doctor_object",doctor_object.toString())
                                .putExtra("doctor_id",doctorDetails.getId())
                                .putExtra("token",token));
                    }
                });

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }


    private DataType_AllDoctorDetails.Rating getRating(JSONObject joSingleDoctorDetails) {
        DataType_AllDoctorDetails.Rating rating = null;
        JSONObject jo_SingleDoctorrating = null;
        try {
            jo_SingleDoctorrating = joSingleDoctorDetails.getJSONObject("rating");

            if (jo_SingleDoctorrating.length() > 0) {
                rating = new DataType_AllDoctorDetails.Rating(
                        jo_SingleDoctorrating.getString("id")
                        , jo_SingleDoctorrating.getString("count")
                        , jo_SingleDoctorrating.getString("success")
                        , jo_SingleDoctorrating.getString("failure")
                        , jo_SingleDoctorrating.getString("doctor_id")
                        , jo_SingleDoctorrating.getString("created_at")
                        , jo_SingleDoctorrating.getString("updated_at"));

            } else {
                rating = new DataType_AllDoctorDetails.Rating("...", "...", "...", "...", "...", "...", "...");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rating;
    }


    private List<DataType_AllDoctorDetails.Schedule_position> getSchedulePositions(JSONObject joSingleDoctor) {
        JSONArray ja_schedule = null;
        List<DataType_AllDoctorDetails.Schedule_position> schedulePositionList =null;
        try {
            ja_schedule = joSingleDoctor.getJSONArray("schedule");
            schedulePositionList = new ArrayList<>();

            if (ja_schedule.length() > 0) {
                for (int j = 0; j < ja_schedule.length(); j++) {
                    JSONObject jo_single_schedule = ja_schedule.getJSONObject(j);
                    if (jo_single_schedule.length() > 0) {
                        DataType_AllDoctorDetails.Schedule_position schedule_position = new DataType_AllDoctorDetails.Schedule_position(
                                jo_single_schedule.getString("schedule_day")
                                , jo_single_schedule.getString("start_time")
                                , jo_single_schedule.getString("end_time")
                                , jo_single_schedule.getString("status")
                        );
                        Log.d(TAG, "onResponse: schedule 0 day: " + schedule_position.getSchedule_day());
                        schedulePositionList.add(schedule_position);
                    } else {
                        DataType_AllDoctorDetails.Schedule_position schedule_position = new DataType_AllDoctorDetails.Schedule_position("...", "...", "...", "...");
                        Log.d(TAG, "onResponse: schedule 0 day: " + schedule_position.getSchedule_day());
                        schedulePositionList.add(schedule_position);
                    }
                }
            } else {
                DataType_AllDoctorDetails.Schedule_position schedule_position = new DataType_AllDoctorDetails.Schedule_position("...", "...", "...", "...");
                schedulePositionList.add(schedule_position);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return schedulePositionList;
    }


    private String[] getFormatedStringArray(String string) {
        String finalString = "";
        for (int i = 0; i < string.length(); i++) {
            char chatat = string.charAt(i);
            String formatedString = "";
            if ((chatat != ',') && !(((int) chatat >= 65) && ((int) chatat <= 90) || ((int) chatat >= 97) && ((int) chatat <= 122))) {
                finalString = finalString;
            } else {
                finalString = finalString + chatat;
            }
        }
        String string1 = finalString.substring(0, finalString.indexOf(','));
        Log.d(TAG, "getFormatedStringArray: s1: " + string1);

        finalString = finalString.replace(string1 + ",", "");
        Log.d(TAG, "getFormatedStringArray: s2: " + finalString);

        if (finalString.contains(",")) {
            String string2 = finalString.substring(0, finalString.indexOf(','));

            finalString = finalString.replace(string2 + ",", "");
            String string3 = finalString;
            Log.d(TAG, "getFormatedStringArray: s3: " + finalString);
            return new String[]{string1, string2, string3};
        } else {
            return new String[]{string1, finalString};
        }
    }
}

