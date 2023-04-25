package com.example.pogoda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WeatherForecast extends AppCompatActivity{

    private String localityName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        localityName = getIntent().getStringExtra("locality_name");
        int itemIndex = getIntent().getIntExtra("item_index", -1);
        int temperature = getIntent().getIntExtra("temperature",0);
        double latitude = getIntent().getDoubleExtra("latitude", -1);
        double longitude = getIntent().getDoubleExtra("longitude", -1);
        double pressure = getIntent().getDoubleExtra("pressure", -1);
        String description = getIntent().getStringExtra("description");

        Button button1 = findViewById(R.id.firstFragment);
        Button button2 = findViewById(R.id.secondFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FirstFragment firstFragment = new FirstFragment(localityName, itemIndex, temperature, latitude, longitude, pressure, description);
        fragmentTransaction.replace(R.id.flFragment, firstFragment);
        fragmentTransaction.commit();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FirstFragment firstFragment = new FirstFragment(localityName, itemIndex, temperature, latitude, longitude, pressure, description);
                fragmentTransaction.replace(R.id.flFragment, firstFragment);
                fragmentTransaction.commit();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SecondFragment secondFragment = new SecondFragment(localityName);
                fragmentTransaction.replace(R.id.flFragment, secondFragment);
                fragmentTransaction.commit();
            }
        });
    }
}


