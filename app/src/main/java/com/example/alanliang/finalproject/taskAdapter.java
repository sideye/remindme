package com.example.alanliang.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class taskAdapter extends ArrayAdapter<JSONObject>{
    taskAdapter (Context context, JSONObject[] tasks) {
        super(context, R.layout.task, tasks);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("GET VIEW");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.task, parent, false);
        JSONObject patient = getItem(position);
        TextView nameTV = (TextView) customView.findViewById(R.id.taskName);
        String name = "";
        try {
            name = patient.getString("name");
        } catch (JSONException e) {

        }
        nameTV.setText(name);
        return customView;
    }
}






