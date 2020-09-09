package com.example.medicine;

public class DataType_DoctorDetails {

    int doctor_photo;
    String doctor_name,specialize_in,working_company;

    public DataType_DoctorDetails(int doctor_photo,String doctor_name, String specialize_in, String working_company) {
        this.doctor_photo = doctor_photo;
        this.doctor_name = doctor_name;
        this.specialize_in = specialize_in;
        this.working_company = working_company;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getSpecialize_in() {
        return specialize_in;
    }

    public void setSpecialize_in(String specialize_in) {
        this.specialize_in = specialize_in;
    }

    public String getWorking_company() {
        return working_company;
    }

    public void setWorking_company(String working_company) {
        this.working_company = working_company;
    }

    public int getDoctor_photo() {
        return doctor_photo;
    }

    public void setDoctor_photo(int doctor_photo) {
        this.doctor_photo = doctor_photo;
    }
}
