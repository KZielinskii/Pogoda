package com.example.pogoda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Locality> localities=new ArrayList<>();
        localities.add(new Locality("Warszawa"));
        localities.add(new Locality("Łódź"));

        LocalitiesListAddapter localitiesListAddapter = new LocalitiesListAddapter(this, localities, getSupportFragmentManager());
        ListView listView = findViewById(R.id.localities_list);

        listView.setAdapter(localitiesListAddapter);
    }
}

