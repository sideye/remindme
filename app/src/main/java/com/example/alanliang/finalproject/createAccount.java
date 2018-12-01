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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class createAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account2);
    }

    void create(View view) {
        EditText usernameInput = (EditText) findViewById(R.id.username);
        EditText passwordInput = (EditText) findViewById(R.id.password);
        EditText nameInput = (EditText) findViewById(R.id.name);
        EditText phoneInput = (EditText) findViewById(R.id.phone);
        final String username = usernameInput.getText().toString();
        final String password = passwordInput.getText().toString();
        final String name = nameInput.getText().toString();
        final String phone = phoneInput.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("http://54.67.72.192/create_account?username=%1$s&password=%2$s&name=%3$s&phone=%4$s", username, password, name, phone);
        System.out.println(url);

        JsonObjectRequest loginJSON = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            response.get("success");
                            proceed();
                        } catch (JSONException e) {
                            fail();
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

    void fail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(createAccount.this);
        builder.setMessage("Account creation failure");
        builder.setTitle("Failure");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void proceed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(createAccount.this);
        builder.setMessage("Account created successfully");
        builder.setTitle("Success");
        AlertDialog dialog = builder.create();
        dialog.show();
        Intent intent = new Intent(createAccount.this, Login.class);
        startActivity(intent);
    }
}
