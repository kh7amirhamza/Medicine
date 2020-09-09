package com.example.medicine;

import com.google.gson.annotations.SerializedName;

public class DT_PlaceAppointment {

    @SerializedName("patient_id")
    Integer patient_id;

    @SerializedName("doctor_id")
    Integer doctor_id;

    @SerializedName("appo_day")
    String appo_day;

    @SerializedName("patient_name")
    String patient_name;

    public DT_PlaceAppointment(Integer patient_id, Integer doctor_id, String appo_day, String patient_name) {
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.appo_day = appo_day;
        this.patient_name = patient_name;
    }

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public Integer getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(Integer doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getAppo_day() {
        return appo_day;
    }

    public void setAppo_day(String appo_day) {
        this.appo_day = appo_day;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }
}
