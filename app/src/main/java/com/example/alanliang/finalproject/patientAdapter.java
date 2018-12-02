package com.example.alanliang.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class patientAdapter extends ArrayAdapter<JSONObject>{
    patientAdapter (Context context, JSONObject[] patients) {
        super(context, R.layout.patient, patients);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.patient, parent, false);
        JSONObject patient = getItem(position);
        TextView nameTV = (TextView) customView.findViewById(R.id.name);
        String name = "";
        try {
            name = patient.getJSONObject("profile").getString("name");
        } catch (JSONException e) {

        }
        nameTV.setText(name);
        return customView;
    }
}






