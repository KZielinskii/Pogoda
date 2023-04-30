package com.example.pogoda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        for(int i=0; i<5; i++)
        {
            Day newDay = new Day(""+dateFiveDays[i], temperatureFiveDays[i], descriptionFiveDays[i]);
            adapter.add(newDay);
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