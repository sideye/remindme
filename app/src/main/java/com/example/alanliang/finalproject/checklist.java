package com.example.alanliang.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class checklist extends AppCompatActivity {
    String username;
    JSONObject patient;
    String patientName;
    JSONArray tasks;
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
            tasks = patient.getJSONArray("tasks");
        } catch (JSONException e) {

        }

        JSONObject[] taskArray = new JSONObject[tasks.length()];
        for (int i = 0; i < taskArray.length; i++) {
            try {
                taskArray[i] = (JSONObject) tasks.get(i);
            } catch (JSONException e) {

            }
        }
        ListAdapter adapter = new taskAdapter(this, taskArray);
        ListView patientListView = (ListView) findViewById(R.id.checklist);

        patientListView.setAdapter(adapter);
        patientListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        JSONObject task = (JSONObject) parent.getItemAtPosition(position);
                        click(task);
                    }
                }
        );
    }

    void click(JSONObject task) {
        Intent intent = new Intent(checklist.this, task.class);
        Bundle b = new Bundle();
        b.putString("user", checklist.this.username);
        b.putString("patient", patientName);
        b.putString("task", task.toString());
        intent.putExtras(b);
        startActivity(intent);
    }

    void create(View view) {
        Intent intent = new Intent(checklist.this, createTask.class);
        Bundle b = new Bundle();
        b.putString("user", checklist.this.username);
        b.putString("patient", checklist.this.patientName);
        intent.putExtras(b);
        startActivity(intent);
    }

    void info(View view) {
        Intent intent = new Intent(checklist.this, userProfile.class);
        Bundle b = new Bundle();
        b.putString("user", checklist.this.username);
        b.putString("patient", checklist.this.patient.toString());
        intent.putExtras(b);
        startActivity(intent);
    }

}
