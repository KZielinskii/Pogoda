package com.example.pogoda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class WindFragment extends Fragment {

    private String localityName;
    private int visibilityInMeters;
    private int humidity;
    private int windSpeed;
    private int windDegree;
    public WindFragment(String localityName, int visibilityInMeters, int humidity, int windSpeed, int windDegree) {
        this.localityName = localityName;
        this.visibilityInMeters = visibilityInMeters;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wind_humidity, container, false);

        TextView humidityTv = view.findViewById(R.id.humidity);
        TextView visibilityTv = view.findViewById(R.id.visibility);
        TextView windSpeedTv = view.findViewById(R.id.wind_speed);
        TextView windDegreeTv = view.findViewById(R.id.wind_deg);
        ImageView wind = view.findViewById(R.id.wind);

        humidityTv.setText(""+this.humidity+ "%");
        visibilityTv.setText(""+(this.visibilityInMeters/100)+ "%");
        windSpeedTv.setText(""+this.windSpeed+" m/s");
        windDegreeTv.setText(""+this.windDegree+"°");


        if(this.windDegree > 338 || this.windDegree <= 23)wind.setImageResource(R.drawable.ic_wind_338_23);
        if(this.windDegree > 23 && this.windDegree <= 68)wind.setImageResource(R.drawable.ic_wind_23_68);
        if(this.windDegree > 68 && this.windDegree <= 113)wind.setImageResource(R.drawable.ic_wind_68_113);
        if(this.windDegree > 113 && this.windDegree <= 158)wind.setImageResource(R.drawable.ic_wind_113_158);
        if(this.windDegree > 158 && this.windDegree <= 203)wind.setImageResource(R.drawable.ic_wind_158_203);
        if(this.windDegree > 203 && this.windDegree <= 248)wind.setImageResource(R.drawable.ic_wind_203_248);
        if(this.windDegree > 248 && this.windDegree <= 293)wind.setImageResource(R.drawable.ic_wind_248_293);
        if(this.windDegree > 293 && this.windDegree <= 338)wind.setImageResource(R.drawable.ic_wind_293_338);

        return view;
    }
}