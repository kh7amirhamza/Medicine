package com.example.medicine;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPatientProfile extends Fragment {

    private static final String TAG = "FragmentPatientProfile";

    TextView fp_name, fp_phone, fp_email, fp_address, fp_gender, fp_location, fp_age, fp_date_of_birth, fp_marital_status,
            fp_previous_diseasis_history, fp_family_diseasis_history;
    String name = "...", phone = "...", email = "...";
    ProgressBar fp_progressbar;
    ImageView fp_imgv_logout;
    SharedPreferences sharedPreferences;
    String role = null, token = null;

    //for doctor

    TextView efpd_txtv_gender, efpd_txtv_location, efpd_txtv_age, efpd_txtv_designation, efpd_txtv_institute, efpd_txtv_department,
            efpd_txtv_qualifications, efpd_txtv_specialty, efpd_txtv_language_spoken, efpd_txtv_schedule1, efpd_txtv_schedule2, efpd_txtv_schedule3;

    int viewCount = 0;
    TextView fp_txtv_edit;
    LinearLayout patientLayout;

    public FragmentPatientProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_profile, container, false);

        patientLayout = view.findViewById(R.id.patientLayout);

        fp_txtv_edit = view.findViewById(R.id.fp_txtv_edit);



        fp_imgv_logout = view.findViewById(R.id.fp_imgv_logout);

        // if (sharedPreferences.getString("role", null) == null && sharedPreferences.getString("token", null) == null) {

       /* name = getActivity().getIntent().getStringExtra("name");
        phone = getActivity().getIntent().getStringExtra("phone");
        email = getActivity().getIntent().getStringExtra("email");*/

        role = getActivity().getIntent().getStringExtra("role");
        token = getActivity().getIntent().getStringExtra("token");


        if (token == null || role == null) {
            SharedPreferences sp = getContext().getSharedPreferences("Authentication", Context.MODE_PRIVATE);
            token = sp.getString("token", null);
            role = sp.getString("role", null);
        }

        fp_name = view.findViewById(R.id.fp_name);
        fp_phone = view.findViewById(R.id.fp_phone);
        fp_email = view.findViewById(R.id.fp_email);
        //fp_address = view.findViewById(R.id.fp_address);
        fp_gender = view.findViewById(R.id.fp_gender);
        fp_location = view.findViewById(R.id.fp_location);
        fp_age = view.findViewById(R.id.fp_age);
        fp_date_of_birth = view.findViewById(R.id.fp_date_of_birth);
        fp_marital_status = view.findViewById(R.id.fp_marital_status);
        fp_previous_diseasis_history = view.findViewById(R.id.fp_previous_diseasis_history);
        fp_family_diseasis_history = view.findViewById(R.id.fp_family_diseasis_history);

        fp_progressbar = view.findViewById(R.id.fp_progressbar);
        //fp_progressbar.setVisibility(View.VISIBLE);

        if (name != null && phone != null && email != null) {
            fp_name.setText(name);
            fp_phone.setText(phone);
            fp_email.setText(email);
        }

        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRequests apiRequests = retrofit.create(ApiRequests.class);

        if (role.equalsIgnoreCase("3")) {


            //Toast.makeText(getContext(), "Patient Profile Clicked", Toast.LENGTH_SHORT).show();
            Call<JsonObject> call1 = apiRequests.getPatientDetails(token);
            call1.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "onResponse: " + "Response value1: " + response.body().toString());
                        try {

                            String patint_email, patint_phone, patint_photo, patint_gender, patint_age, patint_location;

                            JSONObject jo = new JSONObject(response.body().toString());
                            JSONObject jsonObject = jo.getJSONObject("patientdetails");
                            Log.d(TAG, "onResponse: yefudsklamksbhgdskmxl,sdkjbdskml,: " + jsonObject.toString());
                            Log.d(TAG, "onResponse: yefudsklamksbhgdskmxl,sdkjbdskml,: " + jsonObject.getString("id"));
                            SharedPreferences sp = getContext().getSharedPreferences("Authentication", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("patient_id", jsonObject.getString("id"));
                            editor.commit();

                            String p_photo = jsonObject.getString("p_photo");
                            String name = jsonObject.getString("name");
                            String email = jsonObject.getString("email");
                            String phone = jsonObject.getString("phone");
                            String p_address = jsonObject.getString("address");
                            String p_gender = jsonObject.getString("gender");
                            String p_location = jsonObject.getString("location");
                            String p_age = jsonObject.getString("age");
                            String p_date_of_birth = jsonObject.getString("date_of_birth");
                            String p_marital_status = jsonObject.getString("marital_status");
/*
                            String p_previous_diseasis_history_0 = jsonObject.getJSONArray("previous_diseasis_history").getString(0);
                            String p_previous_diseasis_history_1 = jsonObject.getJSONArray("previous_diseasis_history").getString(1);
                            String p_previous_diseasis_history_2 = jsonObject.getJSONArray("previous_diseasis_history").getString(2);

                            String p_family_diseasis_history_0 = jsonObject.getJSONArray("family_diseasis_history").getString(0);
                            String p_family_diseasis_history_1 = jsonObject.getJSONArray("family_diseasis_history").getString(1);
                            String p_family_diseasis_history_2 = jsonObject.getJSONArray("family_diseasis_history").getString(2);
*/

                            Log.d(TAG, "onResponse: patient_id: jsonObject: " + jsonObject.toString());
                            Log.d(TAG, "onResponse: patient_id: jo: " + jo.toString());
                            Log.d(TAG, "onResponse: patient_id: response: " + response.body().toString());
                            fp_name.setText(name);
                            fp_phone.setText(phone);
                            fp_email.setText(email);
                            fp_gender.setText(p_gender);
                            fp_location.setText(p_location);
                            fp_age.setText(p_age);
                            fp_progressbar.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        fp_progressbar.setVisibility(View.GONE);

                        Log.d(TAG, "onResponse: Get Patient Details: Error: " + response.errorBody());
                        Toast.makeText(getContext(), "Failed: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d(TAG, "onFailure: Get Patient: Failed: " + t.getLocalizedMessage());
                    fp_progressbar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });


            fp_txtv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(getContext(), "Clicked: 3", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), EditPatientDetailsActivity.class)
                            .putExtra("token", token)
                    );



                }
            });

        } else if (role.equalsIgnoreCase("2")) {

///////section moved to framgment doctor profile

        }

        fp_imgv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logout
                Call<JsonObject> call = apiRequests.getLogout(token);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                            SharedPreferences sp = getContext().getSharedPreferences("Authentication", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.clear();
                            editor.commit();
                            startActivity(new Intent(getContext(),PreviewActivity.class));
                            getActivity().finish();
                        }else {
                            Toast.makeText(getContext(), "Response Failed: "+response.errorBody(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: Failed: "+response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getContext(), "Logout Failed: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: Logout Failed: "+t.getCause().getMessage());
                    }
                });
            }
        });

        return view;
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

/*
 *
 *
 * Patient Post Details
 *
 *
                PreviousDiseasisHistory previousDiseasisHistory = new PreviousDiseasisHistory(
                        "null","null","null"
                );

                FamilyDiseasisHistory familyDiseasisHistory = new FamilyDiseasisHistory(
                        "null","null","null"
                );

                DataType_PatientDetails dataType_patientDetailsPost = new DataType_PatientDetails("null","null","null",
                        "null","null","null","null",previousDiseasisHistory,familyDiseasisHistory);

                String temp_value = "null";

                String stringBody = "{\n" +
                        "\"p_photo\":\""+temp_value+"\",\n" +
                        "\"address\":\""+temp_value+"\",\n" +
                        "\"gender\":\""+temp_value+"\",\n" +
                        "\"location\":\""+temp_value+"\",\n" +
                        "\"age\":\""+temp_value+"\",\n" +
                        "\"date_of_birth\":\""+temp_value+"\",\n" +
                        "\"marital_status\":\""+temp_value+"\",\n" +
                        "\"previous_diseasis_history\":[\""+temp_value+"\",\""+temp_value+"\",\""+temp_value+"\"],\n" +
                        "\"family_diseasis_history\":[\""+temp_value+"\",\""+temp_value+"\",\""+temp_value+"\"]\n" +
                        "\n" +
                        "}";

                Log.d(TAG, "onCreateView: token: "+token);
                Call<JsonObject> call = jsonPlaceHolderApi.patientDetails(token,stringBody);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: Patient: " + "response Successful" + "   role: " + role + "\nMessage: " + response.message());
                            Toast.makeText(getContext(), "Patient: Data update successfully", Toast.LENGTH_SHORT).show();
                            fp_progressbar.setVisibility(View.GONE);

                            String responseString = response.body().toString();
                            try {
                                JSONObject jsonObject = new JSONObject(responseString);
                                JSONArray jsonArray = jsonObject.getJSONArray("patientdetails");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    if (object.getString("phone").equalsIgnoreCase(phone)) {
                                        String name = object.getString("name");
                                        String email = object.getString("email");
                                        String phone = object.getString("phone");
                                        String address = object.getString("address");
                                        String gender = object.getString("gender");
                                        String location = object.getString("location");
                                        String age = object.getString("age");
                                        String date_of_birth = object.getString("date_of_birth");
                                        String marital_status = object.getString("marital_status");
                                        String previous_diseasis_history = object.getString("previous_diseasis_history");
                                        String family_diseasis_history = object.getString("family_diseasis_history");
                                        String created_at = object.getString("created_at");
                                        String updated_at = object.getString("updated_at");

                                        fp_name.setText(name);
                                        fp_email.setText(email);
                                        fp_phone.setText(phone);
                                        fp_address.setText(address);
                                        fp_gender.setText(gender);
                                        fp_location.setText(location);
                                        fp_age.setText(age);
                                        fp_date_of_birth.setText(date_of_birth);
                                        fp_marital_status.setText(marital_status);
                                        fp_previous_diseasis_history.setText(previous_diseasis_history);
                                        fp_family_diseasis_history.setText(family_diseasis_history);
                                        fp_progressbar.setVisibility(View.GONE);
                                        Log.d(TAG, "onResponse: Patient: " + response.body().toString());

                                        sharedPreferences = getActivity().getSharedPreferences("USER_TYPE", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("token",token);
                                        editor.commit();
                                    }
                                    //if (i == jsonArray.length() - 1) {
                                        fp_progressbar.setVisibility(View.GONE);
                                   // }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                fp_progressbar.setVisibility(View.GONE);
                            }
                        } else {
                            Log.d(TAG, "onResponse: Patient: " + "response Failed" + "   role: " + role + "\nMessage: " + response.message());
                            Toast.makeText(getContext(), "Patient: response Failed" + "  role: " + role + "\nMessage: " + response.message(), Toast.LENGTH_SHORT).show();
                            fp_progressbar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d(TAG, "onFailure: Patient: " + "Error: " + t.getLocalizedMessage());
                        Toast.makeText(getContext(), "Error: Patient: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        fp_progressbar.setVisibility(View.GONE);
                    }
                });

*
*
*
*
*
*
*
*
*
*
*
* Doctor Details Post
*
*

                DataType.Qualifications qualifications = new DataType.Qualifications("cardio", "micro", "psy");
                DataType.Specialty specialty = new DataType.Specialty("heart", "cardio", "psy");
                DataType.LanguageSpoken languageSpoken = new DataType.LanguageSpoken("Bangla", "English");

                DataType.Schedule_position doctorSchedule = new DataType.Schedule_position(
                        new DataType.Schedule_position.SchedulePosition0("saturday", "4-6", "0"),
                        new DataType.Schedule_position.SchedulePosition1("sunday", "4-6", "0"),
                        new DataType.Schedule_position.SchedulePosition2("monday", "4-6", "0")
                );


                DataType.DataType_DoctorDetails dataType_doctorDetails = new DataType.DataType_DoctorDetails(
                        "abc.jpg", "male", "Dhaka", "21", "Proffeser"
                        , "dmc", "Cardio", qualifications, specialty, languageSpoken, "5", doctorSchedule
                );

                Call<JsonObject> call = jsonPlaceHolderApi.postDoctorDetails(token, dataType_doctorDetails);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccessful()) {


                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                JSONObject jsonObject1 = jsonObject.getJSONObject("doctordetails");
                                fp_progressbar.setVisibility(View.GONE);
                                Log.d(TAG, "onResponse: Created at: " + jsonObject1.getString("created_at"));


                                String p_photo = jsonObject1.getString("p_photo");
                                String gender = jsonObject1.getString("gender");//
                                String location = jsonObject1.getString("location");//
                                String age = jsonObject1.getString("age");//
                                String[] qualifications = getFormatedStringArray(jsonObject1.getString("qualifications"));
                                String[] specialty = getFormatedStringArray(jsonObject1.getString("specialty"));
                                String[] language_spoken = getFormatedStringArray(jsonObject1.getString("language_spoken"));

                                String designation = jsonObject1.getString("designation");
                                String institute = jsonObject1.getString("institute");
                                String department = jsonObject1.getString("department");
                                String created_at = jsonObject1.getString("created_at");
                                String updated_at = jsonObject1.getString("updated_at");
                                ////fp_qualifications.setText(qualifications[0] + "," + qualifications[1] + "," + qualifications[2]);
                                //fp_specialty.setText(specialty[0] + "," + specialty[1] + "," + specialty[2]);
                                //fp_language_spoken.setText(language_spoken[0] + "," + language_spoken[1]);

                                //fp_name.setText(name);
                                //fp_email.setText(email);
                                //fp_phone.setText(phone);
                                //fp_address.setText(address);
                                fp_gender.setText(gender);
                                fp_location.setText(location);
                                fp_age.setText(age);
                                //fp_date_of_birth.setText(date_of_birth);
                                //fp_marital_status.setText(marital_status);
                                //fp_previous_diseasis_history.setText(previous_diseasis_history);
                                //fp_family_diseasis_history.setText(family_diseasis_history);
                                //fp_created_at.setText(created_at);
                                //fp_updated_at.setText(updated_at);
                                fp_progressbar.setVisibility(View.GONE);
                                sharedPreferences = getActivity().getSharedPreferences("USER_TYPE", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token",token);
                                editor.commit();
                                Toast.makeText(getContext(), "Data update Successfully", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d(TAG, "onResponse: " + "response Failed" + "role: " + role + "\nMessage: " + response.errorBody().toString()
                            +"Code: "+response.code());
                            Toast.makeText(getContext(), "response Failed" + "role: " + role + "\nMessage: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                            fp_progressbar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                        Toast.makeText(getContext(), "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        fp_progressbar.setVisibility(View.GONE);
                    }
                });



 */