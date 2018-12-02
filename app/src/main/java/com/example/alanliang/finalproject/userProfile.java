package com.example.alanliang.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class userProfile extends AppCompatActivity {
    JSONObject patient;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Bundle b = getIntent().getExtras();
        try {
            patient = new JSONObject(b.getString("patient"));
            this.username = b.getString("user");
            JSONObject profile = patient.getJSONObject("profile");
            String name = profile.getString("name");
            String birthday = profile.getString("birthday");
            String gender = profile.getString("gender");
            String eName = profile.getString("emergencyName");
            String ePhone = profile.getString("emergencyPhone");
            String bio = profile.getString("bio");

            TextView nameText = findViewById(R.id.Name);
            TextView birthdayText = findViewById(R.id.birthday);
            TextView genderText = findViewById(R.id.gender);
            TextView eNameText = findViewById(R.id.eName);
            TextView ePhoneText = findViewById(R.id.ePhone);
            TextView bioText = findViewById(R.id.bio);
            nameText.setText(name);
            birthdayText.setText(birthday);
            genderText.setText(gender);
            eNameText.setText(eName);
            ePhoneText.setText(ePhone);
            bioText.setText(bio);
        } catch (JSONException e) {
            System.out.println(e);
        }

    }
}
