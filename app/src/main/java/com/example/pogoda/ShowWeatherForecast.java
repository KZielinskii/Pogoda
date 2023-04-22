package com.example.pogoda;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

public class ShowWeatherForecast extends DialogFragment {
    private Locality locality;
    private int position;
    private LocalitiesListAddapter localitiesListAddapter;

    public ShowWeatherForecast(Locality locality, int position, LocalitiesListAddapter localitiesListAddapter) {
        this.locality = locality;
        this.position = position;
        this.localitiesListAddapter = localitiesListAddapter;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.weather_forecast_window, null);
        EditText city = ll.findViewById(R.id.editText);


        city.setText(String.valueOf(locality.getCurrentTemperature()));

        builder.setView(ll)
                .setPositiveButton("Wejdź", (dialog, id) -> {
                    String value = city.getText().toString();
                    (localitiesListAddapter.getLocalities().get(position)).getCurrentTemperature();
                })
                .setNegativeButton("Anuluj", (dialog, id) -> ShowWeatherForecast.this.getDialog().cancel());

        return builder.create();
    }
}