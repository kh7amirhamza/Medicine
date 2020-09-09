package com.example.medicine;

import com.google.gson.annotations.SerializedName;

public class DataType_OTP {
    @SerializedName("otp")
    String otp;

    public DataType_OTP(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
