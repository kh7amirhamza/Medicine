package com.example.medicine;

import com.google.gson.annotations.SerializedName;

public class DataType_Doctor_schedule {

    @SerializedName("schedule_day")
    String schedule_day;

    @SerializedName("start_time")
    String start_time;

    @SerializedName("end_time")
    String end_time;

    @SerializedName("status")
    int status;

    public DataType_Doctor_schedule(String schedule_day, String start_time, String end_time, int status) {
        this.schedule_day = schedule_day;
        this.start_time = start_time;
        this.end_time = end_time;
        this.status = status;
    }

    public String getSchedule_day() {
        return schedule_day;
    }

    public void setSchedule_day(String schedule_day) {
        this.schedule_day = schedule_day;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
