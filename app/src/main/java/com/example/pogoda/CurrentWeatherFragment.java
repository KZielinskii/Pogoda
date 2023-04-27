package com.example.pogoda;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CurrentWeatherFragment extends Fragment {

    private String localityName;
    private int index;
    private int temperature;
    private double latitude;
    private double longitude;
    private double pressure;
    private String description;

    public CurrentWeatherFragment(String name, int index, int temperature, double latitude, double longitude, double pressure, String description) {
        this.localityName = name;
        this.index = index;
        this.temperature = temperature;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pressure = pressure;
        this.description = description;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        TextView localityTv = view.findViewById(R.id.locality);
        TextView coordinatesTv = view.findViewById(R.id.coordinates);
        TextView temperatureTv = view.findViewById(R.id.temperature);
        TextView pressureTv = view.findViewById(R.id.pressure);
        TextView descriptionTv = view.findViewById(R.id.weather_description);
        ImageView weatherIconTv = view.findViewById(R.id.weather_icon);

        Button deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Locality> localities = LocalitiesListAddapter.getLocalities();
                LocalitiesListAddapter.getLocalities().get(index).deleteFromPreferencesWeather();
                localities.remove(index);

                MainActivity.localitiesListAddapter.notifyDataSetChanged();

                Activity activity = getActivity();
                if (activity != null) {
                    activity.finish();
                }
            }
        });

        localityTv.setText(this.localityName);
        coordinatesTv.setText(""+this.latitude+"°N   "+this.longitude+"°E");
        temperatureTv.setText(""+this.temperature+" ℃");
        pressureTv.setText(""+this.pressure + " hPa");

        String newDescription = "";

        if(description.contains("clear"))
        {
            weatherIconTv.setImageResource(R.drawable.ic_sun);
            newDescription += "Słonecznie\n";
        }
        else if(description.contains("rain"))
        {
            weatherIconTv.setImageResource(R.drawable.ic_rain);
            newDescription += "Pada\n";
        }
        else if(description.contains("clouds"))
        {
            weatherIconTv.setImageResource(R.drawable.ic_sun_cloud);
            newDescription += "Lekkie zachmurzenie\n";
        }
        else if(description.contains("overcast clouds"))
        {
            weatherIconTv.setImageResource(R.drawable.ic_cloud);
            newDescription += "Całkowite zachmurzenie\n";
        }
        else if(description.contains("snow"))
        {
            weatherIconTv.setImageResource(R.drawable.ic_snow);
            newDescription += "Śnieżnie\n";
        }
        else if(description.contains("mist"))
        {
            weatherIconTv.setImageResource(R.drawable.ic_mist);
            newDescription += "Mgła\n";
        }

        descriptionTv.setText(newDescription);

        return view;
    }

}