package com.example.pogoda;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private String localityName;
    private int index;
    private int temperature;
    private boolean isSunny;
    private boolean isRaining;

    public FirstFragment(String name, int index, int temperature, boolean isSunny, boolean isRaining) {
        this.localityName = name;
        this.index = index;
        this.temperature = temperature;
        this.isRaining = isRaining;
        this.isSunny = isSunny;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        TextView localityTv = view.findViewById(R.id.locality);
        TextView coordinatesTv = view.findViewById(R.id.coordinates);
        TextView temperatureTv = view.findViewById(R.id.temperature);
        TextView pressureTv = view.findViewById(R.id.pressure);
        ImageView weatherIconTv = view.findViewById(R.id.weather_icon);

        Button deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Locality> localities = LocalitiesListAddapter.getLocalities();
                LocalitiesListAddapter.getLocalities().get(index).deleteFromPreferences();
                localities.remove(index);

                Activity activity = getActivity();
                if (activity != null) {
                    activity.finish();
                }
            }
        });

        localityTv.setText(this.localityName);
        temperatureTv.setText(""+this.temperature+" ℃");

        if(isSunny)weatherIconTv.setImageResource(R.drawable.ic_sun);
        else if(isRaining)weatherIconTv.setImageResource(R.drawable.ic_rain);
        else weatherIconTv.setImageResource(R.drawable.ic_cloud);


        return view;
    }

}