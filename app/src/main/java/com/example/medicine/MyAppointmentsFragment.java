package com.example.medicine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.gson.JsonArray;
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


public class MyAppointmentsFragment extends Fragment {

    private TextView fas_name,fas_appo_day,fas_appo_date,fas_doctor_gender,fas_doctor_location,fas_doctor_institute,
            fas_doctor_department,fas_appo_start_time, fas_appo_end_time;

    private Button fas_finish_appointment;

    private static final String TAG = "FragmentAppoinmentStatu";
    private String token = null,role=null;
    int id = -1,doctor_id = -1;

    private TextView fma_txtv_upcomming,fma_txtv_past;
    private RecyclerView fma_past_layout_recyclerview;
    private ScrollView fma_upcomming_layout_scrollview;
    private SwipeRefreshLayout f_my_appointments_s_swiperefreshlayout;
    private ProgressBar f_my_appo_spin_kit_progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_appointments, container, false);
        f_my_appo_spin_kit_progressbar = view.findViewById(R.id.f_my_appo_spin_kit_progressbar);
        FadingCircle fadingCircle = new FadingCircle();
        f_my_appo_spin_kit_progressbar.setIndeterminateDrawable(fadingCircle);

        load_Oncreate(view);

        f_my_appointments_s_swiperefreshlayout = view.findViewById(R.id.f_my_appointments_s_swiperefreshlayout);
        //pull to swipe...
        f_my_appointments_s_swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                f_my_appointments_s_swiperefreshlayout.setRefreshing(true);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        f_my_appointments_s_swiperefreshlayout.setRefreshing(false);
                        load_Oncreate(view);
                    }
                };
                Handler handler = new Handler();
                handler.postDelayed(runnable,2500);
            }
        });
        f_my_appointments_s_swiperefreshlayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_orange_dark)
        );

        return view;
    }

    private void load_Oncreate(View view){
        fas_name = view.findViewById(R.id.fas_name);
        fas_appo_day = view.findViewById(R.id.fas_appo_day);
        fas_appo_date = view.findViewById(R.id.fas_appo_date);
        fas_doctor_gender = view.findViewById(R.id.fas_doctor_gender);
        fas_doctor_location = view.findViewById(R.id.fas_doctor_location);
        fas_doctor_institute = view.findViewById(R.id.fas_doctor_institute);
        fas_doctor_department = view.findViewById(R.id.fas_doctor_department);
        fas_appo_start_time = view.findViewById(R.id.fas_appo_start_time);
        fas_appo_end_time = view.findViewById(R.id.fas_end_time);

        fma_past_layout_recyclerview = view.findViewById(R.id.fma_past_layout_recyclerview);
        fma_upcomming_layout_scrollview = view.findViewById(R.id.fma_upcomming_layout_scrollview);

        fas_finish_appointment = view.findViewById(R.id.fas_finish_appointment);

        token = getActivity().getIntent().getStringExtra("token");

        if (token==null){
            SharedPreferences sp = getContext().getSharedPreferences("Authentication", Context.MODE_PRIVATE);
            //id = sp.getString("id",null);
            token = sp.getString("token",null);
            role = sp.getString("role",null);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequests apiRequests = retrofit.create(ApiRequests.class);

        fma_txtv_upcomming = view.findViewById(R.id.fma_txtv_upcomming);
        fma_txtv_past = view.findViewById(R.id.fma_txtv_past);


        upcommingAppointment(apiRequests);

        //Showing Upcomming Appointment...
        fma_txtv_upcomming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upcommingAppointment(apiRequests);
            }
        });

        //Showing Past Appointment...
        fma_txtv_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pastAppointment(apiRequests);
            }
        });

    }


    public void pastAppointment(ApiRequests apiRequests){
        f_my_appo_spin_kit_progressbar.setVisibility(View.VISIBLE);
        fma_upcomming_layout_scrollview.setVisibility(View.GONE);
        fma_past_layout_recyclerview.setVisibility(View.VISIBLE);

        fma_txtv_past.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        fma_txtv_past.setTextColor(getResources().getColor(android.R.color.white));

        fma_txtv_upcomming.setBackgroundColor(getResources().getColor(android.R.color.white));
        fma_txtv_upcomming.setTextColor(getResources().getColor(android.R.color.black));

        fma_past_layout_recyclerview.setHasFixedSize(true);
        fma_past_layout_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        Call<JsonArray> call = apiRequests.getPatientHistory(token);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()){
                    f_my_appo_spin_kit_progressbar.setVisibility(View.GONE);
                    //Toast.makeText(getContext(), "Response susscesful: "+response.body().toString(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+"Response susscesful: "+response.body().toString());

                    try {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                            JSONArray jsonArray = new JSONArray(response.body().toString());
                            List<DataType_PatientHistory> histories = new ArrayList<>();
                            Log.d(TAG, "onResponse: jsonArray Length: "+jsonArray.length());
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String time = jsonObject.getString("created_at");
                                String doctor_name = "Dr. Someone";
                                JSONObject doctorObject = jsonObject.getJSONObject("doctor");
                                String designation = doctorObject.getString("designation");
                                String institute = doctorObject.getString("institute");

                                DataType_PatientHistory patientHistory = new DataType_PatientHistory(
                                        "title","description",doctor_name,
                                        designation,institute,time
                                );
                                histories.add(patientHistory);

                                if (i==(jsonArray.length()-1)){
                                    Log.d(TAG, "onResponse: historys Length: "+histories.size());
                                    Adapter_PatientHistory adapterPatientHistory = new Adapter_PatientHistory(getContext(),histories);
                                    fma_past_layout_recyclerview.setAdapter(adapterPatientHistory);
                                }
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        f_my_appo_spin_kit_progressbar.setVisibility(View.GONE);
                    }


                }else {
                    f_my_appo_spin_kit_progressbar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Response failed: "+response.errorBody(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+"Response failed: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                f_my_appo_spin_kit_progressbar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Request failed: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+"LocalMess: "+t.getLocalizedMessage()+ "Mess: "+t.getMessage());
            }
        });
    }

    public void upcommingAppointment(ApiRequests apiRequests){
        f_my_appo_spin_kit_progressbar.setVisibility(View.VISIBLE);
        fma_past_layout_recyclerview.setVisibility(View.GONE);
        fma_upcomming_layout_scrollview.setVisibility(View.VISIBLE);

        fma_txtv_upcomming.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        fma_txtv_upcomming.setTextColor(getResources().getColor(android.R.color.white));

        fma_txtv_past.setBackgroundColor(getResources().getColor(android.R.color.white));
        fma_txtv_past.setTextColor(getResources().getColor(android.R.color.black));

        Call<JsonObject> call = apiRequests.getAppointment(token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    f_my_appo_spin_kit_progressbar.setVisibility(View.GONE);
                    Log.d(TAG, "onResponse: response successful: "+response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (jsonObject!=null){
                            JSONObject jo_appointmentstatus = jsonObject.getJSONObject("appointmentstatus");
                            id = jo_appointmentstatus.getInt("user_id");
                            doctor_id = jo_appointmentstatus.getInt("doctor_id");
                            String patient_name = jo_appointmentstatus.getString("patient_name");
                            String start_time = jo_appointmentstatus.getString("appo_start_time");
                            String end_time = jo_appointmentstatus.getString("appo_end_time");
                            String appo_day = jo_appointmentstatus.getString("appo_day");
                            String appo_date = jo_appointmentstatus.getString("appo_date");

                            JSONObject jo_doctor = jo_appointmentstatus.getJSONObject("doctor");

                            String doctor_gender = jo_doctor.getString("gender");
                            String doctor_location = jo_doctor.getString("location");
                            String institute = jo_doctor.getString("institute");
                            String department = jo_doctor.getString("department");

                            fas_name.setText(patient_name);
                            fas_appo_date.setText(appo_date);
                            fas_appo_day.setText(appo_day);
                            fas_doctor_department.setText(department);
                            fas_doctor_gender.setText(doctor_gender);
                            fas_doctor_institute.setText(institute);
                            fas_doctor_location.setText(doctor_location);
                            fas_appo_start_time.setText(start_time);
                            fas_appo_end_time.setText(end_time);





                            fas_finish_appointment.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    f_my_appo_spin_kit_progressbar.setVisibility(View.VISIBLE);
                                    Toast.makeText(getContext(), "fas_finish_appointment clicked", Toast.LENGTH_SHORT).show();


                                    if (id==-1||doctor_id==-1){
                                        Toast.makeText(getContext(), "ID not Found", Toast.LENGTH_SHORT).show();
                                    }
                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl("http://kgsebatech.com/api/")
                                            .addConverterFactory(GsonConverterFactory.create())
                                            .build();

                                    List<String> imageList = new ArrayList<>();
                                    imageList.add("file1.png");
                                    imageList.add("file2.png");
                                    imageList.add("file3.png");
                                    imageList.add("file4.png");
                                    imageList.add("file5.png");

                                    DataType_SavePatientHistory dataType_savePatientHistory =
                                            new DataType_SavePatientHistory(id,doctor_id,0,imageList);

                                    Log.d(TAG, "onClick: id: "+dataType_savePatientHistory.patient_user_id);
                                    Log.d(TAG, "onClick: doctor_id: "+dataType_savePatientHistory.getDoctor_id());
                                    Log.d(TAG, "onClick: status: "+dataType_savePatientHistory.getStatus());
                                    Log.d(TAG, "onClick: token: "+token);
                                    //Log.d(TAG, "onClick: id: "+id);

                                    ApiRequests apiRequests = retrofit.create(ApiRequests.class);

                                    Call<JsonObject> postSavePatientHistory = apiRequests.postSavePatientHistory(token,dataType_savePatientHistory);
                                    postSavePatientHistory.enqueue(new Callback<JsonObject>() {
                                        @Override
                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                            if (response.isSuccessful()){
                                                f_my_appo_spin_kit_progressbar.setVisibility(View.GONE);
                                                Log.d(TAG, "onResponse: response successful: "+response.body().toString());
                                                Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                                                getActivity().finish();
                                                getActivity().overridePendingTransition(0, 0);
                                                startActivity(getActivity().getIntent().putExtra("checkedFragment","History"));
                                                getActivity().overridePendingTransition(0, 0);


                                            }else {
                                                f_my_appo_spin_kit_progressbar.setVisibility(View.GONE);
                                                Log.d(TAG, "onResponse: response Failed: "+response.errorBody().toString()+"    Message: "+response.message());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<JsonObject> call, Throwable t) {
                                            f_my_appo_spin_kit_progressbar.setVisibility(View.GONE);
                                            Log.d(TAG, "onResponse: Failed: "+t.getLocalizedMessage()+"    Message: "+t.getMessage());
                                        }
                                    });
                                }
                            });


                        }else {
                            f_my_appo_spin_kit_progressbar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Data is empty", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        f_my_appo_spin_kit_progressbar.setVisibility(View.GONE);
                    }
                }else {
                    f_my_appo_spin_kit_progressbar.setVisibility(View.GONE);
                    Log.d(TAG, "onResponse: response failed: "+response.errorBody().toString()+"    Message: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                f_my_appo_spin_kit_progressbar.setVisibility(View.GONE);
                Log.d(TAG, "onResponse: Failed: "+t.getLocalizedMessage()+"    Message: "+t.getMessage());
            }
        });
    }

}
