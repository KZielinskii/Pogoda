package com.example.pogoda.Activity;

import static com.example.pogoda.Activity.MainActivity.localitiesListAdapter;
import static com.example.pogoda.Class.Locality.FOR_SIZE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pogoda.Fragment.CurrentWeatherFragment;
import com.example.pogoda.Fragment.DaysFragment;
import com.example.pogoda.Class.Locality;
import com.example.pogoda.R;
import com.example.pogoda.Adapter.ViewPagerAdapter;
import com.example.pogoda.Fragment.WindFragment;

public class WeatherForecastActivity extends AppCompatActivity{
    public static ViewPagerAdapter adapter;
    public static FragmentManager fragmentManager;
    public static Fragment currentWeatherFragment;
    public static Fragment windFragment;
    public static Fragment daysFragment;
    public static boolean isRotated;
    private ViewPager viewPager;
    private static String localityName;
    private static int itemIndex;
    private static int temperature;
    private static double latitude;
    private static double longitude;
    private static double pressure;
    private static String description;
    private static int visibilityInMeters;
    private static int humidity;
    private static int windSpeed;
    private static int windDegree;
    private static String[] dateFiveDays = new String[FOR_SIZE];
    private static int[] temperatureFiveDays = new int[FOR_SIZE];
    private static String[] descriptionFiveDays = new String[FOR_SIZE];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        isRotated= getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT;
        if(!isRotated)
        {
            setContentView(R.layout.activity_details);

            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new CurrentWeatherFragment(localityName, getSupportFragmentManager(), itemIndex, temperature, latitude, longitude, pressure, description));
            adapter.addFragment(new WindFragment(localityName, visibilityInMeters, humidity, windSpeed, windDegree));
            adapter.addFragment(new DaysFragment(localityName, dateFiveDays, temperatureFiveDays, descriptionFiveDays));

            viewPager = findViewById(R.id.viewPager);
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(2);
            viewPager.setCurrentItem(0);
        }
        else
        {
            setContentView(R.layout.activity_details_2);
            fragmentManager = getSupportFragmentManager();
            updateViewRotatedFragment();
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
            Toast.makeText(this, "Odświeżono dane dla miejscowości: "+localityName, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.celsius) {
            MainActivity.temperatureUnit = MainActivity.TemperatureUnit.CELSIUS;
            localitiesListAdapter.notifyDataSetChanged();
            updateViewFragments();
            Toast.makeText(this, "Zmieniono skale temperatur na: Celsius.", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.fahrenheit) {
            MainActivity.temperatureUnit = MainActivity.TemperatureUnit.FAHRENHEIT;
            localitiesListAdapter.notifyDataSetChanged();
            updateViewFragments();
            Toast.makeText(this, "Zmieniono skale temperatur na: Fahrenheit.", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.kelvin) {
            MainActivity.temperatureUnit = MainActivity.TemperatureUnit.KELVIN;
            localitiesListAdapter.notifyDataSetChanged();
            updateViewFragments();
            Toast.makeText(this, "Zmieniono skale temperatur na: Kelvin.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void updateViewRotatedFragment()
    {
        currentWeatherFragment = fragmentManager.findFragmentById(R.id.flFragment1);
        if (currentWeatherFragment != null) {
            fragmentManager.beginTransaction().remove(currentWeatherFragment).commit();
        }

        windFragment = fragmentManager.findFragmentById(R.id.flFragment2);
        if (windFragment != null) {
            fragmentManager.beginTransaction().remove(windFragment).commit();
        }

        daysFragment = fragmentManager.findFragmentById(R.id.flFragment3);
        if (daysFragment != null) {
            fragmentManager.beginTransaction().remove(daysFragment).commit();
        }

        currentWeatherFragment = new CurrentWeatherFragment(localityName, fragmentManager, itemIndex, temperature, latitude, longitude, pressure, description);
        windFragment = new WindFragment(localityName, visibilityInMeters, humidity, windSpeed, windDegree);
        daysFragment = new DaysFragment(localityName, dateFiveDays, temperatureFiveDays, descriptionFiveDays);
        if(!fragmentManager.isDestroyed()) fragmentManager.beginTransaction()
                .add(R.id.flFragment1, currentWeatherFragment)
                .add(R.id.flFragment2, windFragment)
                .add(R.id.flFragment3, daysFragment)
                .commit();
    }

    private void updateViewFragments() {

        isRotated= getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT;
        if(!isRotated)
        {
            int currentItem = viewPager.getCurrentItem();
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new CurrentWeatherFragment(localityName, getSupportFragmentManager(), itemIndex, temperature, latitude, longitude, pressure, description));
            adapter.addFragment(new WindFragment(localityName, visibilityInMeters, humidity, windSpeed, windDegree));
            adapter.addFragment(new DaysFragment(localityName, dateFiveDays, temperatureFiveDays, descriptionFiveDays));
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(currentItem);
        }
        else
        {
            setContentView(R.layout.activity_details_2);
            fragmentManager = getSupportFragmentManager();
            updateViewRotatedFragment();
        }
    }

    private void updateWeather()
    {
        Locality locality = new Locality(localityName, getApplicationContext());
        locality.updateWeather();
    }

    public static void refreshData(Locality locality)
    {
        temperature = locality.getCurrentTemperature();
        latitude = locality.getLatitude();
        longitude = locality.getLongitude();
        pressure = locality.getPressure();
        description = locality.getWeatherDescription();
        visibilityInMeters = locality.getVisibilityInMeters();
        humidity = locality.getHumidity();
        windSpeed = locality.getWindSpeed();
        windDegree = locality.getWindDegree();

        for(int i=0; i<FOR_SIZE; i++)
        {
            dateFiveDays[i] = locality.getDateFiveDays(i);
            temperatureFiveDays[i] = locality.getTemperatureFiveDays(i);
            descriptionFiveDays[i] = locality.getDescriptionFiveDays(i);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(viewPager!=null) outState.putInt("CURRENT_ITEM", viewPager.getCurrentItem());
        outState.putInt("ITEM_INDEX", itemIndex);
        outState.putInt("TEMPERATURE", temperature);
        outState.putDouble("LATITUDE", latitude);
        outState.putDouble("LONGITUDE", longitude);
        outState.putDouble("PRESSURE", pressure);
        outState.putString("DESCRIPTION", description);
        outState.putInt("VISIBILITY", visibilityInMeters);
        outState.putInt("HUMIDITY", humidity);
        outState.putInt("WIND_SPEED", windSpeed);
        outState.putInt("WIND_DEGREE", windDegree);
        outState.putStringArray("DATE_FIVE_DAYS", dateFiveDays);
        outState.putIntArray("TEMPERATURE_FIVE_DAYS", temperatureFiveDays);
        outState.putStringArray("DESCRIPTION_FIVE_DAYS", descriptionFiveDays);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        itemIndex = savedInstanceState.getInt("ITEM_INDEX");
        temperature = savedInstanceState.getInt("TEMPERATURE");
        latitude = savedInstanceState.getDouble("LATITUDE");
        longitude = savedInstanceState.getDouble("LONGITUDE");
        pressure = savedInstanceState.getDouble("PRESSURE");
        description = savedInstanceState.getString("DESCRIPTION");
        visibilityInMeters = savedInstanceState.getInt("VISIBILITY");
        humidity = savedInstanceState.getInt("HUMIDITY");
        windSpeed = savedInstanceState.getInt("WIND_SPEED");
        windDegree = savedInstanceState.getInt("WIND_DEGREE");
        dateFiveDays = savedInstanceState.getStringArray("DATE_FIVE_DAYS");
        temperatureFiveDays = savedInstanceState.getIntArray("TEMPERATURE_FIVE_DAYS");
        descriptionFiveDays = savedInstanceState.getStringArray("DESCRIPTION_FIVE_DAYS");
        if(viewPager!=null) viewPager.setCurrentItem(savedInstanceState.getInt("CURRENT_ITEM", 0));
        adapter.setFragment(0, new CurrentWeatherFragment(localityName, getSupportFragmentManager(), itemIndex, temperature, latitude, longitude, pressure, description));
        adapter.setFragment(1, new WindFragment(localityName, visibilityInMeters, humidity, windSpeed, windDegree));
        adapter.setFragment(2, new DaysFragment(localityName, dateFiveDays, temperatureFiveDays, descriptionFiveDays));
    }

}
