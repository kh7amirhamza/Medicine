package com.example.medicine;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataType_SavePatientHistory {


@SerializedName("patient_user_id")
int patient_user_id;

@SerializedName("doctor_id")
int doctor_id;

@SerializedName("status")
int status;

@SerializedName("photou_url")
List<String> photou_url;

    public DataType_SavePatientHistory(int patient_user_id, int doctor_id, int status, List<String> photou_url) {
        this.patient_user_id = patient_user_id;
        this.doctor_id = doctor_id;
        this.status = status;
        this.photou_url = photou_url;
    }

    public int getPatient_user_id() {
        return patient_user_id;
    }

    public void setPatient_user_id(int patient_user_id) {
        this.patient_user_id = patient_user_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getPhotou_url() {
        return photou_url;
    }

    public void setPhotou_url(List<String> photou_url) {
        this.photou_url = photou_url;
    }
}
