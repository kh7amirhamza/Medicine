package com.example.medicine;

public class DataType_PatientHistory {

    String title, cases_sort_history, doctor_name,doctor_desination,doctor_institute,time;

    public DataType_PatientHistory(String title, String cases_sort_history, String doctor_name, String doctor_desination, String doctor_institute, String time) {
        this.title = title;
        this.cases_sort_history = cases_sort_history;
        this.doctor_name = doctor_name;
        this.doctor_desination = doctor_desination;
        this.doctor_institute = doctor_institute;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCases_sort_history() {
        return cases_sort_history;
    }

    public void setCases_sort_history(String cases_sort_history) {
        this.cases_sort_history = cases_sort_history;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_desination() {
        return doctor_desination;
    }

    public void setDoctor_desination(String doctor_desination) {
        this.doctor_desination = doctor_desination;
    }

    public String getDoctor_institute() {
        return doctor_institute;
    }

    public void setDoctor_institute(String doctor_institute) {
        this.doctor_institute = doctor_institute;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
