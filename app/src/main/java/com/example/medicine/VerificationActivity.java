package com.example.medicine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.style.FadingCircle;
import com.goodiebag.pinview.Pinview;
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

public class VerificationActivity extends AppCompatActivity {
    private static final String TAG = "VerifyCode";

    String OTP_CODE;
    Button btn_verify_otp, btn_resend_code;
    TextView txtv3;
    Pinview pinview;
    String phoneNumber;
    private ProgressBar progressbar;
    LinearLayout lay_1;
    String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        Log.d(TAG, "onClick: phoneNumber: " + phoneNumber);

        //String phoneNumber = "+8801705187083";
        // String phoneNumber = "+8801705187083";
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);

        btn_verify_otp = findViewById(R.id.btn_verify_otp);
        btn_resend_code = findViewById(R.id.btn_resend_code);
        txtv3 = findViewById(R.id.txtv3);
        pinview = findViewById(R.id.pinview);
        pinview.setValue("12345");
        lay_1 = findViewById(R.id.lay_1);

        progressbar = findViewById(R.id.av_spin_kit_progressbar);
        FadingCircle fadingCircle = new FadingCircle();
        progressbar.setIndeterminateDrawable(fadingCircle);


        //sendVerificationCode(phoneNumber);
        createRegisterWithOTP(new DataType_OTP_Send_Get(phoneNumber));

        txtv3.setText(phoneNumber);


        btn_resend_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                //sendVerificationCode(phoneNumber);
                btn_resend_code.setVisibility(View.GONE);
                lay_1.setVisibility(View.VISIBLE);
                pinview.setValue("");
            }
        });

        btn_verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressbar.getVisibility() == View.VISIBLE) {
                    Toast.makeText(VerificationActivity.this, "Task is in progress", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);
                String otp = pinview.getValue();
                if (otp.length() == 5) {
                    if (otp != null) {

                        verifyOTP(new DataType_OTP(otp));
                        //PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationOTP, otp);
                        //signInWithPhoneAuthCredential(credential);
                    } else {

                        Toast.makeText(VerificationActivity.this, "Wrong verification code!\nPlease send again...", Toast.LENGTH_SHORT).show();
                        lay_1.setVisibility(View.INVISIBLE);
                        btn_resend_code.setVisibility(View.VISIBLE);
                        progressbar.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(VerificationActivity.this, "Please insert 5 digit verification code", Toast.LENGTH_SHORT).show();
                }
            }
        });


        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                //Make api calls here or what not
                OTP_CODE = pinview.getValue();
                //Toast.makeText(VerificationActivity.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
            }
        });
        //pinview.getValue();

    }


    private void verifyOTP(DataType_OTP otp) {
        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiRequests apiRequests = retrofit.create(ApiRequests.class);

        Call<JsonObject> call = apiRequests.verifyOtp(token, otp);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressbar.setVisibility(View.GONE);
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code: " + response.code());
                    progressbar.setVisibility(View.GONE);
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    progressbar.setVisibility(View.GONE);
                    if (jsonObject != null) {

                        token = "Bearer " + jsonObject.getString("tokrn");//Toast.makeText(VerificationActivity.this, token, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(VerificationActivity.this, SetUpProfileActivity.class)
                                .putExtra("phone", phoneNumber)
                                .putExtra("token", token)
                                .putExtra("role","3")
                        );
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onResponse: ResponseCode: " + response.code());
                Log.d(TAG, "onResponse: postResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
                progressbar.setVisibility(View.GONE);
                Log.d(TAG, "CheckResponse: onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void createRegisterWithOTP(DataType_OTP_Send_Get dataType_otp_send) {
        progressbar.setVisibility(View.VISIBLE);
        Gson gson = new GsonBuilder().serializeNulls().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kgsebatech.com/api/")
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiRequests apiRequests = retrofit.create(ApiRequests.class);

        Call<JsonObject> call = apiRequests.createRegisterWithOTP(dataType_otp_send);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressbar.setVisibility(View.GONE);
                Log.d(TAG, "onResponse: called");
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code: " + response.code());
                    progressbar.setVisibility(View.GONE);
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    token = "token";
                    token = "Bearer " + jsonObject.getString("token");
                    Toast.makeText(VerificationActivity.this, "Code sent", Toast.LENGTH_SHORT).show();
                    progressbar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onResponse: ResponseCode: " + response.code());
                Log.d(TAG, "onResponse: postResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
                Log.d(TAG, "CheckResponse: onFailure: " + t.getLocalizedMessage());
            }
        });
    }


    // This items for firebase Phone Authentication...

  /*  private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
               // mCallbacks        // OnVerificationStateChangedCallbacks




        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                if (credential.getSmsCode()!=null){
                    pinview.setValue(credential.getSmsCode());
                    signInWithPhoneAuthCredential(credential);
                }

            }
            //01627548380

            @Override
            public void onVerificationFailed(FirebaseException e) {
                progressbar.setVisibility(View.GONE);
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(VerificationActivity.this, "Code sent", Toast.LENGTH_SHORT).show();
                verificationOTP = verificationId;

                // Save verification ID and resending token so we can use them later
                //mVerificationId = verificationId;
                //mResendToken = token;

                // ...
            }
        }
        );

    }*/

  /*  private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressbar.setVisibility(View.GONE);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(VerificationActivity.this, "Sign in success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(VerificationActivity.this,MainActivity.class));
                            finish();
                            FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            progressbar.setVisibility(View.GONE);
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(VerificationActivity.this, "Sign in Failed", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }*/
}
