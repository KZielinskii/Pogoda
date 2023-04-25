package com.example.pogoda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private ArrayList<Locality> localities;
    private LocalitiesListAddapter localitiesListAddapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localities = new ArrayList<>();
        addSavedLocalities();

        localitiesListAddapter = new LocalitiesListAddapter(this, localities, getSupportFragmentManager());
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
            Toast.makeText(this, "Odświeżono ekran.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateWeather()
    {
        ListView listView = findViewById(R.id.localities_list);

        for (int i = 0; i < localities.size(); i++) {
            localities.get(i).updateWeather();
            View itemView = listView.getChildAt(i);
            TextView temp =  itemView.findViewById(R.id.paramValue);

            updateTemperature(temp, i);
        }
    }

    private void updateTemperature(TextView textView, int index)
    {
        Locality locality = localities.get(index);
        double temp = locality.getCurrentTemperature();
        textView.setText(""+temp);
    }
}

