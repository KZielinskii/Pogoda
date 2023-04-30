package com.example.pogoda.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pogoda.Window.AddLocalityWindow;
import com.example.pogoda.Adapter.LocalitiesListAdapter;
import com.example.pogoda.Class.Locality;
import com.example.pogoda.R;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private static ArrayList<Locality> localities;
    public static LocalitiesListAdapter localitiesListAddapter;
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.localities_list);

        localities = new ArrayList<>();
        addSavedLocalities();

        localitiesListAddapter = new LocalitiesListAdapter(this, localities);
        ListView listView = findViewById(R.id.localities_list);
        listView.setAdapter(localitiesListAddapter);

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
            Toast.makeText(this, "Odświeżono dane.", Toast.LENGTH_SHORT).show();
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
        localitiesListAddapter.notifyDataSetChanged();
    }

    private void updateIcon(ImageView icon, int index) {
        Locality locality = localities.get(index);
        if(locality.getIsSunny())icon.setImageResource(R.mipmap.ic_sun);
        else if(locality.getIsRaining())icon.setImageResource(R.mipmap.ic_rain);
        else icon.setImageResource(R.mipmap.ic_cloud);
    }

    private void updateTemperature(TextView textView, int index)
    {
        Locality locality = localities.get(index);
        textView.setText(locality.getCurrentTemperature()+" ℃");
    }
}

