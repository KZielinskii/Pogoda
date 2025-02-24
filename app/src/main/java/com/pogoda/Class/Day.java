package com.example.pogoda.Class;

public class Day {
    private final String dayName;
    private final String dayTemp;
    private final String description;

    public Day(String dayName, int dayTemp, String description) {
        this.dayName = dayName;
        this.dayTemp = ""+dayTemp;
        this.description = description;
    }

    public String getDayName() {
        return dayName;
    }
    public String getDayTemp() {return dayTemp;}
    public String getDescription() {return description;}
}

