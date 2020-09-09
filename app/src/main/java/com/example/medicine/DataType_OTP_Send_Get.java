package com.example.medicine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataType_OTP_Send_Get {
    @SerializedName("phone")
    @Expose
    String phone;

    public DataType_OTP_Send_Get(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
