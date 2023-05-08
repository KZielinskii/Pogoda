package com.example.pogoda.Window;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
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
                .setPositiveButton("Usuń", (dialog, id) -> {
                    LocalitiesListAdapter.getLocalities().get(index).deleteFromPreferencesWeather();
                    localities.remove(index);
                    MainActivity.localitiesListAddapter.notifyDataSetChanged();

                    if (activity != null) {
                        activity.finish();
                    }
                });

        return builder.create();
    }
}
