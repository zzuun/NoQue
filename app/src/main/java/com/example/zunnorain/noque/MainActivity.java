package com.example.zunnorain.noque;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zunnorain.noque.Activities.DocPatSelection;
import com.example.zunnorain.noque.Activities.DoctorOptions;
import com.example.zunnorain.noque.Activities.PatientOptions;
import com.example.zunnorain.noque.Session.Session;

public class MainActivity extends AppCompatActivity {

    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new Session(getApplicationContext());
        if (session.isLoggedIn()) {
            String token = session.getKeyToken();
            String type = session.getKeyName();
            if (session.getKeyType().equals("patient")) {
                String s = session.getKeyToken();
                startActivity(new Intent(MainActivity.this, PatientOptions.class));
            } else if (session.getKeyType().equals("doctor")) {
                startActivity(new Intent(MainActivity.this, DoctorOptions.class));

            }
        } else
            startActivity(new Intent(MainActivity.this, DocPatSelection.class));

        finish();
    }
}
