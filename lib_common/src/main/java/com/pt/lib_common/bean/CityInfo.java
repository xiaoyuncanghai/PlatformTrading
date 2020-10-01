package com.pt.lib_common.bean;

public class CityInfo {
    private String cityName;
    private String cityCode;

    public CityInfo() {
    }

    public CityInfo(String cityName, String cityCode) {
        this.cityName = cityName;
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
