package com.example.alanliang.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class checklist extends AppCompatActivity {
    String username;
    JSONObject patient;
    String patientName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        Bundle b = getIntent().getExtras();
        try {
            this.patient = new JSONObject(b.getString("patient"));
            this.username = b.getString("user");
            TextView nameView = findViewById(R.id.name);
            String name = (String) ((JSONObject) patient.get("profile")).get("name");
            patientName = name;
            nameView.setText(patientName);
        } catch (JSONException e) {

        }

    }

    void create(View view) {
        Intent intent = new Intent(checklist.this, createTask.class);
        Bundle b = new Bundle();
        b.putString("user", checklist.this.username);
        b.putString("patient", checklist.this.patientName);
        intent.putExtras(b);
        startActivity(intent);
    }

}
