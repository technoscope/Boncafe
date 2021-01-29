package com.example.boncafe.ui.cafe;

public class BranchesModel {

    public String address;
    public float lat;
    public float lon;
    public String name_ar;
    public String name_en;
    public String no;
    public String tel;

    // Getter Methods

    public String getAddress() {
        return address;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getName_ar() {
        return name_ar;
    }

    public String getName_en() {
        return name_en;
    }

    public String getNo() {
        return no;
    }

    public String getTel() {
        return tel;
    }

    // Setter Methods

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public void setName_ar(String name_ar) {
        this.name_ar = name_ar;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}

