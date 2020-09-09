package com.example.medicine;

public class DataTypeAllCategoryOfDoctor {

    int id;
    String cat_name,cat_in_bangla,cat_image,created_at,updated_at;

    public DataTypeAllCategoryOfDoctor(int id, String cat_name, String cat_in_bangla, String cat_image, String created_at, String updated_at) {
        this.id = id;
        this.cat_name = cat_name;
        this.cat_in_bangla = cat_in_bangla;
        this.cat_image = cat_image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_in_bangla() {
        return cat_in_bangla;
    }

    public void setCat_in_bangla(String cat_in_bangla) {
        this.cat_in_bangla = cat_in_bangla;
    }

    public String getCat_image() {
        return cat_image;
    }

    public void setCat_image(String cat_image) {
        this.cat_image = cat_image;
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
