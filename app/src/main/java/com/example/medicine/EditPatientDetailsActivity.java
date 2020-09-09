package com.example.medicine;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicine.MultiselectCustomView.Adapter_Showing_Selected_Items;
import com.example.medicine.MultiselectCustomView.MultiSelect;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EditPatientDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    String token;
    private static final String TAG = "EditPatientDetailsActiv";

    EditText aepd_edt_address, aepd_edt_location, aepd_edt_age;

    private String photo = "file1.png", address = "null", gender = "null", location = "null", age = "null", date_of_birth = "null", marital_status = "null";
    List<String> previous_diseasis_historys = new ArrayList<>();
    List<String> family_diseasis_historys = new ArrayList<>();
    TextView aepd_txtv_save;

    RadioButton aepd_radioBtn_male, aepd_radioBtn_female, aepd_radioBtn_married, aepd_radioBtn_unmarried;

    TextView aepd_txtv_date_of_birth,aepd_txtv_add_family_dises, aepd_txtv_add_previous_dises;
    RecyclerView aepd_recy_family_diseasis_history, aepd_recy_previous_diseasis_history;
    List<String> selectedItem_family, selectedItem_previous;

    ProgressBar aepd_spin_kit_progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_details);

        aepd_spin_kit_progressbar = findViewById(R.id.aepd_spin_kit_progressbar);
        FadingCircle fadingCircle = new FadingCircle();
        aepd_spin_kit_progressbar.setIndeterminateDrawable(fadingCircle);

        aepd_edt_address = findViewById(R.id.aepd_edt_address);
        aepd_edt_location = findViewById(R.id.aepd_edt_location);
        aepd_edt_age = findViewById(R.id.aepd_edt_age);
        aepd_txtv_date_of_birth = findViewById(R.id.aepd_txtv_date_of_birth);

        aepd_txtv_add_family_dises = findViewById(R.id.aepd_txtv_add_family_dises);
        aepd_recy_family_diseasis_history = findViewById(R.id.aepd_recy_family_diseasis_history);
        aepd_recy_family_diseasis_history.setHasFixedSize(true);
        aepd_recy_family_diseasis_history.setLayoutManager(new LinearLayoutManager(EditPatientDetailsActivity.this, RecyclerView.HORIZONTAL, false));

        aepd_txtv_add_previous_dises = findViewById(R.id.aepd_txtv_add_previous_dises);
        aepd_recy_previous_diseasis_history = findViewById(R.id.aepd_recy_previous_diseasis_history);
        aepd_recy_previous_diseasis_history.setHasFixedSize(true);
        aepd_recy_previous_diseasis_history.setLayoutManager(new LinearLayoutManager(EditPatientDetailsActivity.this, RecyclerView.HORIZONTAL, false));

        aepd_radioBtn_male = findViewById(R.id.aepd_radioBtn_male);
        aepd_radioBtn_female = findViewById(R.id.aepd_radioBtn_female);
        aepd_radioBtn_married = findViewById(R.id.aepd_radioBtn_married);
        aepd_radioBtn_unmarried = findViewById(R.id.aepd_radioBtn_unmarried);

        token = getIntent().getStringExtra("token");

        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRequests apiRequests = retrofit.create(ApiRequests.class);


        aepd_txtv_add_family_dises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] items = new String[]{"item1", "item2", "item3", "item4", "item5"};

                MultiSelect multiSelect = new MultiSelect(EditPatientDetailsActivity.this, items);
                multiSelect.show();
                multiSelect.setOnClickListener(new MultiSelect.OnClickListener() {
                    @Override
                    public void onClick(List<String> selected_Item_List) {
                        multiSelect.cancel();
                        selectedItem_family = null;
                        selectedItem_family = selected_Item_List;
                        Adapter_Showing_Selected_Items adapter_showing_selected_items = new Adapter_Showing_Selected_Items(
                                EditPatientDetailsActivity.this, selected_Item_List);

                        aepd_recy_family_diseasis_history.setAdapter(adapter_showing_selected_items);
                        //adapter_showing_selected_items.setData();

                    }
                });
            }
        });

        aepd_txtv_add_previous_dises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] items_previous = new String[]{"item1", "item2", "item3", "item4", "item5"};

                MultiSelect multiSelect = new MultiSelect(EditPatientDetailsActivity.this, items_previous);
                multiSelect.show();
                multiSelect.setOnClickListener(new MultiSelect.OnClickListener() {
                    @Override
                    public void onClick(List<String> selected_Item_List) {
                        multiSelect.cancel();
                        selectedItem_previous = null;
                        selectedItem_previous = selected_Item_List;
                        Adapter_Showing_Selected_Items adapter_showing_selected_items = new Adapter_Showing_Selected_Items(
                                EditPatientDetailsActivity.this, selected_Item_List);

                        aepd_recy_previous_diseasis_history.setAdapter(adapter_showing_selected_items);
                        //adapter_showing_selected_items.setData();

                    }
                });
            }
        });


        //Getting existing patient details and set to view...
        aepd_spin_kit_progressbar.setVisibility(View.VISIBLE);
        Call<JsonObject> call1 = apiRequests.getPatientDetails(token);
        call1.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                aepd_spin_kit_progressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + "Response value1: " + response.body().toString());
                    try {
                        JSONObject jo = new JSONObject(response.body().toString());
                        JSONObject jsonObject = jo.getJSONObject("patientdetails");
                        SharedPreferences sp = getSharedPreferences("Authentication", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("patient_id", jsonObject.getString("id"));
                        editor.commit();

                        String p_photo = jsonObject.getString("p_photo");
                        String p_address = jsonObject.getString("address");
                        String p_gender = jsonObject.getString("gender");
                        String p_location = jsonObject.getString("location");
                        String p_age = jsonObject.getString("age");
                        String p_date_of_birth = jsonObject.getString("date_of_birth");
                        String p_marital_status = jsonObject.getString("marital_status");


                    /*    //set Family Diseasis History...
                        JSONArray family_diseasis_history = jsonObject.getJSONArray("family_diseasis_history");
                        List<String> new_selectedItems_family = new ArrayList<>();
                        for (int i = 0; i < family_diseasis_history.length(); i++) {
                            new_selectedItems_family.add(family_diseasis_history.getString(i));
                        }
                        selectedItem_family = null;
                        selectedItem_family = new_selectedItems_family;
                        Adapter_Showing_Selected_Items adapter_showing_selected_items_family = new Adapter_Showing_Selected_Items(
                                EditPatientDetailsActivity.this, new_selectedItems_family);
                        aepd_recy_family_diseasis_history.setAdapter(adapter_showing_selected_items_family);


                        //set Previous Diseasis History...
                        JSONArray previous_diseasis_history = jsonObject.getJSONArray("previous_diseasis_history");
                        List<String> new_selectedItems_previous = new ArrayList<>();
                        for (int i = 0; i < previous_diseasis_history.length(); i++) {
                            new_selectedItems_previous.add(previous_diseasis_history.getString(i));
                        }
                        selectedItem_previous = null;
                        selectedItem_previous = new_selectedItems_previous;
                        Adapter_Showing_Selected_Items adapter_showing_selected_items_previous = new Adapter_Showing_Selected_Items(
                                EditPatientDetailsActivity.this, new_selectedItems_previous);
                        aepd_recy_previous_diseasis_history.setAdapter(adapter_showing_selected_items_previous);

*/
                        aepd_edt_address.setText(p_address);
                        if (p_gender.equalsIgnoreCase("Male")) {
                            aepd_radioBtn_male.setChecked(true);
                        } else {
                            aepd_radioBtn_female.setChecked(true);
                        }

                        if (p_marital_status.equalsIgnoreCase("1")) {
                            aepd_radioBtn_married.setChecked(true);
                        } else {
                            aepd_radioBtn_unmarried.setChecked(true);
                        }

                        aepd_edt_location.setText(p_location);
                        //aepd_edt_age.setText(p_age);
                        aepd_txtv_date_of_birth.setText(p_date_of_birth);
                        date_of_birth = p_date_of_birth;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                aepd_spin_kit_progressbar.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Existing patient details load failed: "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        aepd_txtv_save = findViewById(R.id.aepd_txtv_save);
        aepd_txtv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aepd_spin_kit_progressbar.setVisibility(View.VISIBLE);
               /* if (token!=null){
                    Toast.makeText(EditPatientDetailsActivity.this, "Token Available", Toast.LENGTH_SHORT).show();
                }*/
                address = aepd_edt_address.getText().toString();

                if (aepd_radioBtn_male.isChecked()) {
                    gender = "Male";
                } else if (aepd_radioBtn_female.isChecked()) {
                    gender = "Female";
                } else {
                    gender = "Male";
                }

                location = aepd_edt_location.getText().toString();
                //age = aepd_edt_age.getText().toString();
                //date_of_birth = "2020-5-4";
                marital_status = aepd_radioBtn_married.isChecked() ? "1" : "0";

               /*
                for (int i = 0; i < selectedItem_family.size(); i++) {
                    family_diseasis_historys.add(selectedItem_family.get(i));
                }

                for (int i = 0; i < selectedItem_previous.size(); i++) {
                    previous_diseasis_historys.add(selectedItem_previous.get(i));
                }*/

                DataType_PatientDetails dataType_patientDetails = new DataType_PatientDetails(
                        photo, address, location,date_of_birth, marital_status);


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://kgsebatech.com/api/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiRequests apiRequests = retrofit.create(ApiRequests.class);

                Log.d(TAG, "onClick: token: " + token);
                Log.d(TAG, "onClick: photo: " + dataType_patientDetails.getP_photo());
                Log.d(TAG, "onClick: address: " + dataType_patientDetails.getAddress());
              //  Log.d(TAG, "onClick: gender: " + dataType_patientDetails.getGender());
                Log.d(TAG, "onClick: location: " + dataType_patientDetails.getLocation());
               // Log.d(TAG, "onClick: age: " + dataType_patientDetails.getAge());

                Call<JsonObject> call = apiRequests.patientDetails(token, dataType_patientDetails);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        aepd_spin_kit_progressbar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: successful: " + response.body());
                            Toast.makeText(EditPatientDetailsActivity.this, "Save Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditPatientDetailsActivity.this, MainActivity.class)
                                    .putExtra("role", "3")
                                    .putExtra("token", token)
                            );
                        } else {
                            Log.d(TAG, "onResponse: Failed: " + response.errorBody());
                            Toast.makeText(EditPatientDetailsActivity.this, "Save Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        aepd_spin_kit_progressbar.setVisibility(View.GONE);
                        Toast.makeText(EditPatientDetailsActivity.this, "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: Error: " + t.getLocalizedMessage());
                    }
                });
            }
        });


        aepd_txtv_date_of_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment date_picker = new DatePickerFragment();
                date_picker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        date_of_birth = year + "/" + month + "/" + dayOfMonth;
        aepd_txtv_date_of_birth.setText(date_of_birth);
    }
}