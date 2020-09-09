package com.example.medicine;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class DataType_AllDoctorDetails {

        @SerializedName("id")
        String id;

        @SerializedName("p_photo")
        String p_photo;

        @SerializedName("user_id")
        String user_id;

        @SerializedName("doctor_category_id")
        String doctor_category_id;

        @SerializedName("gender")
        String gender;

        @SerializedName("location")
        String location;

        @SerializedName("age")
        String age;

        @SerializedName("qualifications")
        String qualifications;

        @SerializedName("specialty")
        String specialty;

        @SerializedName("language_spoken")
        String language_spoken;

        @SerializedName("designation")
        String designation;

        @SerializedName("institute")
        String institute;

        @SerializedName("department")
        String department;

        @SerializedName("created_at")
        String created_at;

        @SerializedName("updated_at")
        String updated_at;

        @SerializedName("rating")
        Rating rating;

        @SerializedName("schedule")
        Schedule schedule;


        public DataType_AllDoctorDetails(String id, String p_photo, String user_id, String doctor_category_id, String gender, String location,
                                     String age, String qualifications, String specialty,
                                         String
                                             language_spoken, String designation, String institute,
                                     String department, String created_at, String updated_at, Rating rating, Schedule schedule) {
            this.id = id;
            this.p_photo = p_photo;
            this.user_id = user_id;
            this.doctor_category_id = doctor_category_id;
            this.gender = gender;
            this.location = location;
            this.age = age;
            this.qualifications = qualifications;
            this.specialty = specialty;
            this.language_spoken = language_spoken;
            this.designation = designation;
            this.institute = institute;
            this.department = department;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.rating = rating;
            this.schedule = schedule;
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

        public String getDoctor_category_id() {
            return doctor_category_id;
        }

        public void setDoctor_category_id(String doctor_category_id) {
            this.doctor_category_id = doctor_category_id;
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

        public String getQualifications() {
            return qualifications;
        }

        public void setQualifications(String qualifications) {
            this.qualifications = qualifications;
        }

        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public String getLanguage_spoken() {
            return language_spoken;
        }

        public void setLanguage_spoken(String language_spoken) {
            this.language_spoken = language_spoken;
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

        public Rating getRating() {
            return rating;
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Schedule getSchedule() {
            return schedule;
        }

        public void setSchedule(Schedule schedule) {
            this.schedule = schedule;
        }





    public static class Rating {
        @SerializedName("id")
        String rating_id;

        @SerializedName("count")
        String count;

        @SerializedName("success")
        String success;

        @SerializedName("failure")
        String failure;

        @SerializedName("doctor_id")
        String doctor_id;

        @SerializedName("created_at")
        String created_at;

        @SerializedName("updated_at")
        String updated_at;

        public Rating(String id, String count, String success, String failure, String doctor_id, String created_at, String updated_at) {
            this.rating_id = id;
            this.count = count;
            this.success = success;
            this.failure = failure;
            this.doctor_id = doctor_id;
            this.created_at = created_at;
            this.updated_at = updated_at;
        }

        public String getId() {
            return rating_id;
        }

        public void setId(String id) {
            this.rating_id = id;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getFailure() {
            return failure;
        }

        public void setFailure(String failure) {
            this.failure = failure;
        }

        public String getDoctor_id() {
            return doctor_id;
        }

        public void setDoctor_id(String doctor_id) {
            this.doctor_id = doctor_id;
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

    public static class Schedule {

        List<Schedule_position> schedulePositionList;

        public Schedule(List<Schedule_position> schedulePositionList) {
            this.schedulePositionList = schedulePositionList;
        }

        public List<Schedule_position> getSchedulePositionList() {
            return schedulePositionList;
        }

        public void setSchedulePositionList(List<Schedule_position> schedulePositionList) {
            this.schedulePositionList = schedulePositionList;
        }
    }


        public static class Schedule_position {
            @SerializedName("schedule_day")
            String schedule_day;
            @SerializedName("start_time")
            String start_time;
            @SerializedName("end_time")
            String end_time;
            @SerializedName("status")
            String status;

            public Schedule_position(String schedule_day, String start_time, String end_time, String status) {
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
