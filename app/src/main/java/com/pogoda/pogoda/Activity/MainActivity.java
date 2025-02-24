package com.pogoda.pogoda.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pogoda.pogoda.Adapter.LocalitiesListAdapter;
import com.pogoda.pogoda.Class.Locality;
import com.pogoda.pogoda.R;
import com.pogoda.pogoda.Window.AddLocalityWindow;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity{
    public enum TemperatureUnit {
        CELSIUS,
        KELVIN,
        FAHRENHEIT
    }
    public static TemperatureUnit temperatureUnit;
    private static ArrayList<Locality> localities;
    @SuppressLint("StaticFieldLeak")
    public static LocalitiesListAdapter localitiesListAdapter;
    private ListView listView;
    private Timer timer;
    public static final int DATA_REFRESH_DELAY = 60 * 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.localities_list);

        temperatureUnit = TemperatureUnit.CELSIUS;
        localities = new ArrayList<>();
        addSavedLocalities();

        localitiesListAdapter = new LocalitiesListAdapter(this, localities);
        ListView listView = findViewById(R.id.localities_list);
        listView.setAdapter(localitiesListAdapter);

        ImageButton addLocalities = findViewById(R.id.imageButton);

        addLocalities.setOnClickListener(view -> {
            AddLocalityWindow dialogFragment = new AddLocalityWindow(localities, getApplicationContext());
            dialogFragment.show(getSupportFragmentManager(), "show_add_window_dialog");
        });
    }

    private void addSavedLocalities()
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("SaveWeather", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = preferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            localities.add(new Locality( entry.getKey(), getApplicationContext()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateWeather();
            return true;
        }
        if (id == R.id.celsius) {
            temperatureUnit = TemperatureUnit.CELSIUS;
            localitiesListAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Zmieniono skale temperatur na: Celsius.", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.fahrenheit) {
            temperatureUnit = TemperatureUnit.FAHRENHEIT;
            localitiesListAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Zmieniono skale temperatur na: Fahrenheit.", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.kelvin) {
            temperatureUnit = TemperatureUnit.KELVIN;
            localitiesListAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Zmieniono skale temperatur na: Kelvin.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateWeather()
    {
        localities = LocalitiesListAdapter.getLocalities();
        for (int i = 0; i < localities.size(); i++) {
            localities.get(i).updateWeather();
            View itemView = listView.getChildAt(i);
            TextView temp =  itemView.findViewById(R.id.paramValue);
            ImageView icon = itemView.findViewById(R.id.weather_icon);
            updateTemperature(temp, i);
            updateIcon(icon,i);
        }
        localitiesListAdapter.notifyDataSetChanged();
    }

    private void updateIcon(ImageView icon, int index) {
        Locality locality = localities.get(index);

        String description = locality.getWeatherDescription();
        if(description!=null) {
            if (description.contains("clear")) icon.setImageResource(R.drawable.ic_sun);
            else if (description.contains("thunderstorm")) icon.setImageResource(R.drawable.ic_storm);
            else if (description.contains("rain")) icon.setImageResource(R.drawable.ic_rain);
            else if (description.contains("snow")) icon.setImageResource(R.drawable.ic_snow);
            else if (description.contains("mist")) icon.setImageResource(R.drawable.ic_mist);
            else if (description.contains("overcast clouds")) icon.setImageResource(R.drawable.ic_cloud);
            else if (description.contains("clouds")) icon.setImageResource(R.drawable.ic_sun_cloud);
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateTemperature(TextView textView, int index)
    {
        Locality locality = localities.get(index);
        if(temperatureUnit == TemperatureUnit.CELSIUS)
        {
            textView.setText(locality.getCurrentTemperature()+" ℃");
        } else if (temperatureUnit == TemperatureUnit.KELVIN) {
            int temp = locality.getCurrentTemperature();
            temp += 273.15;
            textView.setText(temp +" K");
        } else if(temperatureUnit == TemperatureUnit.FAHRENHEIT) {
            int temp = locality.getCurrentTemperature();
            temp = 2*temp+32;
            textView.setText(temp +" °F");
        }
    }

    private void startAutoRefresh() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Handler handler = new Handler(getMainLooper());
                handler.post(() -> updateWeather());
            }
        };

        timer.schedule(task, DATA_REFRESH_DELAY, DATA_REFRESH_DELAY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoRefresh();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}

