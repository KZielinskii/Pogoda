package com.example.pogoda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Locality> localities=new ArrayList<>();
        localities.add(new Locality("Warszawa",getApplicationContext()));
        localities.add(new Locality("Łódź",getApplicationContext()));
        localities.add(new Locality("Gdańsk",getApplicationContext()));


        LocalitiesListAddapter localitiesListAddapter = new LocalitiesListAddapter(this, localities, getSupportFragmentManager());
        ListView listView = findViewById(R.id.localities_list);
        listView.setAdapter(localitiesListAddapter);

        ImageButton addLocalities = findViewById(R.id.imageButton);

        addLocalities.setOnClickListener(view -> {
            ShowAddWindow dialogFragment = new ShowAddWindow(this, localities, getApplicationContext());
            dialogFragment.show(getSupportFragmentManager(), "show_add_window_dialog");
        });
    }
}

