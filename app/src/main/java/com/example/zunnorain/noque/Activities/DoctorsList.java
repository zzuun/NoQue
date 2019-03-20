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
import com.example.zunnorain.noque.AdapterClasses.DoctorListAdapter;
import com.example.zunnorain.noque.ModelClasses.DoctorItem;
import com.example.zunnorain.noque.Network.SingletonRequest;
import com.example.zunnorain.noque.R;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoctorsList extends AppCompatActivity implements View.OnClickListener {

    private List<DoctorItem> list;
    private DoctorListAdapter adapter;
    private RecyclerView recyclerView;
    private String baseURL;
    private TextView messageText;
    private ProgressBar progressBar;
    private ThreeBounce threeBounce;
    private LinearLayout mainLayout;
    private ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        messageText = (TextView) findViewById(R.id.message_text);
        messageText.setVisibility(View.INVISIBLE);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.INVISIBLE);


        baseURL = getString(R.string.baseURL) + "getDoctors";
        recyclerView = (RecyclerView) findViewById(R.id.doctor_list);
        backButton = (ImageView) findViewById(R.id.back_button);

        backButton.setOnClickListener(this);
        list = new ArrayList<>();


        adapter = new DoctorListAdapter(getApplicationContext(), list, new DoctorListAdapter.MyAdapterListener() {
            @Override
            public void itemOnClick(View v, int position) {

                String id = list.get(position).getId();
                startActivity(new Intent(DoctorsList.this, MakeAppointment.class).putExtra("doctorID", id));
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

                        JSONArray jsonArray = response.getJSONArray("doctors");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            DoctorItem doctorItem = new DoctorItem();
                            JSONObject object = jsonArray.getJSONObject(i);
                            doctorItem.setName(object.getString("name"));
                            doctorItem.setId(object.getString("id"));
                            list.add(doctorItem);
                        }
                        adapter.notifyDataSetChanged();

                        mainLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        messageText.setVisibility(View.INVISIBLE);

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
        int id=v.getId();
        if (id==R.id.back_button)
        {
            finish();
        }
    }
}
