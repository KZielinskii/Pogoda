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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Day day = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.day_list_item, parent, false);
        }
        TextView dataNameTextView = convertView.findViewById(R.id.dataName);
        TextView hourNameTextView = convertView.findViewById(R.id.hourName);
        TextView dayTempTextView = convertView.findViewById(R.id.dayTemp);
        TextView dayDescription = convertView.findViewById(R.id.description);
        ImageView weatherIconImageView = convertView.findViewById(R.id.weather_icon);

        String dateView = "";
        String dateString = day.getDayName().substring(0, 16);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDate date = LocalDate.parse(dateString, formatter);
        LocalDate today = LocalDate.now();
        if (date.isEqual(today))
        {
            dateView = "Dziś: ";
        } else
        {
            String dayOfWeekString = date.getDayOfWeek().name();
            if(dayOfWeekString.equals("MONDAY")) dateView = "Poniedziałek: ";
            if(dayOfWeekString.equals("TUESDAY")) dateView = "Wtorek: ";
            if(dayOfWeekString.equals("WEDNESDAY")) dateView = "Środa: ";
            if(dayOfWeekString.equals("THURSDAY")) dateView = "Czwartek: ";
            if(dayOfWeekString.equals("FRIDAY")) dateView = "Piątek: ";
            if(dayOfWeekString.equals("SATURDAY")) dateView = "Sobota: ";
            if(dayOfWeekString.equals("SUNDAY")) dateView = "Niedziela: ";

        }
        hourNameTextView.setText(dateString.substring(11,16));
        dataNameTextView.setText(dateView);

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
        updateDescription(day, dayDescription);

        return convertView;
    }

    private void updateIcon(ImageView icon, Day day) {

        String description = day.getDescription();
        if(description!=null) {
            if (description.contains("clear")) icon.setImageResource(R.drawable.ic_sun);
            else if (description.contains("thunderstorm")) icon.setImageResource(R.drawable.ic_storm);
            else if (description.contains("rain")) icon.setImageResource(R.drawable.ic_rain);
            else if (description.contains("snow")) icon.setImageResource(R.drawable.ic_snow);
            else if (description.contains("mist")) icon.setImageResource(R.drawable.ic_mist);
            else if (description.contains("overcast clouds")) icon.setImageResource(R.drawable.ic_cloud);
            else if (description.contains("clouds")) icon.setImageResource(R.drawable.ic_sun_cloud);
        }
    }

    private void updateDescription(Day day, TextView dayDescription)
    {
        String description = day.getDescription();
        String newDescription = "";

        if(description.contains("clear"))
        {
            newDescription += "Słonecznie\n";
        }
        else if(description.contains("thunderstorm"))
        {
            newDescription += "Burza z deszczem\n";
        }
        else if(description.contains("rain"))
        {
            newDescription += "Pada deszcz\n";
        }
        else if(description.contains("overcast clouds"))
        {
            newDescription += "Całkowite zachmurzenie\n";
        }
        else if(description.contains("clouds"))
        {
            newDescription += "Lekkie zachmurzenie\n";
        }
        else if(description.contains("snow"))
        {
            newDescription += "Pada śnieg\n";
        }
        else if(description.contains("mist"))
        {
            newDescription += "Jest mgła\n";
        }
        dayDescription.setText(newDescription);
    }
}

