package com.example.alanliang.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
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

import java.util.ArrayList;

public class Main extends AppCompatActivity {
    String username;
    JSONObject patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Main.this, createPatient.class);
//                Bundle b = new Bundle();
//                b.putString("user", Main.this.username);
//                intent.putExtras(b);
//                startActivity(intent);
//            }
//        });


    }

    public void onClick(View view) {
        Intent intent = new Intent(Main.this, createPatient.class);
        Bundle b = new Bundle();
        b.putString("user", Main.this.username);
        intent.putExtras(b);
        startActivity(intent);
    }

    void addExistingPatient(View view) {
        EditText patientNameInput = findViewById(R.id.addExisting);
        String patientName = patientNameInput.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("http://54.67.72.192/add_caretaker" +
                        "?user=%1$s&patient=%2$s",
                username, patientName);
        System.out.println(url);

        JsonObjectRequest loginJSON = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            response.get("success");
                            AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
                            builder.setMessage("Patient added successfully");
                            builder.setTitle("Success");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            downloadPatients(username);
                        } catch (JSONException e) {

                        }
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

    void launchList(View view) {
        Intent intent = new Intent(Main.this, checklist.class);
        Bundle b = new Bundle();
        b.putString("user", Main.this.username);
        b.putString("patient", patient.toString());
        intent.putExtras(b);
        startActivity(intent);
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
        Intent intent = new Intent(Main.this, Main.class);
        Bundle b = new Bundle();
        b.putString("patients", patients.toString());
        b.putString("user", username);
        intent.putExtras(b);
        startActivity(intent);
    }



}
