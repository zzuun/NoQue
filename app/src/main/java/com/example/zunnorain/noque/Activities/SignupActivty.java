package com.example.zunnorain.noque.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.zunnorain.noque.Network.SingletonRequest;
import com.example.zunnorain.noque.R;
import com.example.zunnorain.noque.Session.Session;

import org.json.JSONObject;

public class SignupActivty extends AppCompatActivity implements View.OnClickListener {


    Session session;
    private TextView loginButton;
    private TextInputLayout usernameField, passwordField, emailField,cnfrmPassField;
    private Button signupButton;
    private String baseURL;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String name, email, password,confrmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activty);

        session= new Session(getApplicationContext());
        baseURL=getString(R.string.baseURL)+"pSignup";

        signupButton = (Button) findViewById(R.id.signup_button);
        loginButton = (TextView) findViewById(R.id.login_button);
        usernameField = (TextInputLayout) findViewById(R.id.user_name_field);
        passwordField = (TextInputLayout) findViewById(R.id.password_field);
        emailField = (TextInputLayout) findViewById(R.id.email_field);
        cnfrmPassField=(TextInputLayout)findViewById(R.id.confirmpassword_field);


        usernameField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                usernameField.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
        cnfrmPassField.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cnfrmPassField.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signupButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (R.id.signup_button == id) {

            name = usernameField.getEditText().getText().toString().trim();
            email = emailField.getEditText().getText().toString().trim();
            password = passwordField.getEditText().getText().toString().trim();
            confrmPassword=cnfrmPassField.getEditText().getText().toString().trim();

            if (email.isEmpty() || !email.matches(emailPattern)) {
                emailField.setError("Enter Valid Email");
                return;
            }
            if (name.isEmpty()) {
                usernameField.setError("Enter Valid Name");
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
            if (!confrmPassword.equals(password))
            {
                cnfrmPassField.setError("Password Mismatch");
                return;
            }
            sendRequest();
        } else if (R.id.login_button == id) {
            startActivity(new Intent(SignupActivty.this, PatientLoginActivity.class));
            finish();
        }
    }

    private void sendRequest() {
       RequestQueue requestQueue=Volley.newRequestQueue(this);
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("name",name);
            jsonObject.put("email",email);
            jsonObject.put("password",password);
            jsonObject.put("c_password",confrmPassword);
        }catch (Exception e)
        {

        }

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, baseURL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String s = response.getString("success");
                    if (s.equals("true")) {
                        Toast.makeText(SignupActivty.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        String token = response.getString("token");
                        session.setUser(name,email,token,"patient");
                        Intent intent=new Intent(SignupActivty.this,PatientOptions.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);


                    }else
                        Toast.makeText(SignupActivty.this,response.getString("message") , Toast.LENGTH_SHORT).show();
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    Toast.makeText(SignupActivty.this, "hh", Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {

                }
                error.printStackTrace();
            }
        });

       // requestQueue.add(jsonObjectRequest);
        SingletonRequest.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


    }
}
