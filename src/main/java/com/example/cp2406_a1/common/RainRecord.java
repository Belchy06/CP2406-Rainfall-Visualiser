package com.example.cp2406_a1.common;

import java.util.ArrayList;

public class RainRecord {
    private String productCode;
    private String stationNum;
    private int year;
    private int month;
    private int day;
    private double rain;
    private int periodDays;
    private String quality;

    RainRecord(String productCode, String stationNum, int year, int month, int day, double rain, int periodDays, String quality) {
        this.productCode = productCode;
        this.stationNum = stationNum;
        this.year = year;
        this.month = month;
        this.day = day;
        this.rain = rain;
        this.periodDays = periodDays;
        this.quality = quality;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getStationNum() {
        return stationNum;
    }

    public void setStationNum(String stationNum) {
        this.stationNum = stationNum;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public int getPeriodDays() {
        return periodDays;
    }

    public void setPeriodDays(int periodDays) {
        this.periodDays = periodDays;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }
}
