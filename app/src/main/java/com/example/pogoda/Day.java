package com.example.pogoda;

public class Day {
    private String dayName;
    private String dayTemp;
    private int weatherIcon;

    public Day(String dayName, int dayTemp, int weatherIcon) {
        this.dayName = dayName;
        this.dayTemp = ""+dayTemp;
        this.weatherIcon = weatherIcon;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(String dayTemp) {
        this.dayTemp = dayTemp;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}

