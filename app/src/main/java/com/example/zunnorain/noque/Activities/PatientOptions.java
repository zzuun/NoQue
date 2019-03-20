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

public class PatientOptions extends AppCompatActivity implements View.OnClickListener {


    private Button makeAppointment, reschedule, viewPrescription, contactUs;
    Session session;
    private TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_options);
        session= new Session(getApplicationContext());
        makeAppointment = (Button) findViewById(R.id.make_appointment_button);
        reschedule = (Button) findViewById(R.id.reschedule_button);
        viewPrescription = (Button) findViewById(R.id.view_prescription_button);
        contactUs = (Button) findViewById(R.id.contact_us_button);
        logout= (TextView)findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.LogOutSession();
                startActivity(new Intent(PatientOptions.this,DocPatSelection.class));
                finish();
            }
        });

        makeAppointment.setOnClickListener(this);
        reschedule.setOnClickListener(this);
        viewPrescription.setOnClickListener(this);
        contactUs.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.make_appointment_button) {
            startActivity(new Intent(PatientOptions.this, DoctorsList.class));
        }
        if (id == R.id.reschedule_button) {
            startActivity(new Intent(PatientOptions.this, RescheduleAppointment.class));
        }
        if (id == R.id.view_prescription_button) {
            startActivity(new Intent(PatientOptions.this, ViewPrescription.class));
        }
        if (id == R.id.contact_us_button) {
            startActivity(new Intent(PatientOptions.this, AboutUs.class));
        }
    }


}
