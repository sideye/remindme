package com.example.alanliang.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class createTask extends AppCompatActivity {
    String username;
    String patientName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        Bundle b = getIntent().getExtras();
        username = b.getString("user");
        patientName = b.getString("patient");
    }


    void create (View view) {
        EditText taskNameInput = (EditText) findViewById(R.id.taskName);
        EditText dueDateInput = (EditText) findViewById(R.id.due);
        EditText notesInput = (EditText) findViewById(R.id.bio);

        final String taskName = taskNameInput.getText().toString();
        final String dueDate = dueDateInput.getText().toString();
        final String notes = notesInput.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("http://54.67.72.192/add_task" +
                        "?patient=%1$s&item_name=%2$s&due_date=%3$s&notes=%4$s&caretakers=%5$s",
                patientName, taskName, dueDate, notes, this.username);
        System.out.println(url);

        JsonObjectRequest loginJSON = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        downloadPatient();
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

    void downloadPatient() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("http://54.67.72.192/get_patient?patient=%s", patientName);
        System.out.println(url);

        JsonObjectRequest loginJSON = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        proceed(response);
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

    void proceed(JSONObject patient) {
        Intent intent = new Intent(createTask.this, checklist.class);
        Bundle b = new Bundle();
        b.putString("user", username);
        b.putString("patient", patient.toString());
        intent.putExtras(b);
        startActivity(intent);
    }
}
