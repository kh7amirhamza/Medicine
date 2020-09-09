package com.example.medicine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceAppointmentActivity extends AppCompatActivity {

    private static final String TAG = "PlaceAppointmentActivit";
    private RecyclerView apa_recyclerview;
    private String jsonResponse;

    private EditText apa_edt_name;
    private TextView apa_txtv_place_appointment;

    String doctor_id = null,patient_id = null,token,appo_day =null;
    Adapter_Doctor_Schedule adapterDoctorSchedule;
    private MaterialToolbar apa_material_toolbar;
    ProgressBar apa_spin_kit_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_appointment);

        apa_spin_kit_progressbar = findViewById(R.id.apa_spin_kit_progressbar);
        FadingCircle fadingCircle = new FadingCircle();
        apa_spin_kit_progressbar.setIndeterminateDrawable(fadingCircle);

        apa_material_toolbar = findViewById(R.id.apa_material_toolbar);
        setSupportActionBar(apa_material_toolbar);
        apa_material_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        apa_recyclerview = findViewById(R.id.apa_recyclerview);
        apa_edt_name = findViewById(R.id.apa_edt_name);
        apa_txtv_place_appointment = findViewById(R.id.apa_txtv_place_appointment);


        SharedPreferences sp = getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        patient_id = sp.getString("patient_id",null);
        doctor_id = getIntent().getStringExtra("doctor_id");
        token = getIntent().getStringExtra("token");

        jsonResponse = getIntent().getStringExtra("doctor_object");
        JSONObject doctor_object = null;

        try {
            doctor_object = new JSONObject(jsonResponse);
            List<DataType_AllDoctorDetails.Schedule_position> schedulePositionList = getSchedulePositions(doctor_object);
            DataType_AllDoctorDetails.Schedule schedule = new DataType_AllDoctorDetails.Schedule(schedulePositionList);

            adapterDoctorSchedule = new Adapter_Doctor_Schedule(PlaceAppointmentActivity.this,schedule);
            apa_recyclerview.setHasFixedSize(true);
            apa_recyclerview.setLayoutManager(new LinearLayoutManager(this));
            apa_recyclerview.setAdapter(adapterDoctorSchedule);

            Log.d(TAG, "onCreate: adapter is set");


            adapterDoctorSchedule.setOnClickListener(new Adapter_Doctor_Schedule.OnClickListener() {
                @Override
                public void onClick( String day) {
                    appo_day = day;
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

        apa_txtv_place_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: Taped");
                String patient_name = apa_edt_name.getText().toString();
                if (patient_name==null||patient_name.length()<1){
                    Toast.makeText(PlaceAppointmentActivity.this, "Please insert a valid name", Toast.LENGTH_SHORT).show();
                }else {
                    apa_spin_kit_progressbar.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onClick: patient_id: "+patient_id);
                    Log.d(TAG, "onClick: doctor_id: "+doctor_id);
                    Log.d(TAG, "onClick: patient_id: "+patient_id);
                    Log.d(TAG, "onClick: patient_name: "+patient_name);

                    DT_PlaceAppointment dt_placeAppointment=  new DT_PlaceAppointment(
                            Integer.parseInt(patient_id),Integer.parseInt(doctor_id),appo_day,patient_name);
/*
                    String placeAppointmentJson = "{\n" +
                            "    \"patient_id\":29,\"doctor_id\":1,\"appo_day\":\"saturday\",\"patient_name\":\"someone\"\n" +
                            "}";

*/
                    Log.d(TAG, "onClick: token: "+token);
                    Log.d(TAG, "onClick: patient_id: "+dt_placeAppointment.getPatient_id());
                    Log.d(TAG, "onClick: doctor_id: "+dt_placeAppointment.getDoctor_id());
                    Log.d(TAG, "onClick: appo_day: "+dt_placeAppointment.getAppo_day());
                    Log.d(TAG, "onClick: patient_name: "+dt_placeAppointment.getPatient_name());

                    Log.d(TAG, "onClick: AdapterClicked");

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://kgsebatech.com/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiRequests apiRequests = retrofit.create(ApiRequests.class);


                    //Placing appointment...

                    Call<JsonObject> call_place_appointment = apiRequests.placeAppointment(token, dt_placeAppointment);

                    call_place_appointment.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            apa_spin_kit_progressbar.setVisibility(View.GONE);
                            if (response.isSuccessful()){
                                Toast.makeText(PlaceAppointmentActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse: "+response.body().toString());
                                startActivity(new Intent(PlaceAppointmentActivity.this, MainActivity.class));
                            }else {
                                Log.d(TAG, "onResponse: "+response.errorBody()+"   code: "+response.code()+"    Message: "+response.message());

                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            apa_spin_kit_progressbar.setVisibility(View.GONE);
                            Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                        }
                    });

                }
            }
        });

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




}
