package com.example.medicine;

public class DoctorsCategoryModel {
    private int image;
    private String title;

    public DoctorsCategoryModel(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public DoctorsCategoryModel() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
