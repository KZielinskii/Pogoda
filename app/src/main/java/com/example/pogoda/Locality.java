package com.example.pogoda;

public class Locality {

    private final String name;
    private int currentTemperature;

    public Locality(String name) {
        this.name = name;
        //TODO currentTemperature = ?
        currentTemperature = 0;
    }

    public String getName() {
        return name;
    }

    public int getCurrentTemperature()
    {
        return currentTemperature;
    }

    public void setCurrentTemperature()
    {
        //TODO currentTemperature = ?;
    }
}
