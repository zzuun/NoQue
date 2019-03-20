package com.example.zunnorain.noque.Activities;


import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.zunnorain.noque.Network.SingletonRequest;
import com.example.zunnorain.noque.R;
import com.example.zunnorain.noque.Session.Session;

import org.json.JSONObject;

public class DoctorLoginActivity extends AppCompatActivity implements OnClickListener {

    Session session;
    private TextInputLayout emailField, passwordField;
    private Button loginButton;
    private String baseURL;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        baseURL=getString(R.string.baseURL)+"dLogin";

        session = new Session(getApplicationContext());


        loginButton = (Button) findViewById(R.id.login_button);
        emailField = (TextInputLayout) findViewById(R.id.email_field);
        passwordField = (TextInputLayout) findViewById(R.id.password_field);
        ;
        emailField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailField.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        passwordField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passwordField.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (R.id.login_button == id) {

            email = emailField.getEditText().getText().toString().trim();
            password = passwordField.getEditText().getText().toString().trim();

            if (email.isEmpty() || !email.matches(emailPattern)) {
                emailField.setError("Enter Valid Email");
                return;
            }

            if (password.isEmpty()) {
                passwordField.setError("Password Can't be Empty");
                return;
            }
            if (password.length() < 5) {
                passwordField.setError("Password Must be Atleast 6 Characters Long");
                return;
            }
            sendRequest();

        }
    }

    private void sendRequest() {

        JSONObject jsonObject= new JSONObject();
        try {

            jsonObject.put("email",email);
            jsonObject.put("password",password);
        }catch (Exception e)
        {

        }

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, baseURL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String s = response.getString("success");
                    if (s.equals("true")) {
                        Toast.makeText(DoctorLoginActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        String token = response.getString("token");
                        String name=response.getString("name");
                        session.setUser(name,email,token,"doctor");
                        Intent intent=new Intent(DoctorLoginActivity.this,DoctorOptions.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }else
                        Toast.makeText(DoctorLoginActivity.this,response.getString("message") , Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    Toast.makeText(DoctorLoginActivity.this, "hh", Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {

                }
                error.printStackTrace();
            }
        });

        SingletonRequest.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


    }

}

