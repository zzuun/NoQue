package com.example.zunnorain.noque.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.zunnorain.noque.Network.SingletonRequest;
import com.example.zunnorain.noque.R;
import com.example.zunnorain.noque.Session.Session;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONException;
import org.json.JSONObject;

public class WritePrescription extends AppCompatActivity implements View.OnClickListener {

    private TextView patientName;
    private EditText prescriptionText;
    private Button submit;
    private ImageView backButton;
    Session session;
    private String baseURL;
    private String patientID;
    private TextView messageText;
    private ProgressBar progressBar;
    private ThreeBounce threeBounce;
    private LinearLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_prescription);



        session = new Session(getApplicationContext());
        patientName = (TextView) findViewById(R.id.patient_name);
        prescriptionText = (EditText) findViewById(R.id.prescription_text);
        submit = (Button) findViewById(R.id.submit_button);
        backButton = (ImageView) findViewById(R.id.back_button);

        Intent intent = getIntent();
        patientID = intent.getStringExtra("patientID");

        patientName.setText(intent.getStringExtra("patientName"));
        submit.setOnClickListener(this);
        backButton.setOnClickListener(this);

    }

    private void submitPrescription() {
        String string = prescriptionText.getText().toString().trim();
        if (string.isEmpty()) {
            Toast.makeText(this, "Prescription Can't be empty", Toast.LENGTH_SHORT).show();
            return;
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("token", session.getKeyToken());
                jsonObject.put("patient_id", patientID);
                jsonObject.put("prescription", string);
            } catch (JSONException e) {

            }


            baseURL = getString(R.string.baseURL) + "dPrescription";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, baseURL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String s = response.getString("success");
                        if (s.equals("true")) {
                            Toast.makeText(WritePrescription.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } catch (Exception e) {

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            SingletonRequest.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_button) {
            finish();
        } else if (id == R.id.submit_button) {
            submitPrescription();
        }
    }
}
