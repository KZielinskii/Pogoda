package com.example.pogoda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pogoda.Activity.MainActivity;
import com.example.pogoda.Class.Day;
import com.example.pogoda.R;

import java.util.List;

public class DayListAdapter extends ArrayAdapter<Day> {

    private Context context;
    private List<Day> days;

    public DayListAdapter(Context context, List<Day> days) {
        super(context, 0, days);
        this.context = context;
        this.days = days;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Day day = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.day_list_item, parent, false);
        }
        TextView dayNameTextView = convertView.findViewById(R.id.dayName);
        TextView dayTempTextView = convertView.findViewById(R.id.dayTemp);
        ImageView weatherIconImageView = convertView.findViewById(R.id.weather_icon);

        dayNameTextView.setText(day.getDayName().substring(0,16));

        if(MainActivity.temperatureUnit == MainActivity.TemperatureUnit.CELSIUS)
        {
            dayTempTextView.setText(day.getDayTemp()+" ℃");
        } else if (MainActivity.temperatureUnit == MainActivity.TemperatureUnit.KELVIN) {
            int temp = Integer.parseInt(day.getDayTemp());
            temp += 273.15;
            dayTempTextView.setText(temp +" K");
        } else if(MainActivity.temperatureUnit == MainActivity.TemperatureUnit.FAHRENHEIT) {
            int temp = Integer.parseInt(day.getDayTemp());
            temp = 2*temp+32;
            dayTempTextView.setText(temp +" °F");
        }

        updateIcon(weatherIconImageView, day);

        return convertView;
    }

    private void updateIcon(ImageView icon, Day day) {

        String description = day.getDescription();
        if(description!=null) {
            if (description.contains("clear")) icon.setImageResource(R.drawable.ic_sun);
            else if (description.contains("rain")) icon.setImageResource(R.drawable.ic_rain);
            else if (description.contains("clouds")) icon.setImageResource(R.drawable.ic_sun_cloud);
            else if (description.contains("overcast clouds")) icon.setImageResource(R.drawable.ic_cloud);
            else if (description.contains("snow")) icon.setImageResource(R.drawable.ic_snow);
            else if (description.contains("mist")) icon.setImageResource(R.drawable.ic_mist);
        }
    }
}

