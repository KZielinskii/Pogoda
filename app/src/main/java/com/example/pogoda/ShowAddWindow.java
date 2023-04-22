package com.example.pogoda;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class ShowAddWindow extends DialogFragment {
    private MainActivity mainActivity;
    private ArrayList<Locality> localities;
    private Context context;
    public ShowAddWindow(MainActivity mainActivity, ArrayList<Locality> localities, Context context) {
        this.mainActivity = mainActivity;
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
                    localities.add(new Locality(newLocality, context));
                })
                .setNegativeButton("Anuluj", (dialog, id) -> dialog.cancel());

        return builder.create();
    }
}
