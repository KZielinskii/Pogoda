package com.pogoda.pogoda.Fragment;

import static com.pogoda.pogoda.Class.Locality.FOR_SIZE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pogoda.pogoda.Class.Day;
import com.pogoda.pogoda.Adapter.DayListAdapter;
import com.pogoda.pogoda.R;

import java.util.ArrayList;

public class DaysFragment extends Fragment {

    private String localityName;
    private String[] dateFiveDays;
    private int[] temperatureFiveDays;
    private String[] descriptionFiveDays;
    public DaysFragment(){}
    public DaysFragment(String localityName, String[] dateFiveDays, int[] temperatureFiveDays, String[] descriptionFiveDays)
    {
        this.localityName = localityName;
        this.dateFiveDays = dateFiveDays;
        this.temperatureFiveDays = temperatureFiveDays;
        this.descriptionFiveDays = descriptionFiveDays;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            localityName = savedInstanceState.getString("localityName");
            dateFiveDays = savedInstanceState.getStringArray("dateFiveDays");
            temperatureFiveDays = savedInstanceState.getIntArray("temperatureFiveDays");
            descriptionFiveDays = savedInstanceState.getStringArray("descriptionFiveDays");
        }

        View view = inflater.inflate(R.layout.fragment_upcoming_days, container, false);
        TextView name = view.findViewById(R.id.locality);
        name.setText(this.localityName);

        ArrayList<Day> array = new ArrayList<>();

        ListView listView = view.findViewById(R.id.days_list);
        DayListAdapter adapter = new DayListAdapter(getContext(), array);
        listView.setAdapter(adapter);

        for(int i=0; i<FOR_SIZE; i++)
        {
            if(dateFiveDays[i]!=null)
            {
                Day newDay = new Day(""+dateFiveDays[i], temperatureFiveDays[i], descriptionFiveDays[i]);
                adapter.add(newDay);
            }
            else return view;
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("localityName", localityName);
        outState.putStringArray("dateFiveDays", dateFiveDays);
        outState.putIntArray("temperatureFiveDays", temperatureFiveDays);
        outState.putStringArray("descriptionFiveDays", descriptionFiveDays);
    }

}