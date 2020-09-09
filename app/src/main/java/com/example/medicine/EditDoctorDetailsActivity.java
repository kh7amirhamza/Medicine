package com.example.medicine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medicine.DataType.DoctorDetailsPostRespo;
import com.example.medicine.DataType.DoctorSchedule;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EditDoctorDetailsActivity extends AppCompatActivity {
   // private String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiYzAyMjFiOTZjOGU2NmFlZmNlOWY5MzdhNWYxYTUzMjEwNDY0Y2JhMGRiMjM2YzhjNzQ1ODIzN2RiNjYyMDgyYzk4Njc5OWZhNjcwZTA3MDQiLCJpYXQiOjE1OTQxNTAwODEsIm5iZiI6MTU5NDE1MDA4MSwiZXhwIjoxNjI1Njg2MDgxLCJzdWIiOiIxOTAiLCJzY29wZXMiOltdfQ.OxRtP5TvLqGbdg9uyu6Iv1cx1MJKD4scx-3aW4FCZN0tivOrbRi47ynDgsaDfIzdNuvbVKopzBB1hTROKXAzy0XxiD6XWLYjFtPEIU3LrkV_Lm8XtqRYPfPuVMoI-SEPi4eJIW75pJK2hN2H6b7_e3PL_pchQCj1Vi4ImZ5LcNQNDUci45IG4rWcinPyZsx19t9C5kx4nEcsNY88Ac8awQUArEnGY84pqq2KQK6Chz8wQpHUBqjmpldR8aGehqIy0Bcfo7baK-6MTc8eS9MfBwZH1b8_kGflSxVQ1NtfjbZ2rRY6M-OFxGa4LdzUNoFSm_N9tr_EiqzK5vFcErbxbviD23A26AGiCkmzi8ALdlUYAIZnDKy2FyxLbN58fgLxMEaLwwdARJKaaO5_C48MTicC8AybwW4-1Pp_aXCTcgdmMmpk3XcfWBNDLkGtMGta-FnyJs4m4bI12BONCzkjoyVz9F3rYkRxZZUSlBOa0CGVvK9-jtW9yAnZdnYe10-Dpf6xtD2Wb-E_jw3PeTjt4scPzib_HoTA-DZ5Gmpy2UP9AmFHCzKWa7vnmTjRm6gcgkRLzvygiY9RZVcOA09nFF4DiBgeKgXuef-qKPcBOoUiUYq28lDjNKUWbFYzdFLIU80cW09ASJj4woru8mJmvx4t2IQStW0V-Bg_x7pe4tI";
//    SharedPreferences sharedPreferences = getSharedPreferences("USER_TYPE",Context.MODE_PRIVATE);

                                        //editor.putString("token",token);
    String token;
                                                //= "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMmM2MTBhZjgyNmM3MjIzNzRlY2MzNzM3YjNkZjg5NjkzNGVjY2YxY2ZmYTgyOWVjMjIyMDc0MmQ3NzQ1M2RjODhiY2U3NGRlMWZiMWFmYTUiLCJpYXQiOjE1OTQxNTQ1NTEsIm5iZiI6MTU5NDE1NDU1MSwiZXhwIjoxNjI1NjkwNTUxLCJzdWIiOiIyNDgiLCJzY29wZXMiOltdfQ.ogJBJePltFu0VG8-qmzdEfFwIH2MBcWkWVCU1Ce71vs9z119tQiD2M8xhaWVHf5jO5H1gClPnGAtLRVT3MwV9wU3T8JVCYkM9JVvEeCFKPYdUDldPLIQ5dxu0oNYi4kKZljnl-txzZuQxFRb1LIXA_fYxQdIbJhsebe9jbPYhDtXd8cz8nvVUIEBoajADJqz4yyAo6zce4uoC5YvZAwz6q1_yaEwItvnQlB7eJZUYBBLnOXkxIpQgvBDw5c1dGzV8Cxuoxz-1jZbJyc_078pGJQmvMK-uxD6D1tjRaUXdEr33c5LGT3tzYGztdumFF2fZ9-eizsdiPoix_xk_rWIoDjp1greeGuxloGu6MVNfegn0B_67HfTfjmYHdn6KWwOVr1UUoOvYVf3ierPUf7RgSs6WSJaRzcx17RFnipSN1VyRua_48rHKUS6u_OBNS8KIJF33jBRPMelksJ08b1SOEB-aURyKx63dzH4Fhd1UG7BtkUhSdROOVJKa0wA1fFxl1LKcVC2-4LGuREqk6CRhwcmCC5mN9dUUCUeq4PP78zcwrdWD92njN-fvtDoa4zDCzbcfj__FoG8ZcfF7bKo4HWXf3EEoZ5SW4w0OmuwZ5WLd2I4WWQHOqK-mMXpRg_xACAU1WM1gdZ1NfG9AgDHpvQ-3SYpvf2ohfk2GrkKSEY";

    private static final String TAG = "EditDoctorDetailsActivi";

    CircleImageView aedd_imgv_profile_image;

    EditText aedd_edt_gender, aedd_edt_location, aedd_edt_age, aedd_edt_designation, aedd_edt_institute, aedd_edt_department,
            aedd_edt_qualification1, aedd_edt_qualification2, aedd_edt_qualification3, aedd_edt_specialty1, aedd_edt_specialty2,
            aedd_edt_specialty3, aedd_edt_language_spoken1, aedd_edt_language_spoken2, aedd_edt_doctor_rating, aedd_edt_schedule_day1,
            aedd_edt_start_time1,aedd_edt_end_time1, aedd_edt_status1, aedd_edt_schedule_day2, aedd_edt_start_time2, aedd_edt_end_time2,
            aedd_edt_status2, aedd_edt_schedule_day3, aedd_edt_start_time3,aedd_edt_end_time3, aedd_edt_status3;


    String gender = "null", location = "null", age = "3", designation = "null", institute = "null", department = "null", qualification1 = "null",
            qualification2 = "null", qualification3 = "null", specialty1 = "null", specialty2 = "null", specialty3 = "null", language_spoken1 = "null",
            language_spoken2 = "null", doctor_rating = "null", schedule_day1 = "null", time_start_1 = "null", time_end_1 = "null", status1 = "null", schedule_day2 = "null",
            time_start_2 = "null",time_end_2 = "null", status2 = "null", schedule_day3 = "null", time_start_3 = "null", time_end_3 = "null", status3 = "null";

    TextView aedd_txtv_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor_details);

        aedd_imgv_profile_image = findViewById(R.id.aedd_imgv_profile_image);
        aedd_edt_gender = findViewById(R.id.aedd_edt_gender);
        aedd_edt_location = findViewById(R.id.aedd_edt_location);
        aedd_edt_location = findViewById(R.id.aedd_edt_location);
        aedd_edt_age = findViewById(R.id.aedd_edt_age);
        aedd_edt_designation = findViewById(R.id.aedd_edt_designation);
        aedd_edt_institute = findViewById(R.id.aedd_edt_institute);
        aedd_edt_department = findViewById(R.id.aedd_edt_department);
        aedd_edt_qualification1 = findViewById(R.id.aedd_edt_qualification1);
        aedd_edt_qualification2 = findViewById(R.id.aedd_edt_qualification2);
        aedd_edt_qualification3 = findViewById(R.id.aedd_edt_qualification3);
        aedd_edt_specialty1 = findViewById(R.id.aedd_edt_specialty1);
        aedd_edt_specialty2 = findViewById(R.id.aedd_edt_specialty2);
        aedd_edt_specialty3 = findViewById(R.id.aedd_edt_specialty3);
        aedd_edt_language_spoken1 = findViewById(R.id.aedd_edt_language_spoken1);
        aedd_edt_language_spoken2 = findViewById(R.id.aedd_edt_language_spoken2);
        aedd_edt_doctor_rating = findViewById(R.id.aedd_edt_doctor_rating);
        aedd_edt_schedule_day1 = findViewById(R.id.aedd_edt_schedule_day1);
        aedd_edt_start_time1 = findViewById(R.id.aedd_edt_start_time1);
        aedd_edt_end_time1 = findViewById(R.id.aedd_edt_end_time1);
        aedd_edt_status1 = findViewById(R.id.aedd_edt_status1);
        aedd_edt_schedule_day2 = findViewById(R.id.aedd_edt_schedule_day2);
        aedd_edt_start_time2 = findViewById(R.id.aedd_edt_start_time2);
        aedd_edt_end_time2 = findViewById(R.id.aedd_edt_end_time2);
        aedd_edt_status2 = findViewById(R.id.aedd_edt_status2);
        aedd_edt_schedule_day3 = findViewById(R.id.aedd_edt_schedule_day3);
        aedd_edt_start_time3 = findViewById(R.id.aedd_edt_start_time3);
        aedd_edt_end_time3 = findViewById(R.id.aedd_edt_end_time3);
        aedd_edt_status3 = findViewById(R.id.aedd_edt_status3);

        aedd_txtv_save = findViewById(R.id.aedd_txtv_save);

        token = getIntent().getStringExtra("token");

        aedd_txtv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gender = aedd_edt_gender.getText().toString();
                location = aedd_edt_location.getText().toString();
                age = aedd_edt_age.getText().toString();
                designation = aedd_edt_designation.getText().toString();
                institute = aedd_edt_institute.getText().toString();
                department = aedd_edt_department.getText().toString();
                qualification1 = aedd_edt_qualification1.getText().toString();
                qualification2 = aedd_edt_qualification2.getText().toString();
                qualification3 = aedd_edt_qualification3.getText().toString();
                specialty1 = aedd_edt_specialty1.getText().toString();
                specialty2 = aedd_edt_specialty2.getText().toString();
                specialty3 = aedd_edt_specialty3.getText().toString();
                language_spoken1 = aedd_edt_language_spoken1.getText().toString();
                language_spoken2 = aedd_edt_language_spoken2.getText().toString();
                doctor_rating = aedd_edt_doctor_rating.getText().toString();
                schedule_day1 = aedd_edt_schedule_day1.getText().toString();
                time_start_1 = aedd_edt_start_time1.getText().toString();
                time_end_1 = aedd_edt_end_time1.getText().toString();
                status1 = aedd_edt_status1.getText().toString();
                schedule_day2 = aedd_edt_schedule_day2.getText().toString();
                time_start_2 = aedd_edt_start_time2.getText().toString();
                time_end_2 = aedd_edt_end_time2.getText().toString();
                status2 = aedd_edt_status2.getText().toString();
                schedule_day3 = aedd_edt_schedule_day3.getText().toString();
                time_start_3 = aedd_edt_start_time3.getText().toString();
                time_end_3 = aedd_edt_end_time3.getText().toString();
                status3 = aedd_edt_status3.getText().toString();

                List<String> qualifications = new ArrayList<>();
                List<String> specialty = new ArrayList<>();
                List<String> languageSpoken = new ArrayList<>();
                //nothing added...


                DoctorSchedule.SchedulePosition0 schedulePosition1 = new DoctorSchedule.SchedulePosition0(schedule_day1, time_start_1,time_end_1, status1);
                DoctorSchedule.SchedulePosition1 schedulePosition2 = new DoctorSchedule.SchedulePosition1(schedule_day2, time_start_2,time_end_2, status2);
                DoctorSchedule.SchedulePosition2 schedulePosition3 = new DoctorSchedule.SchedulePosition2(schedule_day3, time_start_3,time_end_3, status3);
                DoctorSchedule doctorSchedule = new DoctorSchedule(schedulePosition1, schedulePosition2, schedulePosition3);

                DoctorDetailsPostRespo dataType_doctorDetails = new DoctorDetailsPostRespo(
                        "null", gender, location, Integer.parseInt(age), designation, institute, department, qualifications, specialty, languageSpoken, doctor_rating, doctorSchedule
                );
                Retrofit retrofit = new  Retrofit.Builder()
                        .baseUrl("http://kgsebatech.com/api/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiRequests apiRequests = retrofit.create(ApiRequests.class);

                if (token!=null){
                    Call<JsonObject> call = apiRequests.postDoctorDetails(token,dataType_doctorDetails);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(EditDoctorDetailsActivity.this, "Save Success", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse: save Success: "+ response.body().toString());
                                startActivity(new Intent(EditDoctorDetailsActivity.this, MainActivity.class)
                                        .putExtra("role","2")
                                        .putExtra("token",token)
                                );
                            }
                            else {
                                Toast.makeText(EditDoctorDetailsActivity.this, "Save Failed: "+response.errorBody(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse: Save Failed: "+response.errorBody());
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                            Toast.makeText(EditDoctorDetailsActivity.this, "onFailure: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onFailure: "+t.getLocalizedMessage());

                        }
                    });

                }else {
                    Toast.makeText(EditDoctorDetailsActivity.this, "Token is Null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
