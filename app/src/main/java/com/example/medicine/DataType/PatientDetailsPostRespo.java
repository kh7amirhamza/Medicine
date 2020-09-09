package com.example.medicine.DataType;

import com.google.gson.annotations.SerializedName;

public class PatientDetailsPostRespo {
    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("phone")
    String phone;

    @SerializedName("id")
    String id;

    @SerializedName("p_photo")
    String p_photo;

    @SerializedName("user_id")
    String user_id;

    @SerializedName("address")
    String address;

    @SerializedName("gender")
    String gender;

    @SerializedName("location")
    String location;

    @SerializedName("age")
    String age;

    @SerializedName("date_of_birth")
    String date_of_birth;

    @SerializedName("marital_status")
    String marital_status;

    @SerializedName("previous_diseasis_history")
    String[] previous_diseasis_history;

    @SerializedName("family_diseasis_history")
    String[] family_diseasis_history;

    @SerializedName("created_at")
    String created_at;

    @SerializedName("updated_at")
    String updated_at;


    public PatientDetailsPostRespo(String name, String email, String phone, String id, String p_photo,
                                   String user_id, String address, String gender, String location,
                                   String age, String date_of_birth, String marital_status,
                                   String[] previous_diseasis_history, String[] family_diseasis_history,
                                   String created_at, String updated_at) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.id = id;
        this.p_photo = p_photo;
        this.user_id = user_id;
        this.address = address;
        this.gender = gender;
        this.location = location;
        this.age = age;
        this.date_of_birth = date_of_birth;
        this.marital_status = marital_status;
        this.previous_diseasis_history = previous_diseasis_history;
        this.family_diseasis_history = family_diseasis_history;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP_photo() {
        return p_photo;
    }

    public void setP_photo(String p_photo) {
        this.p_photo = p_photo;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String[] getPrevious_diseasis_history() {
        return previous_diseasis_history;
    }

    public void setPrevious_diseasis_history(String[] previous_diseasis_history) {
        this.previous_diseasis_history = previous_diseasis_history;
    }

    public String[] getFamily_diseasis_history() {
        return family_diseasis_history;
    }

    public void setFamily_diseasis_history(String[] family_diseasis_history) {
        this.family_diseasis_history = family_diseasis_history;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
