package com.example.alanliang.finalproject;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class task extends AppCompatActivity {
    JSONObject task;
    String taskname;
    String patient;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Bundle b = getIntent().getExtras();
        TextView taskNameTV = findViewById(R.id.taskName);
        TextView dueDateTV = findViewById(R.id.dueDate);
        TextView notesTV = findViewById(R.id.notes);


        try {
            patient = b.getString("patient");
            username = b.getString("user");
            task = new JSONObject(b.getString("task"));
            taskNameTV.setText(task.getString("name"));
            taskname = task.getString("name");
            dueDateTV.setText(task.getString("due"));
            notesTV.setText(task.getString("notes"));
        } catch (JSONException e) {

        }


    }

    void complete(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("http://54.67.72.192/delete_task" +
                        "?patient=%1$s&item_name=%2$s",
                patient, taskname);
        System.out.println(url);

        JsonObjectRequest loginJSON = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        downloadPatients(username);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(loginJSON);
    }

    void downloadPatients(String username) {
        System.out.println("Downloading patients");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("http://54.67.72.192/get_user_patients?user=%s", username);
        JsonArrayRequest patientJSON = new JsonArrayRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Display the first 500 characters of the response string.
                        proceed(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        queue.add(patientJSON);
    }

    void proceed(JSONArray patients) {
        JSONObject actual_patient = null;
        for (int i = 0; i < patients.length(); i++) {
            try {
                String name = patients.getJSONObject(i).getJSONObject("profile").getString("name");
                System.out.println(name + " " + patient);
                if (name.equals(patient)) {
                    actual_patient = patients.getJSONObject(i);
                    System.out.println("Patient set correctly");
                }
            } catch (JSONException e) {

            }
        }
        Intent intent = new Intent(task.this, checklist.class);
        Bundle b = new Bundle();
        b.putString("patient", actual_patient.toString());
        b.putString("user", username);
        intent.putExtras(b);
        startActivity(intent);
    }

}
