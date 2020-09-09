package com.example.medicine.DataType;

public class DoctorShortDetailsGet {
    String id;
    String name;
    String category;
    String department;
    String designation;
    String institute;
    String consultation_fee;

    public DoctorShortDetailsGet(String id, String name, String category, String department, String designation, String institute, String consultation_fee) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.department = department;
        this.designation = designation;
        this.institute = institute;
        this.consultation_fee = consultation_fee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getConsultation_fee() {
        return consultation_fee;
    }

    public void setConsultation_fee(String consultation_fee) {
        this.consultation_fee = consultation_fee;
    }
}
