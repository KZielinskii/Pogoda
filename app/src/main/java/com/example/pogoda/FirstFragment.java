package com.example.pogoda;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstFragment extends Fragment {

    private String localityName;

    public FirstFragment(String name) {
        this.localityName = name;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        TextView locality = view.findViewById(R.id.locality);
        TextView coordinates = view.findViewById(R.id.coordinates);
        TextView temperature = view.findViewById(R.id.temperature);
        TextView pressure = view.findViewById(R.id.pressure);
        ImageView weatherIcon = view.findViewById(R.id.weather_icon);

        locality.setText(localityName);


        return view;
    }
}