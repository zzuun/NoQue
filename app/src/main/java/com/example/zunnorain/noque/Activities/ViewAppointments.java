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
import com.example.zunnorain.noque.AdapterClasses.MakeAppointmentListAdapter;
import com.example.zunnorain.noque.AdapterClasses.ViewAppointmentListAdapter;
import com.example.zunnorain.noque.ModelClasses.ScheduleItem;
import com.example.zunnorain.noque.Network.SingletonRequest;
import com.example.zunnorain.noque.R;
import com.example.zunnorain.noque.Session.Session;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ViewAppointments extends AppCompatActivity implements View.OnClickListener {

    private List<ScheduleItem> list;
    private ViewAppointmentListAdapter adapter;
    private RecyclerView recyclerView;

    private String baseURL;
    private TextView messageText;

    private ProgressBar progressBar;
    private ThreeBounce threeBounce;
    private LinearLayout mainLayout;
    Session session;
    Calendar calendar;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments);


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

        recyclerView = (RecyclerView) findViewById(R.id.appointment_list);

        backButton.setOnClickListener(this);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        list = new ArrayList<>();

        adapter = new ViewAppointmentListAdapter(getApplicationContext(), list, new ViewAppointmentListAdapter.MyAdapterListener() {
            @Override
            public void slotOneOnClick(View v, int position) {

            }

            @Override
            public void slotTwoOnClick(View v, int position) {

            }

            @Override
            public void slotThreeOnClick(View v, int position) {

            }

            @Override
            public void slotFourOnClick(View v, int position) {

            }

            @Override
            public void slotFiveOnClick(View v, int position) {

            }

            @Override
            public void slotSixOnClick(View v, int position) {

            }

            @Override
            public void slotSevenOnClick(View v, int position) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        sendRequest();

    }

    private void initialList() {


        Format monthFormat = new SimpleDateFormat("MMMM");
        DateFormat dateFormat = new SimpleDateFormat("dd");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        list.clear();
        for (int i = 0; i < 5; i++) {
            String date = dateFormat.format(calendar.getTime());
            String day = monthFormat.format(calendar.getTime()) + ", " + dayFormat.format(calendar.getTime());
            list.add(new ScheduleItem(date, day, false, R.drawable.time_interval_item_grey,
                    false, R.drawable.time_interval_item_grey, false, R.drawable.time_interval_item_grey,
                    false, R.drawable.time_interval_item_grey, false, R.drawable.time_interval_item_grey,
                    false, R.drawable.time_interval_item_grey, false, R.drawable.time_interval_item_grey));
            calendar.add(Calendar.DATE, 1);
        }

    }

    private void sendRequest() {

        mainLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        messageText.setVisibility(View.INVISIBLE);

        baseURL = getString(R.string.baseURL) + "getDoctorAppointments?token=" + session.getKeyToken();
        list.clear();

        initialList();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, baseURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String s = response.getString("success");
                    if (s.equals("true")) {

                        int day1 = Integer.parseInt(response.getString("day1"));
                        int day2 = Integer.parseInt(response.getString("day2"));
                        int day3 = Integer.parseInt(response.getString("day3"));
                        int day4 = Integer.parseInt(response.getString("day4"));
                        int day5 = Integer.parseInt(response.getString("day5"));

                        int[] day = {day1, day2, day3, day4, day5};


                        JSONArray jsonappointArray = response.getJSONArray("data");
                        //JSONObject jsonObject = jsonArray.getJSONObject(0);

                        int j = 0, k = 0, i = 0;
                        //JSONArray jsonappointArray = jsonObject.getJSONArray("appointments");
                        while (i < jsonappointArray.length()) {
                            j = 0;
                            while (j < day[k]) {
                                JSONObject object = jsonappointArray.getJSONObject(i++);
                                int startTime = Integer.parseInt(object.getString("startTime"));
                                if (startTime == 9) {
                                    list.get(k).setSlotOne(true);
                                    list.get(k).setSlotOneBackground(R.drawable.time_interval_item_blue);
                                } else if (startTime == 10) {
                                    list.get(k).setSlotTwo(true);
                                    list.get(k).setSlotTwoBackground(R.drawable.time_interval_item_blue);
                                } else if (startTime == 11) {
                                    list.get(k).setSlotThree(true);
                                    list.get(k).setSlotThreeBackground(R.drawable.time_interval_item_blue);
                                } else if (startTime == 12) {
                                    list.get(k).setSlotFour(true);
                                    list.get(k).setSlotFourBackground(R.drawable.time_interval_item_blue);
                                } else if (startTime == 13) {
                                    list.get(k).setSlotFive(true);
                                    list.get(k).setSlotFiveBackground(R.drawable.time_interval_item_blue);
                                } else if (startTime == 14) {
                                    list.get(k).setSloSix(true);
                                    list.get(k).setSlotSixBackground(R.drawable.time_interval_item_blue);
                                } else if (startTime == 15) {
                                    list.get(k).setSloSeven(true);
                                    list.get(k).setSlotSevenBackground(R.drawable.time_interval_item_blue);
                                }
                                j++;
                            }
                            k++;
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

                mainLayout.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                messageText.setVisibility(View.VISIBLE);
            }
        });

        SingletonRequest.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back_button)
            finish();
    }
}
