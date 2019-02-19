package com.example.batterent.Model;

public class BatteryModel {


    public String batteryName;
    public String color;

    public BatteryModel(String batteryName, String color) {
        this.batteryName = batteryName;
        this.color = color;
    }

    public String getBatteryName() {
        return batteryName;
    }

    public void setBatteryName(String batteryName) {
        this.batteryName = batteryName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
