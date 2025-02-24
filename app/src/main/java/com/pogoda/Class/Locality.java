package com.example.pogoda.Class;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pogoda.Activity.MainActivity;
import com.example.pogoda.Activity.WeatherForecastActivity;
import com.example.pogoda.Adapter.LocalitiesListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Locality
{
    public static final int FOR_SIZE = 64;
    private int countFiveDaysData;
    private final Context context;
    private final String name;
    private int currentTemperature;
    private boolean isSunny;
    private boolean isRaining;
    private double latitude;
    private double longitude;
    private double pressure;
    private String weatherDescription;
    private int visibilityInMeters;
    private int humidity;
    private int windSpeed;
    private int windDegree;
    private final String[] dateFiveDays = new String[FOR_SIZE];
    private final int[] temperatureFiveDays = new int[FOR_SIZE];
    private final String[] descriptionFiveDays = new String[FOR_SIZE];

    public Locality(String name, Context context)
    {
        this.name = name;
        this.context = context;
        readFromPreferencesWeather();
        updateWeather();
    }

    public String getName()
    {
        return name;
    }

    public int getCurrentTemperature()
    {
        return currentTemperature;
    }

    public boolean getIsSunny()
    {
        return isSunny;
    }

    public boolean getIsRaining()
    {
        return isRaining;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public double getPressure()
    {
        return pressure;
    }

    public String getWeatherDescription()
    {
        return weatherDescription;
    }

    public int getHumidity()
    {
        return humidity;
    }

    public int getVisibilityInMeters()
    {
        return visibilityInMeters;
    }

    public int getWindDegree()
    {
        return windDegree;
    }

    public int getWindSpeed()
    {
        return windSpeed;
    }

    public String getDateFiveDays(int index)
    {
        return dateFiveDays[index];
    }

    public int getTemperatureFiveDays(int index)
    {
        return temperatureFiveDays[index];
    }

    public String getDescriptionFiveDays(int index)
    {
        return descriptionFiveDays[index];
    }

    public void updateWeather()
    {
        updateOneDaysWeateher();
        updateFiveDaysWeather();
    }

    private void updateOneDaysWeateher()
    {
        String start = "https://api.openweathermap.org/data/2.5/weather?q=";
        String end = "&appid=bc170ec055ab5eb458be802e3683e686&units=metric";
        String url = start + name + end;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response ->
                {
                    try
                    {
                        JSONObject main = response.getJSONObject("main");
                        JSONObject wind = response.getJSONObject("wind");
                        JSONArray weather = response.getJSONArray("weather");

                        currentTemperature = main.getInt("temp");
                        humidity = main.getInt("humidity");
                        windSpeed = wind.getInt("speed");
                        windDegree = wind.getInt("deg");
                        visibilityInMeters = response.getInt("visibility");
                        latitude = response.getJSONObject("coord").getDouble("lat");
                        longitude = response.getJSONObject("coord").getDouble("lon");
                        pressure = main.getDouble("pressure");
                        weatherDescription = weather.getJSONObject(0).getString("description");
                        isSunny = weatherDescription.contains("clear");
                        isRaining = weatherDescription.contains("rain");

                        saveToPreferencesOneWeather();
                        MainActivity.localitiesListAdapter.notifyDataSetChanged();
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                },
                error ->
                {
                    if (error instanceof NetworkError)
                    {
                        readFromPreferencesOneWeather();
                        readFromPreferencesFiveDaysWeather();
                        Toast.makeText(context, "Dane mogą być nieaktualne.\n (Sprawdź połączenie z internetem!)", Toast.LENGTH_SHORT).show();
                    } else if (error.networkResponse != null && error.networkResponse.statusCode == 404)
                    {
                        Toast.makeText(context, "Podano niepoprawną nazwę lokalizacji!", Toast.LENGTH_SHORT).show();
                        LocalitiesListAdapter.getLocalities().remove(this);
                        MainActivity.localitiesListAdapter.notifyDataSetChanged();
                    } else
                    {
                        readFromPreferencesOneWeather();
                        readFromPreferencesFiveDaysWeather();
                        Toast.makeText(context, "Dane mogą być nieaktualne.\n (Sprawdź połączenie z internetem!)", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public void updateFiveDaysWeather()
    {
        String start = "https://api.openweathermap.org/data/2.5/forecast?q=";
        String end = "&appid=bc170ec055ab5eb458be802e3683e686&units=metric";
        String url = start + name + end;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response ->
                {
                    try
                    {
                        JSONArray forecasts = response.getJSONArray("list");
                        countFiveDaysData = forecasts.length();
                        for (int i = 0; i < countFiveDaysData; i++)
                        {
                            JSONObject forecast = forecasts.getJSONObject(i);
                            JSONObject main = forecast.getJSONObject("main");
                            JSONArray weather = forecast.getJSONArray("weather");

                            String date = forecast.getString("dt_txt");

                            dateFiveDays[i] = date;
                            temperatureFiveDays[i] = main.getInt("temp");
                            descriptionFiveDays[i] = weather.getJSONObject(0).getString("description");
                        }
                        saveToPreferencesFiveDaysWeather();
                        if(WeatherForecastActivity.adapter != null && !WeatherForecastActivity.isRotated)
                        {
                            WeatherForecastActivity.refreshData(this);
                            WeatherForecastActivity.adapter.notifyDataSetChanged();
                        }
                        if(WeatherForecastActivity.daysFragment != null && WeatherForecastActivity.isRotated)
                        {
                            WeatherForecastActivity.refreshData(this);
                            WeatherForecastActivity.updateViewRotatedFragment();
                        }

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                },
                error ->
                {
                    if (error instanceof NetworkError)
                    {
                        Toast.makeText(context, "Dane mogą być nieaktualne.\n (Sprawdź połączenie z internetem!)", Toast.LENGTH_SHORT).show();
                        readFromPreferencesFiveDaysWeather();
                    } else if (error.networkResponse != null && error.networkResponse.statusCode == 404)
                    {
                        Toast.makeText(context, "Podano niepoprawną nazwę lokalizacji!", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        Toast.makeText(context, "Dane mogą być nieaktualne.\n (Sprawdź połączenie z internetem!)", Toast.LENGTH_SHORT).show();
                    }
                    readFromPreferencesFiveDaysWeather();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    private void readFromPreferencesWeather()
    {
        readFromPreferencesOneWeather();
        readFromPreferencesFiveDaysWeather();
    }

    public void deleteFromPreferencesWeather()
    {
        deleteFromPreferencesOneWeather();
        deleteFromPreferencesFiveDaysWeather();
    }

    private void readFromPreferencesOneWeather()
    {
        SharedPreferences preferences = context.getSharedPreferences("SaveWeather", Context.MODE_PRIVATE);
        String saved = preferences.getString(name, "");
        String[] dane = saved.split(",");

        if (!saved.equals(""))
        {
            currentTemperature = Double.valueOf(Double.parseDouble(dane[0])).intValue();
            latitude = Double.parseDouble(dane[1]);
            longitude = Double.parseDouble(dane[2]);
            pressure = Double.parseDouble(dane[3]);
            weatherDescription = String.valueOf(dane[4]);
            visibilityInMeters = Integer.parseInt(dane[5]);
            humidity = Integer.parseInt(dane[6]);
            windSpeed = Integer.parseInt(dane[7]);
            windDegree = Integer.parseInt(dane[8]);
        }
    }

    private void saveToPreferencesOneWeather()
    {
        SharedPreferences preferences = context.getSharedPreferences("SaveWeather", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, currentTemperature + "," + latitude + "," + longitude + "," + pressure + "," + weatherDescription + "," + visibilityInMeters + "," + humidity + "," + windSpeed + "," + windDegree);
        editor.apply();
    }

    public void deleteFromPreferencesOneWeather()
    {
        SharedPreferences preferences = context.getSharedPreferences("SaveWeather", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(name);
        editor.apply();
    }

    private void readFromPreferencesFiveDaysWeather()
    {
        SharedPreferences preferences = context.getSharedPreferences("SaveWeatherFiveDays", Context.MODE_PRIVATE);
        String saved = preferences.getString(name, "");
        String[] dane = saved.split(",");

        if (!saved.equals(""))
        {
            for (int i = 0; i < FOR_SIZE; i++)
            {
                dateFiveDays[i] = dane[0];
                temperatureFiveDays[i] = Integer.parseInt(dane[1]);
                descriptionFiveDays[i] = dane[2];
            }
        }
    }

    private void saveToPreferencesFiveDaysWeather()
    {
        if (dateFiveDays[0] != null)
        {
            SharedPreferences preferences = context.getSharedPreferences("SaveWeatherFiveDays", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            for (int i = 0; i < FOR_SIZE; i++)
            {
                editor.putString(name + i, dateFiveDays[i] + "," + temperatureFiveDays[i] + "," + descriptionFiveDays[i]);
            }
            editor.apply();
        }
    }

    public void deleteFromPreferencesFiveDaysWeather()
    {
        SharedPreferences preferences = context.getSharedPreferences("SaveWeatherFiveDays", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (int i = 0; i < FOR_SIZE; i++) editor.remove(name + i);
        editor.apply();
    }
}
