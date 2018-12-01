package com.example.alanliang.finalproject;

import android.content.DialogInterface;
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

public class Login extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    void createAccount(View view) {
        Intent intent = new Intent(Login.this, createAccount.class);
        startActivity(intent);
    }

    void login(View view) {
        EditText usernameInput = (EditText) findViewById(R.id.username);
        EditText passwordInput = (EditText) findViewById(R.id.name);
        final String username = usernameInput.getText().toString();
        this.username = username;
        String password = passwordInput.getText().toString();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("http://54.67.72.192/login?username=%1$s&password=%2$s", username, password);
        System.out.println(url);

        JsonObjectRequest loginJSON = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            response.get("success");
                            downloadPatients(username);
                        } catch(JSONException error) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                            builder.setMessage("Incorrect username or password!");
                            builder.setTitle("Please try again");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            return;
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
        Intent intent = new Intent(Login.this, Main.class);
        Bundle b = new Bundle();
        b.putString("patients", patients.toString());
        b.putString("user", username);
        intent.putExtras(b);
        startActivity(intent);
    }
}
