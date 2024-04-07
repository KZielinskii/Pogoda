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


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.pogoda.Class.Locality;
import com.example.pogoda.R;

import java.util.ArrayList;


public class AddLocalityWindow extends DialogFragment
{
    private final ArrayList<Locality> localities;
    private final Context context;

    public AddLocalityWindow(ArrayList<Locality> localities, Context context)
    {
        this.localities = localities;
        this.context = context;
    }

    @NonNull
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
                    else Toast.makeText(context, "Nie można dodać lokalizacji.\n(Sprawdź połączenie z internetem!)", Toast.LENGTH_SHORT).show();
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
}