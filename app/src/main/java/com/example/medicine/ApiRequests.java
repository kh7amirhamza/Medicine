package com.example.medicine;

import com.example.medicine.DataType.DoctorDetailsPostRespo;
import com.example.medicine.DataType.DoctorProfile;
import com.example.medicine.DataType.LogIn;
import com.example.medicine.DataType.PatientProfile;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRequests {

    //@Headers({"Accept: application/json"})
    @POST("registerotp")
    Call<JsonObject> createRegisterWithOTP(@Body DataType_OTP_Send_Get dataType_otp_send);

    @POST("register")
    Call<JsonObject> createRegister(@Body DataType_Register dataType_register);

    @POST("patientDetails")
    Call<String> viewPatient(@Header("Authorization") String dataTypethToken);

    @POST("verifyotp")
    Call<JsonObject> verifyOtp(@Header("Authorization") String token, @Body DataType_OTP otp);

    @POST("profile")
    Call<JsonObject> setPatientProfile(@Header("Authorization") String token, @Body PatientProfile patientProfile);

    @POST("profile")
    Call<JsonObject> setDoctorProfile(@Header("Authorization") String token, @Body DoctorProfile doctorProfile);


    @POST("patientDetails")
    Call<JsonObject> patientDetails(@Header("Authorization") String token, @Body DataType_PatientDetails dataType_patientDetails);

    @POST("doctordetails")
    Call<JsonObject> postDoctorDetails(@Header("Authorization") String token, @Body DoctorDetailsPostRespo doctorDetailsPostRespo);


    @POST("appointmentsave")
    Call<JsonObject> placeAppointment(@Header("Authorization") String token, @Body DT_PlaceAppointment dt_placeAppointment);


    @GET("doctordetails")
    Call<JsonObject> getDoctorDetails(@Header("Authorization") String token);

    @GET("patientDetails")
    Call<JsonObject> getPatientDetails(@Header("Authorization") String token);

    @GET("allCategoryOfDoctor")
    Call<JsonObject> getAllCategoryOfDoctor(@Header("Authorization") String token);

    @GET("getalldoctor/{id}")
    Call<JsonObject> getAlldoctorByCategory(@Header("Authorization") String token, @Path("id") String categoryId);


    @GET("appointmentget")
    Call<JsonObject> getAppointment(@Header("Authorization") String token);

    @POST("patientHistory")
    Call<JsonObject> postSavePatientHistory(@Header("Authorization") String token, @Body DataType_SavePatientHistory savePatientHistory);

    @GET("patientHistory")
    Call<JsonArray> getPatientHistory(@Header("Authorization") String token);

    @POST("login")
    Call<JsonObject> postLogin(@Body LogIn dataType_login);

    @GET("logout")
    Call<JsonObject> getLogout(@Header("Authorization") String token);

    @POST("changeschedule")
    Call<JsonObject> postChangeSchudule(@Header("Authorization") String token, @Body List<DataType_Doctor_schedule> dataType_doctor_scheduleList);

    @GET("doctorschedule")
    Call<JsonArray> getDoctorSchedule(@Header("Authorization") String token);

}
