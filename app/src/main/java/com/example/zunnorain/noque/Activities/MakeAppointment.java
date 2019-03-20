package com.example.zunnorain.noque.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.zunnorain.noque.AdapterClasses.MakeAppointmentListAdapter;
import com.example.zunnorain.noque.ModelClasses.ScheduleItem;
import com.example.zunnorain.noque.Network.SingletonRequest;
import com.example.zunnorain.noque.R;
import com.example.zunnorain.noque.Session.Session;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MakeAppointment extends AppCompatActivity implements View.OnClickListener {

    private List<ScheduleItem> list;
    private TextView doctorName, doctorEmail, clinicName, clinicEmail, clinicNumber;
    private MakeAppointmentListAdapter adapter;
    private RecyclerView recyclerView;
    private String doctorID;
    private String baseURL, baseURL2;
    private String appointmentStatus;
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
        setContentView(R.layout.activity_make_appointment);

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
        doctorName = (TextView) findViewById(R.id.doctor_name);
        clinicName = (TextView) findViewById(R.id.clinic_name);
        clinicEmail = (TextView) findViewById(R.id.clinic_email);
        clinicNumber = (TextView) findViewById(R.id.clinic_number);
        doctorEmail = (TextView) findViewById(R.id.doctor_email);

        backButton.setOnClickListener(this);
        Intent intent = getIntent();
        doctorID = intent.getStringExtra("doctorID");

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        list = new ArrayList<>();

        adapter = new MakeAppointmentListAdapter(getApplicationContext(), list, new MakeAppointmentListAdapter.MyAdapterListener() {
            @Override
            public void slotOneOnClick(View v, int position) {
                checkAppointmentStatus(position, list.get(position).isSlotOne(), "9", "10");
            }

            @Override
            public void slotTwoOnClick(View v, int position) {
                checkAppointmentStatus(position, list.get(position).isSlotTwo(), "10", "11");
            }

            @Override
            public void slotThreeOnClick(View v, int position) {
                checkAppointmentStatus(position, list.get(position).isSlotThree(), "11", "12");
            }

            @Override
            public void slotFourOnClick(View v, int position) {
                checkAppointmentStatus(position, list.get(position).isSlotFour(), "12", "13");
            }

            @Override
            public void slotFiveOnClick(View v, int position) {
                checkAppointmentStatus(position, list.get(position).isSlotFive(), "13", "14");
            }

            @Override
            public void slotSixOnClick(View v, int position) {
                checkAppointmentStatus(position, list.get(position).isSloSix(), "14", "15");
            }

            @Override
            public void slotSevenOnClick(View v, int position) {
                checkAppointmentStatus(position, list.get(position).isSloSeven(), "15", "16");
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        sendRequest();

    }

    private void checkAppointmentStatus(int position, boolean status, String s, String s1) {
        if (appointmentStatus == "true") {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Make Appointment");
            builder1.setMessage("You Already had an appointment.");
            builder1.setCancelable(false);
            builder1.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        } else {
            if (!status) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Make Appointment");
                builder1.setMessage("This slot is already reserved.");
                builder1.setCancelable(false);
                builder1.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            } else
                makeAppointment(position + 1, s, s1);
        }

    }

    private void makeAppointment(int i, String s, String s1) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("token", session.getKeyToken());
            jsonObject.put("doctor_id", doctorID);
            jsonObject.put("day", i);
            jsonObject.put("startTime", s);
            jsonObject.put("endTime", s1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        baseURL2 = getString(R.string.baseURL) + "mAppointment";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, baseURL2, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String s = response.getString("success");
                    if (s.equals("true")) {
                        Toast.makeText(MakeAppointment.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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

    private void initialList() {


        Format monthFormat = new SimpleDateFormat("MMMM");
        DateFormat dateFormat = new SimpleDateFormat("dd");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        Calendar currentCal = Calendar.getInstance();
        currentCal.set(Calendar.HOUR_OF_DAY, 0);
        currentCal.set(Calendar.MINUTE, 0);
        currentCal.set(Calendar.SECOND, 0);
        currentCal.set(Calendar.MILLISECOND, 0);


        list.clear();
        for (int i = 0; i < 5; i++) {

            String date = dateFormat.format(calendar.getTime());
            String day = monthFormat.format(calendar.getTime()) + ", " + dayFormat.format(calendar.getTime());

            if (calendar.getTime().before(currentCal.getTime())) {
                list.add(new ScheduleItem(date, day, false, R.drawable.time_interval_item_grey,
                        false, R.drawable.time_interval_item_grey, false, R.drawable.time_interval_item_grey,
                        false, R.drawable.time_interval_item_grey, false, R.drawable.time_interval_item_grey,
                        false, R.drawable.time_interval_item_grey, false, R.drawable.time_interval_item_grey));
            } else {
                list.add(new ScheduleItem(date, day, true, R.drawable.time_interval_item_green,
                        true, R.drawable.time_interval_item_green, true, R.drawable.time_interval_item_green,
                        true, R.drawable.time_interval_item_green, true, R.drawable.time_interval_item_green,
                        true, R.drawable.time_interval_item_green, true, R.drawable.time_interval_item_green));

            }


            calendar.add(Calendar.DATE, 1);
        }

    }

    private void sendRequest() {

        mainLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        messageText.setVisibility(View.INVISIBLE);

        baseURL = getString(R.string.baseURL) + "dAppointments?id=" + doctorID + "&token=" + session.getKeyToken();
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

                        appointmentStatus = response.getString("appStatus");

                        int[] day = {day1, day2, day3, day4, day5};


                        JSONArray jsonArray = response.getJSONArray("data");
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        doctorName.setText(jsonObject.getString("name"));
                        doctorEmail.setText(jsonObject.getString("email"));
                        JSONObject clinic = jsonObject.getJSONObject("clinic");
                        clinicName.setText(clinic.getString("name"));
                        clinicEmail.setText(clinic.getString("email"));
                        clinicNumber.setText(clinic.getString("phone"));

                        int j = 0, k = 0, i = 0;
                        JSONArray jsonappointArray = jsonObject.getJSONArray("appointments");
                        while (i < jsonappointArray.length()) {
                            j = 0;
                            while (j < day[k]) {
                                JSONObject object = jsonappointArray.getJSONObject(i++);
                                int startTime = Integer.parseInt(object.getString("startTime"));
                                if (startTime == 9) {
                                    list.get(k).setSlotOne(false);
                                    list.get(k).setSlotOneBackground(R.drawable.time_interval_item_grey);
                                } else if (startTime == 10) {
                                    list.get(k).setSlotTwo(false);
                                    list.get(k).setSlotTwoBackground(R.drawable.time_interval_item_grey);
                                } else if (startTime == 11) {
                                    list.get(k).setSlotThree(false);
                                    list.get(k).setSlotThreeBackground(R.drawable.time_interval_item_grey);
                                } else if (startTime == 12) {
                                    list.get(k).setSlotFour(false);
                                    list.get(k).setSlotFourBackground(R.drawable.time_interval_item_grey);
                                } else if (startTime == 13) {
                                    list.get(k).setSlotFive(false);
                                    list.get(k).setSlotFiveBackground(R.drawable.time_interval_item_grey);
                                } else if (startTime == 14) {
                                    list.get(k).setSloSix(false);
                                    list.get(k).setSlotSixBackground(R.drawable.time_interval_item_grey);

                                } else if (startTime == 15) {
                                    list.get(k).setSloSeven(false);
                                    list.get(k).setSlotSevenBackground(R.drawable.time_interval_item_grey);
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
