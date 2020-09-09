package com.example.medicine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.medicine.Adapters.AllCategoryOfDoctorAdapter;
import com.example.medicine.DataType.AllCategoryOfDoctor;
import com.github.ybq.android.spinkit.style.FadingCircle;
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

public class MakeAppointmentFragment extends Fragment {

    private static final String TAG = "MakeAppointmentFragment";

    private RecyclerView recyclerView;
    private AllCategoryOfDoctorAdapter adapter;
    SwipeRefreshLayout fma_swipe_refresh_layout;

    private ProgressBar fma_spin_kit_progressbar;

    String token = null,role = null;
    public MakeAppointmentFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_appointment, container, false);

        fma_swipe_refresh_layout = view.findViewById(R.id.fma_swipe_refresh_layout);
        fma_spin_kit_progressbar = view.findViewById(R.id.fma_spin_kit_progressbar);
        FadingCircle fadingCircle = new FadingCircle();
        fma_spin_kit_progressbar.setIndeterminateDrawable(fadingCircle);

        load_Oncreate(view);
        //pull to swipe...
        fma_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fma_swipe_refresh_layout.setRefreshing(true);
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        fma_swipe_refresh_layout.setRefreshing(false);
                        load_Oncreate(view);
                    }
                };
                Handler handler = new Handler();
                handler.postDelayed(runnable,2500);
            }
        });
        fma_swipe_refresh_layout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_orange_dark)
        );


        return view;
    }

    private void load_Oncreate(View view){
        fma_spin_kit_progressbar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView)view.findViewById(R.id.doctorsCategoryRecylerID);
        recyclerView.setHasFixedSize(true);

        token = getActivity().getIntent().getStringExtra("token");
        role = getActivity().getIntent().getStringExtra("role");

        if (token==null){
            SharedPreferences sp = getContext().getSharedPreferences("Authentication", Context.MODE_PRIVATE);
            token = sp.getString("token",null);
            role = sp.getString("role",null);
            Log.d(TAG, "onResponse: tokenCheck: " + sp.getString("token", null));
            Log.d(TAG, "onResponse: roleCheck: " + sp.getString("role", null));
        }


        //Get all Category of Doctor...

        List<AllCategoryOfDoctor> dataTypeAllCategoryOfDoctors = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequests apiRequests = retrofit.create(ApiRequests.class);

        Call<JsonObject> call = apiRequests.getAllCategoryOfDoctor(token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    fma_spin_kit_progressbar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        Log.d(TAG, "onResponse: jsonObject: "+jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("allCategory");
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jo = jsonArray.getJSONObject(i);
                            AllCategoryOfDoctor dataTypeAllCategoryOfDoctor = new AllCategoryOfDoctor(
                                    jo.getInt("id"),jo.getString("cat_name"),jo.getString("cat_in_bangla"),
                                    jo.getString("cat_image"),jo.getString("created_at"),jo.getString("updated_at")
                            );
                            dataTypeAllCategoryOfDoctors.add(dataTypeAllCategoryOfDoctor);
                            if (i==(jsonArray.length()-1)){
                                Log.d(TAG, "onCreateView: dataTypeAllCategoryOfDoctors: length: "+ dataTypeAllCategoryOfDoctors.size());
                                adapter = new AllCategoryOfDoctorAdapter(getContext(),dataTypeAllCategoryOfDoctors,token);

                                recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),3));
                                recyclerView.setAdapter(adapter);
                                // adapter.notifyDataSetChanged();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        fma_spin_kit_progressbar.setVisibility(View.GONE);
                    }
                }else {
                    fma_spin_kit_progressbar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "onResponse: "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                fma_spin_kit_progressbar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "onFailure: "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

}