package com.example.alanliang.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main extends AppCompatActivity {
    String username;
    JSONObject patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        JSONArray patientList = null;
        try {
            patientList = new JSONArray(b.getString("patients"));
            this.username = b.getString("user");
        } catch (JSONException e) {

        }
        try {
            this.patient = (JSONObject) patientList.get(0);
        } catch (JSONException e) {

        }
        System.out.println(patientList);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, createPatient.class);
                Bundle b = new Bundle();
                b.putString("user", Main.this.username);
                intent.putExtras(b);
                startActivity(intent);
            }
        });


    }

    void launchList(View view) {
        Intent intent = new Intent(Main.this, checklist.class);
        Bundle b = new Bundle();
        b.putString("user", Main.this.username);
        b.putString("patient", patient.toString());
        intent.putExtras(b);
        startActivity(intent);
    }



}
