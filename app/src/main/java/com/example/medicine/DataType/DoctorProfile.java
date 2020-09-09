package com.example.medicine.DataType;

import com.google.gson.annotations.SerializedName;

public class DoctorProfile {
    @SerializedName("name")
    String name;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("role")
    String role;
    @SerializedName("doctor_category")
    String doctor_category;

    public DoctorProfile(String name, String email, String password, String role,String doctor_category) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.doctor_category = doctor_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDoctor_category() {
        return doctor_category;
    }

    public void setDoctor_category(String doctor_category) {
        this.doctor_category = doctor_category;
    }
}
