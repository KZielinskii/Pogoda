package com.example.pogoda.Fragment;

import static com.example.pogoda.Class.Locality.FOR_SIZE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pogoda.Class.Day;
import com.example.pogoda.Adapter.DayListAdapter;
import com.example.pogoda.R;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("localityName", localityName);
        outState.putStringArray("dateFiveDays", dateFiveDays);
        outState.putIntArray("temperatureFiveDays", temperatureFiveDays);
        outState.putStringArray("descriptionFiveDays", descriptionFiveDays);
    }

}