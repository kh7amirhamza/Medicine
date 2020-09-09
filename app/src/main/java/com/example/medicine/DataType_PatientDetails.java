package com.example.medicine;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataType_PatientDetails {

    @SerializedName("p_photo")
    String p_photo;

    @SerializedName("address")
    String address;

    /*@SerializedName("gender")
    String gender;*/

    @SerializedName("location")
    String location;

    /*@SerializedName("age")
    String age;*/

    @SerializedName("date_of_birth")
    String date_of_birth;

    @SerializedName("marital_status")
    String marital_status;


    /*@SerializedName("previous_diseasis_history")
    List<String> previous_diseasis_history;


    @SerializedName("family_diseasis_history")
    List<String> family_diseasis_history;
*/

    public DataType_PatientDetails(String p_photo, String address,String location, String date_of_birth, String marital_status) {
        this.p_photo = p_photo;
        this.address = address;
        //this.gender = gender;
        this.location = location;
        //this.age = age;
        this.date_of_birth = date_of_birth;
        this.marital_status = marital_status;
       // this.previous_diseasis_history = previous_diseasis_history;
        //this.family_diseasis_history = family_diseasis_history;
    }

    public String getP_photo() {
        return p_photo;
    }

    public void setP_photo(String p_photo) {
        this.p_photo = p_photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

   /* public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }*/

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /*public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }*/

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

    /*public List<String> getPrevious_diseasis_history() {
        return previous_diseasis_history;
    }

    public void setPrevious_diseasis_history(List<String> previous_diseasis_history) {
        this.previous_diseasis_history = previous_diseasis_history;
    }

    public List<String> getFamily_diseasis_history() {
        return family_diseasis_history;
    }

    public void setFamily_diseasis_history(List<String> family_diseasis_history) {
        this.family_diseasis_history = family_diseasis_history;
    }*/

}
