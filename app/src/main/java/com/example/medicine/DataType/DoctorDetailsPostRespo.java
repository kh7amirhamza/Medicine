package com.example.medicine.DataType;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorDetailsPostRespo {

    @SerializedName("c")
    String c;

    @SerializedName("gender")
    String gender;

    @SerializedName("location")
    String location;

    @SerializedName("age")
    int age;

    @SerializedName("designation")
    String designation;

    @SerializedName("institute")
    String institute;

    @SerializedName("department")
    String department;

    @SerializedName("qualifications")
    List<String> qualifications;

    @SerializedName("specialty")
    List<String> specialty;

    @SerializedName("language_spoken")
    List<String> language_spoken;

    @SerializedName("doctor_rating")
    String doctor_rating;

    @SerializedName("doctor_schedule")
    DoctorSchedule doctor_schedule;

    public DoctorDetailsPostRespo(String c, String gender, String location, int age, String designation, String institute, String department, List<String> qualifications, List<String> specialty, List<String> language_spoken, String doctor_rating, DoctorSchedule doctor_schedule) {
        this.c = c;
        this.gender = gender;
        this.location = location;
        this.age = age;
        this.designation = designation;
        this.institute = institute;
        this.department = department;
        this.qualifications = qualifications;
        this.specialty = specialty;
        this.language_spoken = language_spoken;
        this.doctor_rating = doctor_rating;
        this.doctor_schedule = doctor_schedule;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<String> qualifications) {
        this.qualifications = qualifications;
    }

    public List<String> getSpecialty() {
        return specialty;
    }

    public void setSpecialty(List<String> specialty) {
        this.specialty = specialty;
    }

    public List<String> getLanguage_spoken() {
        return language_spoken;
    }

    public void setLanguage_spoken(List<String> language_spoken) {
        this.language_spoken = language_spoken;
    }

    public String getDoctor_rating() {
        return doctor_rating;
    }

    public void setDoctor_rating(String doctor_rating) {
        this.doctor_rating = doctor_rating;
    }

    public DoctorSchedule getDoctor_schedule() {
        return doctor_schedule;
    }

    public void setDoctor_schedule(DoctorSchedule doctor_schedule) {
        this.doctor_schedule = doctor_schedule;
    }
}
