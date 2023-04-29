package com.example.pogoda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherForecastActivity extends AppCompatActivity{
    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    private final static int FOR_SIZE = 5;
    private String localityName;
    private int itemIndex;
    private int temperature;
    private double latitude;
    private double longitude;
    private double pressure;
    private String description;
    private int visibilityInMeters;
    private int humidity;
    private int windSpeed;
    private int windDegree;
    private String[] dateFiveDays = new String[FOR_SIZE];
    private int[] temperatureFiveDays = new int[FOR_SIZE];
    private String[] descriptionFiveDays = new String[FOR_SIZE];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        localityName = getIntent().getStringExtra("locality_name");
        itemIndex = getIntent().getIntExtra("item_index", -1);
        temperature = getIntent().getIntExtra("temperature",0);
        latitude = getIntent().getDoubleExtra("latitude", -1);
        longitude = getIntent().getDoubleExtra("longitude", -1);
        pressure = getIntent().getDoubleExtra("pressure", -1);
        description = getIntent().getStringExtra("description");
        visibilityInMeters = getIntent().getIntExtra("visibility", -1);
        humidity = getIntent().getIntExtra("humidity", -1);
        windSpeed = getIntent().getIntExtra("wind_speed", -1);
        windDegree = getIntent().getIntExtra("wind_deg", -1);

        for(int i=0; i<FOR_SIZE; i++)
        {
            dateFiveDays[i] = getIntent().getStringExtra("five_days_data_"+i);
            temperatureFiveDays[i] = getIntent().getIntExtra("five_days_temperature_"+i,0);
            descriptionFiveDays[i] = getIntent().getStringExtra("five_days_description_"+i);
        }

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CurrentWeatherFragment(localityName, getSupportFragmentManager(), itemIndex, temperature, latitude, longitude, pressure, description));
        adapter.addFragment(new WindFragment(localityName, visibilityInMeters, humidity, windSpeed, windDegree));
        adapter.addFragment(new DaysFragment(localityName, dateFiveDays, temperatureFiveDays, descriptionFiveDays));

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
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
            Toast.makeText(this, "Odświeżono dane dla miejscowości: "+localityName, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateWeather()
    {
        Locality locality = new Locality(localityName, getApplicationContext());
        locality.updateWeather();
        //todo nie aktualizuje sie
    }


}


