package com.example.pogoda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

        dayNameTextView.setText(day.getDayName().substring(0,10));
        dayTempTextView.setText(day.getDayTemp()+" ℃");
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

