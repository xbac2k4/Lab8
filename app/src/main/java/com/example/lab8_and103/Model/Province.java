package com.example.lab8_and103.Model;

public class Province {
    private int ProvinceID;
    private String ProvinceName;

    public Province() {
    }

    public Province(int provinceID, String provinceName) {
        ProvinceID = provinceID;
        ProvinceName = provinceName;
    }

    public int getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(int provinceID) {
        ProvinceID = provinceID;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }
}
