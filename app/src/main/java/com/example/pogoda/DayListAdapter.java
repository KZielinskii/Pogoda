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

        dayNameTextView.setText(day.getDayName());
        dayTempTextView.setText(day.getDayTemp());

        return convertView;
    }
}

