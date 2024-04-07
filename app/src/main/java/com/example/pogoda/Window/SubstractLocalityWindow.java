package com.example.pogoda.Window;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.pogoda.Activity.MainActivity;
import com.example.pogoda.Adapter.LocalitiesListAdapter;
import com.example.pogoda.Class.Locality;
import com.example.pogoda.R;

import java.util.ArrayList;

public class SubstractLocalityWindow extends DialogFragment
{
    private ArrayList<Locality> localities;
    private int index;
    private FragmentActivity activity;
    public SubstractLocalityWindow(ArrayList<Locality> localities, int index, FragmentActivity activity)
    {
        this.localities = localities;
        this.index = index;
        this.activity = activity;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.sub_location_window, null);
        TextView textView = ll.findViewById(R.id.editText);
        textView.setText(localities.get(index).getName());

        builder.setView(ll)
                .setNegativeButton("Anuluj", (dialog, id) -> dialog.cancel())
                .setPositiveButton("Usuń", (dialog, id) ->
                {
                    LocalitiesListAdapter.getLocalities().get(index).deleteFromPreferencesWeather();
                    localities.remove(index);
                    MainActivity.localitiesListAdapter.notifyDataSetChanged();

                    if (activity != null)
                    {
                        activity.finish();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
        Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

        int buttonSize = getResources().getDimensionPixelSize(R.dimen.min_size);
        positiveButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonSize);
        negativeButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonSize);

        int paddingInPixels = 42;
        positiveButton.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);
        negativeButton.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);

        return dialog;
    }
}
