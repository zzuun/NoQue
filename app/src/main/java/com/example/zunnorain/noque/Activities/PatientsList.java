package com.example.zunnorain.noque.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.zunnorain.noque.AdapterClasses.PatientListAdapter;
import com.example.zunnorain.noque.ModelClasses.PatientItem;
import com.example.zunnorain.noque.Network.SingletonRequest;
import com.example.zunnorain.noque.R;
import com.example.zunnorain.noque.Session.Session;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PatientsList extends AppCompatActivity implements View.OnClickListener {


    private List<PatientItem> list;
    private PatientListAdapter adapter;
    private RecyclerView recyclerView;
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
        setContentView(R.layout.activity_patients_list);

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

        baseURL = getString(R.string.baseURL) + "getPatients?token=" + session.getKeyToken();
        recyclerView = (RecyclerView) findViewById(R.id.patient_list);
        list = new ArrayList<>();

        backButton.setOnClickListener(this);

        adapter = new PatientListAdapter(getApplicationContext(), list, new PatientListAdapter.MyAdapterListener() {
            @Override
            public void itemOnClick(View v, int position) {

                String id = list.get(position).getId();
                startActivity(new Intent(PatientsList.this, WritePrescription.class)
                        .putExtra("patientID", id)
                        .putExtra("patientName", list.get(position).getName()));
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        sendRequest();

    }

    private void sendRequest() {


        mainLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        messageText.setVisibility(View.INVISIBLE);

        list.clear();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, baseURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String s = response.getString("success");
                    if (s.equals("true")) {

                        JSONArray jsonArray = response.getJSONArray("data");
                        if (jsonArray.length() < 1) {
                            mainLayout.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            messageText.setText("No Patient Found");
                            messageText.setVisibility(View.VISIBLE);
                        } else {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                PatientItem patientItem = new PatientItem();
                                JSONObject object = jsonArray.getJSONObject(i);
                                patientItem.setName(object.getString("name"));
                                patientItem.setId(object.getString("id"));
                                list.add(patientItem);
                            }
                            adapter.notifyDataSetChanged();
                            mainLayout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            messageText.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        mainLayout.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        messageText.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    mainLayout.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    messageText.setVisibility(View.VISIBLE);
                } catch (Exception e) {

                }
                error.printStackTrace();
            }
        });

        SingletonRequest.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_button) {
            finish();
        }
    }
}
