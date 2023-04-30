package com.example.pogoda.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.pogoda.Activity.WeatherForecastActivity;
import com.example.pogoda.Class.Locality;
import com.example.pogoda.R;

import java.util.ArrayList;

public class LocalitiesListAdapter extends ArrayAdapter<Locality> {
    private static ArrayList<Locality> localities;
    private Context context;

    public LocalitiesListAdapter(Context context, ArrayList<Locality> localities) {
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
            Intent intent = new Intent(getContext(), WeatherForecastActivity.class);
            intent.putExtra("item_index", position);
            intent.putExtra("locality_name", locality.getName());
            intent.putExtra("temperature", locality.getCurrentTemperature());
            intent.putExtra("is_sunny", locality.getIsSunny());
            intent.putExtra("is_raining", locality.getIsRaining());
            intent.putExtra("latitude", locality.getLatitude());
            intent.putExtra("longitude", locality.getLongitude());
            intent.putExtra("pressure", locality.getPressure());
            intent.putExtra("description", locality.getWeatherDescription());
            intent.putExtra("visibility",locality.getVisibilityInMeters());
            intent.putExtra("humidity",locality.getHumidity());
            intent.putExtra("wind_speed",locality.getWindSpeed());
            intent.putExtra("wind_deg",locality.getWindDegree());
            for(int i=0; i<Locality.FOR_SIZE; i++)
            {
                intent.putExtra("five_days_data_"+i,locality.getDateFiveDays(i));
                intent.putExtra("five_days_temperature_"+i,locality.getTemperatureFiveDays(i));
                intent.putExtra("five_days_description_"+i,locality.getDescriptionFiveDays(i));
            }

            getContext().startActivity(intent);
        });

        return itemView;
    }

    private void updateIcon(ImageView icon, int index) {
        Locality locality = localities.get(index);
        String description = locality.getWeatherDescription();
        if(description!=null) {
            if (description.contains("clear")) icon.setImageResource(R.drawable.ic_sun);
            else if (description.contains("rain")) icon.setImageResource(R.drawable.ic_rain);
            else if (description.contains("clouds")) icon.setImageResource(R.drawable.ic_sun_cloud);
            else if (description.contains("overcast clouds")) icon.setImageResource(R.drawable.ic_cloud);
            else if (description.contains("snow")) icon.setImageResource(R.drawable.ic_snow);
            else if (description.contains("mist")) icon.setImageResource(R.drawable.ic_mist);
        }
    }
    public static ArrayList<Locality> getLocalities() {
        return localities;
    }
}


