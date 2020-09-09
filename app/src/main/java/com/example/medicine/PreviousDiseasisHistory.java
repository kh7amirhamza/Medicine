package com.example.medicine;

import com.google.gson.annotations.SerializedName;

public class PreviousDiseasisHistory {

    @SerializedName("0")
    String position0;

    @SerializedName("1")
    String position1;

    @SerializedName("2")
    String position2;

    public PreviousDiseasisHistory(String position0, String position1, String position2) {
        this.position0 = position0;
        this.position1 = position1;
        this.position2 = position2;
    }

    public String getPosition0() {
        return position0;
    }

    public void setPosition0(String position0) {
        this.position0 = position0;
    }

    public String getPosition1() {
        return position1;
    }

    public void setPosition1(String position1) {
        this.position1 = position1;
    }

    public String getPosition2() {
        return position2;
    }

    public void setPosition2(String position2) {
        this.position2 = position2;
    }
}
