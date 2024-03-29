package com.example.pogoda.Window;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.fragment.app.DialogFragment;

import com.example.pogoda.Class.Locality;
import com.example.pogoda.R;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class AddLocalityWindow extends DialogFragment
{
    private ArrayList<Locality> localities;
    private Context context;

    public AddLocalityWindow(ArrayList<Locality> localities, Context context)
    {
        this.localities = localities;
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.add_location_window, null);
        EditText editText = ll.findViewById(R.id.editText);

        builder.setView(ll)
                .setPositiveButton("Dodaj", (dialog, id) -> {
                    String newLocality = editText.getText().toString();
                    newLocality = newLocality.trim();

                    ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    if(isConnected) localities.add(new Locality(newLocality, context));
                    else Toast.makeText(context, "Nie można dodać lokalizacji.\n (Sprawdź połączenie z internetem!)", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Anuluj", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();

        Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

        int buttonSize = getResources().getDimensionPixelSize(R.dimen.min_size);
        positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonSize);
        negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonSize);

        return dialog;
    }



    private boolean isValidLocality(String locality) {
        String start = "https://api.openweathermap.org/data/2.5/weather?q=";
        String end = "&appid=bc170ec055ab5eb458be802e3683e686&units=metric";
        String url = start + locality + end;

        try {
            URLConnection connection = new URL(url).openConnection();
            connection.connect();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}