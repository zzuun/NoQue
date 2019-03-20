package com.example.zunnorain.noque.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zunnorain.noque.R;

public class DocPatSelection extends AppCompatActivity implements View.OnClickListener{

    private Button doctorButton, patientButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_pat_selection);

        doctorButton=(Button)findViewById(R.id.doctor_button);
        patientButton=(Button)findViewById(R.id.patient_button);

        doctorButton.setOnClickListener(this);
        patientButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id =view.getId();

        if (R.id.doctor_button==id)
        {
            startActivity(new Intent(DocPatSelection.this,DoctorLoginActivity.class));
        }
        if (R.id.patient_button==id)
        {
            startActivity(new Intent(DocPatSelection.this,PatientLoginActivity.class));
        }
    }
}
