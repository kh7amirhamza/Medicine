package com.example.medicine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicine.Adapters.DoctorShortDetailsAdapter;
import com.example.medicine.DataType.DoctorShortDetailsGet;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
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

public class ViewAllDoctorByCatActivity extends AppCompatActivity {
    private static final String TAG = "ViewAllDoctorByCatActiv";
    String token, catId, catName;
    MaterialButton avadbc_btn;
    List<DataType_AllDoctorDetails.Schedule_position> schedulePositionList;
    RecyclerView avadbc_recyclerview;

    DataType_AllDoctorDetails.Rating rating;
    List<DataType_AllDoctorDetails> dataType_allDoctorDetails;
    DoctorShortDetailsAdapter doctorShortDetailsAdapter;
    String jsonResponse = null;
    private MaterialToolbar avadbc_toolbar;
    private ProgressBar avadbc_spin_kit_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_doctor_by_cat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        avadbc_toolbar = findViewById(R.id.avadbc_toolbar);
        setSupportActionBar(avadbc_toolbar);

        avadbc_spin_kit_progressbar = findViewById(R.id.avadbc_spin_kit_progressbar);
        FadingCircle fadingCircle = new FadingCircle();
        avadbc_spin_kit_progressbar.setIndeterminateDrawable(fadingCircle);
        avadbc_spin_kit_progressbar.setVisibility(View.VISIBLE);

        avadbc_recyclerview = findViewById(R.id.avadbc_recyclerview);
        avadbc_recyclerview.setHasFixedSize(true);
        avadbc_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        token = getIntent().getStringExtra("token");
        catId = getIntent().getStringExtra("catId");
        catName = getIntent().getStringExtra("catName");
        avadbc_toolbar.setTitle(catName);
        avadbc_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiRequests apiRequests = retrofit.create(ApiRequests.class);
        Call<JsonObject> call = apiRequests.getAlldoctorByCategory(token, catId);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                avadbc_spin_kit_progressbar.setVisibility(View.GONE);
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "response failed: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: response failed: " + response.errorBody());
                    return;
                }

                List<DoctorShortDetailsGet> doctorShortDetailsGetList = new ArrayList<>();
                jsonResponse = response.body().toString();
                Log.d(TAG, "onCreate: jsonResponseString: inside: " + jsonResponse);

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("all_doctor");
                    Log.d(TAG, "onResponse: jsonArrayLength: "+jsonArray.length());


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject joSingleDoctorDetails = jsonArray.getJSONObject(i);
                        DoctorShortDetailsGet doctorShortDetailsGet = new DoctorShortDetailsGet(
                                String.valueOf(joSingleDoctorDetails.getInt("id")),
                                "Dr. Someone",
                                catName,
                                joSingleDoctorDetails.getString("department"),
                                joSingleDoctorDetails.getString("designation"),
                                joSingleDoctorDetails.getString("institute"),
                                "Consultation Fee- 700tk"
                        );
                        doctorShortDetailsGetList.add(doctorShortDetailsGet);


                       /* doctorDetails = new DataType_AllDoctorDetails(
                                String.valueOf(joSingleDoctorDetails.getInt("id")),
                                joSingleDoctorDetails.getString("p_photo")
                                , joSingleDoctorDetails.getString("user_id")
                                , joSingleDoctorDetails.getString("doctor_category_id")
                                , joSingleDoctorDetails.getString("gender")
                                , joSingleDoctorDetails.getString("location")
                                , joSingleDoctorDetails.getString("age")
                                , joSingleDoctorDetails.getString("qualifications")
                                , joSingleDoctorDetails.getString("specialty")
                                , joSingleDoctorDetails.getString("language_spoken")
                                , joSingleDoctorDetails.getString("designation")
                                , joSingleDoctorDetails.getString("institute")
                                , joSingleDoctorDetails.getString("department")
                                , joSingleDoctorDetails.getString("created_at")
                                , joSingleDoctorDetails.getString("updated_at")

                        );*/

                        if (i==(jsonArray.length()-1)){
                            Log.d(TAG, "onResponse: l: "+(jsonArray.length()-1));
                            //Log.d(TAG, "onResponse: dataType_allDoctorDetails: length: "+String.valueOf(dataType_allDoctorDetails.size()));
                        doctorShortDetailsAdapter = new DoctorShortDetailsAdapter(getApplicationContext(),doctorShortDetailsGetList,catName,jsonResponse,token);
                        avadbc_recyclerview.setAdapter(doctorShortDetailsAdapter);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                avadbc_spin_kit_progressbar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "onFailure: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
        Log.d(TAG, "onCreate: jsonResponseString: outside: " + jsonResponse);
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
}














































/*
*
*
*   //  adapter_doctor_preview.setData(dataType_allDoctorDetails);

                          /*  avadbc_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            myCalendar.set(Calendar.YEAR, year);
                                            myCalendar.set(Calendar.MONTH, month);
                                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                            SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yy");
                                            Date myDate = myCalendar.getTime();

                                            avadbc_btn.setText(myFormat.format(myCalendar.getTime()));

                                            DateFormat finalFormat = new SimpleDateFormat("EEEE");
                                            String dayOfWeek=finalFormat.format(myDate);
                                            Toast.makeText(ViewAllDoctorByCatActivity.this, dayOfWeek, Toast.LENGTH_SHORT).show();

                                            //filter here...
                                            adapter_doctor_preview.setFilter(dataType_allDoctorDetails,dayOfWeek);
                                        }
                                    };

                                    new DatePickerDialog(ViewAllDoctorByCatActivity.this, dateSetListener, myCalendar
                                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                }
                            });*/

