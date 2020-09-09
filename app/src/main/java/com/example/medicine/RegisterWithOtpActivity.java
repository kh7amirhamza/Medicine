package com.example.medicine;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterWithOtpActivity extends AppCompatActivity {
    private static final String TAG = "RegisterWithOtpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_with_otp);


        //Register with OTP...
        String phone_number = "01712478533";
        DataType_OTP_Send_Get dataType_otp_send = new DataType_OTP_Send_Get(phone_number);

        createRegisterWithOTP(dataType_otp_send);


        //Register with Email & Password...
        DataType_Register dataType_register = new DataType_Register("Atik", "atik1@gmail.com", "atik1Pass",
                "atikPass", "01745789001", "3");
        //createRegister(dataType_register);


        // View Patient data...
        String Tokn = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMTBlNjFhNjJhOTczOWZmMmJkMjRmMWYyMTliMzY3MTUzMGIyMzkzMjNkMTEyOGNiN2I1NTFlN2I1YTg2NzNjNWFmMWZiMTIwNTkyOThkYmIiLCJpYXQiOjE1OTMxNzEwMjgsIm5iZiI6MTU5MzE3MTAyOCwiZXhwIjoxNjI0NzA3MDI4LCJzdWIiOiI2OCIsInNjb3BlcyI6W119.CJPFzA1UxeTQe1UprQoHM1pPa-zihVeFp1A0ObjdsHMtw073Akx2R0igq-2DFtAwYjoLvZ_xn56Ua7FyXfCr5_VB7tnug5WcKgbYxhwutJviff6GuBEc3HoM_bclxbvG_LQuZLPbYkk-rzELnuT3HuyKxyAfCW9IteJPltGkVhLVkkVCtTdyzmYCAxqcm4_Vmpf-vU94Hx2Z8CvQE2nIBTop4TXcZglnd9heFGPNCgle1A0tBm77Kmrl8oiEZFHOXGwPVUTOLmquJz6BU9jkJy2SCvbsREFPN9MvHw30nxarRAHeTDYMPBTHMG8bjQHuYdKpt3NJXSfrK8tLNUPpChO8jxStUalwZ12NPMEv_hqVC2pz7jetlSYkdc2m0v6tjuMHqmmxOx8swk_TybT98uD4tEz9lUrZljbmZ8ap9vmTujRf3KfMNQlZK-W5KeCQPoKCTsrcrXywycGjdk0_4jj823P5jFQOagE9Ht0ME_k6nnX297YflsCodqnnoLrYDYo46c6YccmXhfA5OVkleR88kJZ-OosE5ggNhi56OsqIBM6knwv8Hpv4ghNVXrb-r1Xxt-1hKa-aRaXUt_1BxyJlMUnRczH_8LXEkX4u62n6Jh_wcZC3sCkVo75HtR5bybSq7sWlojZPPMIJfIvk658dMw2e8bkCxmLpsjYmRv8";
        //createViewPatient(Tokn);

    }

    public void createViewPatient(String token) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(ApiRequests.class).viewPatient(token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "CheckResponse: onResponse: response is successful!");
                    Log.d(TAG, "CheckResponse: onResponse: " + response.body().toString());
                } else {
                    Log.d(TAG, "CheckResponse: onResponse: response is failed\n" + "responseCode: " + response.code() + "\n ResponseMessage: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "CheckResponse: onFailure: " + t.getLocalizedMessage());
            }
        });

    }


    public void createRegister(DataType_Register dataType_register) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(ApiRequests.class).createRegister(dataType_register).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "CheckResponse: onResponse: response is successful!");
                    Log.d(TAG, "onResponse: ResponseCode: " + response.code());
                    Log.d(TAG, "onResponse: postResponse: " + response.body().toString());
                } else {
                    Log.d(TAG, "CheckResponse: onResponse: response is failed\n" + "responseCode: " + response.code() + "\n ResponseMessage: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "CheckResponse: onFailure: " + t.getLocalizedMessage());
            }
        });

    }


    private void createRegisterWithOTP(DataType_OTP_Send_Get dataType_otp_send) {

        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiRequests apiRequests = retrofit.create(ApiRequests.class);

        Call<JSONObject> call =null;
        //= apiRequests.createRegisterWithOTP(dataType_otp_send);

        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code: " + response.code());
                    return;
                }


                Log.d(TAG, "onResponse: ResponseCode: " + response.code());
                Log.d(TAG, "onResponse: postResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d(TAG, "CheckResponse: onFailure: " + t.getLocalizedMessage());
            }
        });
    }

   /* private void createPost2() {


        ApiRequests jsonPlaceHolderApi = retrofit.create(ApiRequests.class);

        DataType_Register dataType_register = new DataType_Register("Atik","atik@gmail.com","atikPass",
                "atikPass","01747896000","3");
        Call<String> call = jsonPlaceHolderApi.createRegister(dataType_register);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: Code: "+response.code()+"\nErrorMassage: "+response.message());
                    return;
                }


                Log.d(TAG, "onResponse: ResponseCode: "+response.code());

                String dataType_user = response.body();


                //Log.d(TAG, "onResponse: headers: "+headers.toString());
                //Log.d(TAG, "onResponse: body: /nMessage: "+ dataType_user.getMessage()+"/nToken: "+ dataType_user.getToken());

                Log.d(TAG, "onResponse: postResponse: "+dataType_user.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }*/


    //For volley
    /*        StringRequest request = new StringRequest(ApiRequests.Method.POST, "http://kgsebatech.com/api/registerotp", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals(null)) {
                    //.e("Your Array Response", response);
                    Log.d(TAG, "onResponse: "   + response);
                } else {
                    Log.e("Your Array Response", "Data Null");
                    Log.d(TAG, "onResponse: data null");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error is ", "" + error);
                Log.d(TAG, "onErrorResponse: "+error);
            }
        }) {
            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d(TAG, "getHeaders: ");
                Map<String, String> params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json");

                params.put("Accept","application/json");
                params.put("Content-Type","application/json");
                //params.put("Content-Type", "application/json; charset=UTF-8");
                //params.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMjBjMGRlZWIxNDYzZmY0MDg5YjkyOGMxMzA3ZjEwODU0ZGMwMWEyMjU2MjI1MDU5ZDVkNGY2ZDdiZWNhZGE4ZTE0NDEwY2U2NmUxMjRjMzQiLCJpYXQiOjE1OTMwMjAyMjksIm5iZiI6MTU5MzAyMDIyOSwiZXhwIjoxNjI0NTU2MjI5LCJzdWIiOiIxMSIsInNjb3BlcyI6W119.mdKyk-5b6HIC6gvBV1ZmB6EpctmE8Abp8vHr7l1m3a5hNTxkyIfSkri7kBJ3JdCGwFhKUwy4BPUwIlEFekNWTtsIB4N1VdX7OrDop5h2d4GBopguNYpgDqBumL9xWvNfRrfCjLQT_reoF6JL_MZ6k2Tx-fqLvTsFAYjntfWDzmv9lB_PyEUOXQctzlAnh873bxNgMh6H5qy_wVKBcYHa8zJfThyATEQdd8nzqT0qYAlB7GQazuuFDyxxEe3ZH7T9obaCY8AwQ7CLoh-yTmGamkrXDK8O17Un_Zcv3k7x5rvyRu7gQTadErZgOdSTdlqZXwsb0v_pekEBu8ToWvq7NFww5UYZWk5C5vJ7mOL4AxAz3O3UcrOaKuHgeyGu_GvKLVFLOnd4Auqjds9C5K8HIZsb57gx2RHgY7i1R5_oZKcN-8YCV_hjfwdre8kcrKqjq8AYqVVyT1yQ020cGgLGV01YWLBw8WPFTHEihPC0WPDpMusVBQKlEpRZE5dni_R0lpsL-CDuLuz17hR6k4YA1D8kviE6GHE6Re2gdiUaWEJbMdvamyeG4XYkEbn-7oHrUPA33OCkjN1ztd8RM0knpCBEIphCxgTbVlublZyeFNJPo8dnJihn2n318Tx8P934ZS1HV8JdED-KqHZXNklyawSWD0Orx_e4YT4-_tAqX10");
                return params;
            }

            //Pass Your Parameters here
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", "01923060756");
                params.put("email", "ng@gmail.com");
                Log.d(TAG, "getParams: ");

                return params;
            }
        };

         RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
*/

}
