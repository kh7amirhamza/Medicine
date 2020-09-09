package com.example.medicine;

import com.google.gson.annotations.SerializedName;

public class DataType_Register {

    String name;
    String email;
    String password;

    @SerializedName("password confirmation")
    String password_confirmation;

    String phone;
    String role;

    public DataType_Register(String name, String email, String password, String password_confirmation, String phone, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;
        this.phone = phone;
        this.role = role;
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

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
