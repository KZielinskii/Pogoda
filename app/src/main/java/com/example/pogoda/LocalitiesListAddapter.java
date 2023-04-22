package com.example.pogoda;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class LocalitiesListAddapter extends ArrayAdapter<Locality> {
    private ArrayList<Locality> localities;
    private FragmentManager fragmentManager;

    public LocalitiesListAddapter(Context context, ArrayList<Locality> localities, FragmentManager fragmentManager) {
        super(context, 0, localities);
        this.localities = localities;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;

        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.locality_list_item, parent, false);
        }

        Locality locality = getItem(position);
        TextView nameView = itemView.findViewById(R.id.paramName);
        TextView parmView = itemView.findViewById(R.id.paramValue);
        nameView.setText(locality.getName());
        parmView.setText(String.valueOf(locality.getCurrentTemperature()));

        LinearLayout listItem = itemView.findViewById(R.id.list_item);
        listItem.setOnClickListener(view -> {
            ShowWeatherForecast dialogFragment = new ShowWeatherForecast(locality, position, this);
            dialogFragment.show(fragmentManager, "show_weather_forecast_dialog");
        });

        return itemView;

    }

    public ArrayList<Locality> getLocalities() {
        return localities;
    }

}
