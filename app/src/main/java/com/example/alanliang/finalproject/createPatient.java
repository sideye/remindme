package com.example.alanliang.finalproject;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

public class createPatient extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);

        Bundle b = getIntent().getExtras();
        this.username = b.getString("user");
    }

    void create(View view) {
        EditText nameInput = (EditText) findViewById(R.id.name);
        EditText birthdayInput = (EditText) findViewById(R.id.birthday);
        EditText genderInput = (EditText) findViewById(R.id.gender);
        EditText eNameINput = (EditText) findViewById(R.id.emergencyName);
        EditText ePhoneInput = (EditText) findViewById(R.id.emergencyPhone);
        EditText bioInput = (EditText) findViewById(R.id.bio);

        final String name = nameInput.getText().toString();
        final String birthday = birthdayInput.getText().toString();
        final String gender = genderInput.getText().toString();
        final String eName = eNameINput.getText().toString();
        final String ePhone = ePhoneInput.getText().toString();
        final String bio = bioInput.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("http://54.67.72.192/add_patient" +
                "?name=%1$s&birthday=%2$s&gender=%3$s&emergencyName=%4$s&emergencyPhone=%5$s&bio=%6$s&user=%7$s",
                name, birthday, gender, eName, ePhone, bio, this.username);
        System.out.println(url);

        JsonObjectRequest loginJSON = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        downloadPatients(createPatient.this.username);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(createPatient.this);
        builder.setMessage("Patient created successfully");
        builder.setTitle("Success");
        AlertDialog dialog = builder.create();

        dialog.show();

        Intent intent = new Intent(createPatient.this, Main.class);
        Bundle b = new Bundle();
        b.putString("patients", patients.toString());
        b.putString("user", username);
        intent.putExtras(b);
        startActivity(intent);
    }
}
