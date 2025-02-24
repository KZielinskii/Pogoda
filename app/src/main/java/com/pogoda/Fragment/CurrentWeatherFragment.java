package com.example.pogoda.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pogoda.Activity.MainActivity;
import com.example.pogoda.Adapter.LocalitiesListAdapter;
import com.example.pogoda.R;
import com.example.pogoda.Window.SubstractLocalityWindow;

public class CurrentWeatherFragment extends Fragment {

    private String localityName;
    private FragmentManager fragmentManager;
    private int index;
    private int temperature;
    private double latitude;
    private double longitude;
    private double pressure;
    private String description;

    public CurrentWeatherFragment() {}

    public CurrentWeatherFragment(String name, FragmentManager supportFragmentManager, int index, int temperature, double latitude, double longitude, double pressure, String description) {
        this.localityName = name;
        this.index = index;
        this.temperature = temperature;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pressure = pressure;
        this.description = description;
        this.fragmentManager = supportFragmentManager;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            localityName = savedInstanceState.getString("localityName");
            index = savedInstanceState.getInt("index");
            temperature = savedInstanceState.getInt("temperature");
            latitude = savedInstanceState.getDouble("latitude");
            longitude = savedInstanceState.getDouble("longitude");
            pressure = savedInstanceState.getDouble("pressure");
            description = savedInstanceState.getString("description");
        }

        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        TextView localityTv = view.findViewById(R.id.locality);
        TextView coordinatesTv = view.findViewById(R.id.coordinates);
        TextView temperatureTv = view.findViewById(R.id.temperature);
        TextView pressureTv = view.findViewById(R.id.pressure);
        TextView descriptionTv = view.findViewById(R.id.weather_description);
        ImageView weatherIconTv = view.findViewById(R.id.weather_icon);

        Button deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {

            SubstractLocalityWindow dialogFragment = new SubstractLocalityWindow(LocalitiesListAdapter.getLocalities(), index, getActivity());
            dialogFragment.show(fragmentManager, "show_sub_window_dialog");

        });

        localityTv.setText(this.localityName);
        coordinatesTv.setText(""+this.latitude+"°N   "+this.longitude+"°E");

        if(MainActivity.temperatureUnit == MainActivity.TemperatureUnit.CELSIUS)
        {
            temperatureTv.setText(this.temperature+" ℃");
        } else if (MainActivity.temperatureUnit == MainActivity.TemperatureUnit.KELVIN) {
            int temp = this.temperature;
            temp += 273.15;
            temperatureTv.setText(temp +" K");
        } else if(MainActivity.temperatureUnit == MainActivity.TemperatureUnit.FAHRENHEIT) {
            int temp = this.temperature;
            temp = 2*temp+32;
            temperatureTv.setText(temp +" °F");
        }

        pressureTv.setText(""+this.pressure + " hPa");

        String newDescription = "";

        if(description.contains("clear"))
        {
            weatherIconTv.setImageResource(R.drawable.ic_sun);
            newDescription += "Słonecznie\n";
        }
        else if(description.contains("thunderstorm"))
        {
            weatherIconTv.setImageResource(R.drawable.ic_rain);
            newDescription += "Burza\n";
        }
        else if(description.contains("rain"))
        {
            weatherIconTv.setImageResource(R.drawable.ic_rain);
            newDescription += "Pada\n";
        }
        else if(description.contains("overcast clouds"))
        {
            weatherIconTv.setImageResource(R.drawable.ic_cloud);
            newDescription += "Całkowite zachmurzenie\n";
        }
        else if(description.contains("clouds"))
        {
            weatherIconTv.setImageResource(R.drawable.ic_sun_cloud);
            newDescription += "Lekkie zachmurzenie\n";
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("localityName", localityName);
        outState.putInt("index", index);
        outState.putInt("temperature", temperature);
        outState.putDouble("latitude", latitude);
        outState.putDouble("longitude", longitude);
        outState.putDouble("pressure", pressure);
        outState.putString("description", description);
    }
}