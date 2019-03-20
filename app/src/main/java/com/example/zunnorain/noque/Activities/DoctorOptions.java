package com.example.zunnorain.noque.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zunnorain.noque.R;
import com.example.zunnorain.noque.Session.Session;

public class DoctorOptions extends AppCompatActivity implements View.OnClickListener {

    Session session;
    private TextView logout;
    private Button viewAppointment;
    private Button writePrescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_options);
        session = new Session(getApplicationContext());

        logout = (TextView) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.LogOutSession();
                startActivity(new Intent(DoctorOptions.this, DocPatSelection.class));
                finish();
            }
        });

        viewAppointment = (Button) findViewById(R.id.view_appointments);
        writePrescription = (Button) findViewById(R.id.write_prescription);

        viewAppointment.setOnClickListener(this);
        writePrescription.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.view_appointments) {
            startActivity(new Intent(DoctorOptions.this, ViewAppointments.class));
        }
        if (id == R.id.write_prescription) {
            startActivity(new Intent(DoctorOptions.this, PatientsList.class));
        }
    }
}
