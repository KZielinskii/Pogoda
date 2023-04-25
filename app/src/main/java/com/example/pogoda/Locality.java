package com.example.pogoda;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
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
    private int currentTemperature;
    private boolean isSunny;
    private boolean isRaining;
    private double latitude;
    private double longitude;
    private double pressure;

    public Locality(String name, Context context) {
        this.name = name;
        this.context = context;
        readFromPreferences();
        updateWeather();
    }

    public String getName() {
        return name;
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }
    public boolean getIsSunny(){return isSunny;}
    public boolean getIsRaining(){return isRaining;}
    public double getLatitude(){return latitude;}
    public double getLongitude(){return longitude;}
    public double getPressure(){return pressure;}

    public void updateWeather() {

        String start = "https://api.openweathermap.org/data/2.5/weather?q=";
        String end = "&appid=bc170ec055ab5eb458be802e3683e686&units=metric";
        String url = start + name + end;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject main = response.getJSONObject("main");
                        currentTemperature = main.getInt("temp");
                        latitude = response.getJSONObject("coord").getDouble("lat");
                        longitude = response.getJSONObject("coord").getDouble("lon");
                        pressure = main.getDouble("pressure");

                        JSONArray weather = response.getJSONArray("weather");
                        String weatherDescription = weather.getJSONObject(0).getString("description");

                        isSunny = weatherDescription.contains("clear");
                        isRaining = weatherDescription.contains("rain");

                        saveToPreferences();
                        MainActivity.localitiesListAddapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        readFromPreferences();
                        Toast.makeText(context, "Dane mogą być nieaktualne.\n (Sprawdź połączenie z internetem!)", Toast.LENGTH_SHORT).show();
                    } else if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                        Toast.makeText(context, "Podano niepoprawną nazwę lokalizacji!", Toast.LENGTH_SHORT).show();
                        LocalitiesListAddapter.getLocalities().remove(this);
                        MainActivity.localitiesListAddapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Wystąpił błąd: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    readFromPreferences();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    private void readFromPreferences() {
        SharedPreferences preferences = context.getSharedPreferences("SaveWeather", Context.MODE_PRIVATE);
        String saved = preferences.getString(name, "");
        String[] dane = saved.split(",");

        if(!saved.equals(""))
        {
            if(dane[0]!=null)currentTemperature = Double.valueOf(Double.parseDouble(dane[0])).intValue();
            if(dane[1]!=null)isSunny = Boolean.parseBoolean(dane[1]);
            if(dane[2]!=null)isRaining = Boolean.parseBoolean(dane[2]);
            if(dane[3]!=null)latitude = Double.parseDouble(dane[3]);
            if(dane[4]!=null)longitude = Double.parseDouble(dane[4]);
            if(dane[5]!=null)pressure = Double.parseDouble(dane[5]);
        }
    }

    private void saveToPreferences() {
        SharedPreferences preferences = context.getSharedPreferences("SaveWeather", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, currentTemperature + "," + isSunny + "," + isRaining + "," + latitude + "," + longitude + "," + pressure);
        editor.apply();
    }

    public void deleteFromPreferences() {
        SharedPreferences preferences = context.getSharedPreferences("SaveWeather", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(name);
        editor.apply();
    }
}
