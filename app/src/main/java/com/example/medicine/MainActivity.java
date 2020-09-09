package com.example.medicine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "MainActivity";
    private static final int MAKE_APPOINTMENT_FRAGMENT =0;
    private static final int HISTORY_FRAGMENT =1;
    private static final int MY_APPOINTMENTS =2;
    private static final int SETTINGS_FRAGMENT=3;
    private static final int HELP_FRAGMENT=4;

    private FrameLayout frameLayout;
    private NavigationView navigationView;
    public static DrawerLayout drawer;

    private Window window;
    private Toolbar toolbar;

    private int currentFragment = -1;
    String role = null, token = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        frameLayout=(FrameLayout)findViewById(R.id.mainFrameLayoutID);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        gotoFragment("Make Appointment",new MakeAppointmentFragment(), MAKE_APPOINTMENT_FRAGMENT);

        // Get and set Profile data...
        role = getIntent().getStringExtra("role");
        token = getIntent().getStringExtra("token");

        if (token == null || role == null) {
            SharedPreferences sp = getSharedPreferences("Authentication", Context.MODE_PRIVATE);
            token = sp.getString("token",null);
            role = sp.getString("role",null);
        }

        //Create Retrofit instance...
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRequests apiRequests = retrofit.create(ApiRequests.class);

        Log.d(TAG, "onCreate: token: "+token);
        Call<JsonObject> call1 = apiRequests.getPatientDetails(token);
        call1.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jo = new JSONObject(response.body().toString());
                        JSONObject jsonObject = jo.getJSONObject("patientdetails");
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

                        ImageView drawerPprofileID = drawer.findViewById(R.id.drawerPprofileID);
                        TextView drawerNameID = drawer.findViewById(R.id.drawerNameID);
                        TextView drawerMobileID = drawer.findViewById(R.id.drawerMobileID);

                        drawerNameID.setText(name);
                        drawerMobileID.setText(phone);

                        //Edit Profile
                        TextView nav_header_txtv_edit = (TextView) drawer.findViewById(R.id.nav_header_txtv_edit);
                        nav_header_txtv_edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(MainActivity.this,EditPatientDetailsActivity.class)
                                        .putExtra("token",token));
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failed: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Error: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (currentFragment == MAKE_APPOINTMENT_FRAGMENT){
            currentFragment = -1;
            super.onBackPressed();
            System.exit(0);
        }else {
            gotoFragment("Make Appointment", new MakeAppointmentFragment(), MAKE_APPOINTMENT_FRAGMENT);
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        int id = item.getItemId();

        if (id == R.id.nav_home){
            gotoFragment("Make Appointment",new MakeAppointmentFragment(), MAKE_APPOINTMENT_FRAGMENT);
        }else if (id == R.id.nav_trip_hostory){
            gotoFragment("History",new FragmentPatientHistoryWithDoctor(), HISTORY_FRAGMENT);
        }else if (id == R.id.nav_discount){
            gotoFragment("My Appointments",new MyAppointmentsFragment(), MY_APPOINTMENTS);
        }else if (id == R.id.nav_setting){
            gotoFragment(getString(R.string.settings_title),new SettingsFragment(),SETTINGS_FRAGMENT);
        }else if (id == R.id.nav_help){
            gotoFragment(getString(R.string.help_title),new HelpFragment(),HELP_FRAGMENT);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void gotoFragment(String title, Fragment fragment, int fragment_no){
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment,fragment_no);
    }

    private void setFragment(Fragment fragment, int fragment_No){
        if (fragment_No != currentFragment){
            currentFragment = fragment_No;

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayout.getId(),fragment);
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.commit();
        }
    }
}