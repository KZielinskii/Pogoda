package com.example.pogoda;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Locality {

    private Context context;
    private final String name;
    private double currentTemperature;
    private boolean isSunny;
    private boolean isRaining;

    public Locality(String name, Context context) {
        this.name = name;
        this.context = context;
        updateWeather(name);
    }

    public String getName() {
        return name;
    }

    public double getCurrentTemperature()
    {
        return currentTemperature;
    }

    public void updateWeather(String location)
    {
        String start = "https://api.openweathermap.org/data/2.5/weather?q=";
        String end = "&appid=bc170ec055ab5eb458be802e3683e686&units=metric";
        String url = start+location+end;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject main = response.getJSONObject("main");
                        currentTemperature = main.getDouble("temp");

                        JSONArray weather = response.getJSONArray("weather");
                        String weatherDescription = weather.getJSONObject(0).getString("description");

                        isSunny = weatherDescription.contains("clear");
                        isRaining = weatherDescription.contains("rain");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    error.printStackTrace();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }
}
