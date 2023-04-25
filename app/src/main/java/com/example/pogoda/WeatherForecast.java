package com.example.pogoda;

import static java.sql.Types.NULL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class WeatherForecast extends AppCompatActivity{

    private String localityName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        localityName = getIntent().getStringExtra("locality_name");
        int itemIndex = getIntent().getIntExtra("item_index", -1);
        int temperature = getIntent().getIntExtra("temperature",0);
        boolean isSunny = getIntent().getBooleanExtra("is_sunny",false);
        boolean isRaining = getIntent().getBooleanExtra("is_raining",false);

        Button button1 = findViewById(R.id.firstFragment);
        Button button2 = findViewById(R.id.secondFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FirstFragment firstFragment = new FirstFragment(localityName, itemIndex, temperature, isSunny, isRaining);
        fragmentTransaction.replace(R.id.flFragment, firstFragment);
        fragmentTransaction.commit();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FirstFragment firstFragment = new FirstFragment(localityName, itemIndex, temperature, isSunny, isRaining);
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


