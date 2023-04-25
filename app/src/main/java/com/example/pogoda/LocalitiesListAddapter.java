package com.example.pogoda;

import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class LocalitiesListAddapter extends ArrayAdapter<Locality> {
    private static ArrayList<Locality> localities;
    private Context context;

    public LocalitiesListAddapter(Context context, ArrayList<Locality> localities) {
        super(context, 0, localities);
        this.localities = localities;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;

        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.locality_list_item, parent, false);
        }

        Locality locality = localities.get(position);

        TextView nameView = itemView.findViewById(R.id.paramName);
        TextView parmView = itemView.findViewById(R.id.paramValue);
        ImageView icon = itemView.findViewById(R.id.weather_icon);

        nameView.setText(locality.getName());
        parmView.setText(String.valueOf(locality.getCurrentTemperature()+" ℃"));
        updateIcon(icon, position);

        LinearLayout listItem = itemView.findViewById(R.id.list_item);
        listItem.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), WeatherForecast.class);
            intent.putExtra("item_index", position);
            intent.putExtra("locality_name", locality.getName());
            intent.putExtra("temperature", locality.getCurrentTemperature());
            intent.putExtra("is_sunny", locality.getIsSunny());
            intent.putExtra("is_raining", locality.getIsRaining());

            getContext().startActivity(intent);
        });

        return itemView;
    }

    private void updateIcon(ImageView icon, int index) {
        Locality locality = localities.get(index);
        if(locality.getIsSunny())icon.setImageResource(R.mipmap.ic_sun);
        else if(locality.getIsRaining())icon.setImageResource(R.mipmap.ic_rain);
        else icon.setImageResource(R.mipmap.ic_cloud);
    }
    public static ArrayList<Locality> getLocalities() {
        return localities;
    }
}


