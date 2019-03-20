package com.example.zunnorain.noque.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Context context;

    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_EMAIL = "email";

    public Session(Context context) {

        this.context = context;
        sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void LogOutSession() {
        editor.clear();
        editor.commit();
    }


    public void setUser(String name, String email, String token, String type) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_TYPE, type);
        editor.putBoolean(IS_LOGIN,true);
        editor.commit();
    }

    public String getKeyName() {
        return sharedPreferences.getString(KEY_NAME, null);
    }

    public String getKeyEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public String getKeyType() {
        return sharedPreferences.getString(KEY_TYPE, null);
    }

    public String getKeyToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }
}
