package com.example.zunnorain.noque.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.zunnorain.noque.Network.SingletonRequest;
import com.example.zunnorain.noque.R;
import com.example.zunnorain.noque.Session.Session;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONArray;
import org.json.JSONObject;

public class ViewPrescription extends AppCompatActivity implements View.OnClickListener {


    private TextView prescription;
    private String baseURL;
    private TextView messageText;
    private ProgressBar progressBar;
    private ThreeBounce threeBounce;
    private LinearLayout mainLayout;
    private ImageView backButton;

    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prescription);

        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        messageText = (TextView) findViewById(R.id.message_text);
        messageText.setVisibility(View.INVISIBLE);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);

        session = new Session(getApplicationContext());
        backButton = (ImageView) findViewById(R.id.back_button);
        prescription = (TextView) findViewById(R.id.prescription);


        backButton.setOnClickListener(this);

        sendRequest();
    }


    private void sendRequest() {

        mainLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        messageText.setVisibility(View.INVISIBLE);

        baseURL = getString(R.string.baseURL) + "pPrescriptions?token=" + session.getKeyToken();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, baseURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String s = response.getString("success");
                    if (s.equals("true")) {
                        JSONObject jsonObject = response.getJSONObject("data");
                        JSONArray jsonArray = jsonObject.getJSONArray("prescriptions");
                        if (jsonArray.length() < 1) {

                            mainLayout.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            messageText.setText("No Prescription Found");
                            messageText.setVisibility(View.VISIBLE);
                        } else {
                            JSONObject object = jsonArray.getJSONObject(0);
                            prescription.setText(object.getString("prescription"));

                            mainLayout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            messageText.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        mainLayout.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        messageText.setText("No Prescription Found");
                        messageText.setVisibility(View.VISIBLE);

                    }
                } catch (
                        Exception e)

                {

                }
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {

                mainLayout.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                messageText.setText("Something Went Wrong");
                messageText.setVisibility(View.VISIBLE);
            }
        });

        SingletonRequest.getInstance(

                getApplicationContext()).

                addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_button)
            finish();
    }
}
