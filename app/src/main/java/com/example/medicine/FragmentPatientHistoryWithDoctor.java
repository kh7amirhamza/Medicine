package com.example.medicine;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.gson.JsonArray;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPatientHistoryWithDoctor extends Fragment {
    private static final String TAG = "FragmentPatientHistoryW";

    private RecyclerView fph_recyclerview;
    private SwipeRefreshLayout fph_swiperefreshlayout;

    String token = null, role = null;
    private ProgressBar fph_spin_kit_progressbar;

    public FragmentPatientHistoryWithDoctor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_history_with_doctor, container, false);

        fph_swiperefreshlayout = view.findViewById(R.id.fph_swiperefreshlayout);

        fph_spin_kit_progressbar = view.findViewById(R.id.fph_spin_kit_progressbar);
        FadingCircle fadingCircle = new FadingCircle();
        fph_spin_kit_progressbar.setIndeterminateDrawable(fadingCircle);

        load_Oncreate(view);

        //pull to swipe...
        fph_swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fph_swiperefreshlayout.setRefreshing(true);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        fph_swiperefreshlayout.setRefreshing(false);
                        load_Oncreate(view);
                    }
                };
                Handler handler = new Handler();
                handler.postDelayed(runnable,2500);
            }
        });
        fph_swiperefreshlayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_orange_dark)
        );



        return view;
    }

    private void load_Oncreate(View view){

        fph_spin_kit_progressbar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequests apiRequests = retrofit.create(ApiRequests.class);
        //token = getActivity().getIntent().getStringExtra("token");

        SharedPreferences sp = getContext().getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        //id = sp.getString("id",null);
        token = sp.getString("token",null);
        role = sp.getString("role",null);
        // }
        Call<JsonArray> call = apiRequests.getPatientHistory(token);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()){
                    fph_spin_kit_progressbar.setVisibility(View.GONE);
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

                                    fph_recyclerview = view.findViewById(R.id.fph_recyclerview);
                                    fph_recyclerview.setHasFixedSize(true);
                                    fph_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                                    fph_recyclerview.setAdapter(adapterPatientHistory);
                                }
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        fph_spin_kit_progressbar.setVisibility(View.GONE);
                    }


                }else {
                    fph_spin_kit_progressbar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Response failed: "+response.errorBody(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+"Response failed: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                fph_spin_kit_progressbar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Request failed: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+"LocalMess: "+t.getLocalizedMessage()+ "Mess: "+t.getMessage());
            }
        });
    }

}
