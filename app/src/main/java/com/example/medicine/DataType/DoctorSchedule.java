package com.example.medicine.DataType;

import com.google.gson.annotations.SerializedName;

public class DoctorSchedule {
    @SerializedName("0")
    SchedulePosition0 schedule_0;

    @SerializedName("1")
    SchedulePosition1 schedule_1;

    @SerializedName("2")
    SchedulePosition2 schedule_2;

    public DoctorSchedule(SchedulePosition0 schedule_0, SchedulePosition1 schedule_1, SchedulePosition2 schedule_2) {
        this.schedule_0 = schedule_0;
        this.schedule_1 = schedule_1;
        this.schedule_2 = schedule_2;
    }

    public static class SchedulePosition0 {
        @SerializedName("schedule_day")
        String schedule_day;
        @SerializedName("start_time")
        String start_time;
        @SerializedName("end_time")
        String end_time;
        @SerializedName("status")
        String status;

        public SchedulePosition0(String schedule_day, String start_time, String end_time, String status) {
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class SchedulePosition1 {
        @SerializedName("schedule_day")
        String schedule_day;
        @SerializedName("start_time")
        String start_time;
        @SerializedName("end_time")
        String end_time;
        @SerializedName("status")
        String status;

        public SchedulePosition1(String schedule_day, String start_time, String end_time, String status) {
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class SchedulePosition2 {
        @SerializedName("schedule_day")
        String schedule_day;
        @SerializedName("start_time")
        String start_time;
        @SerializedName("end_time")
        String end_time;
        @SerializedName("status")
        String status;

        public SchedulePosition2(String schedule_day, String start_time, String end_time, String status) {
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
