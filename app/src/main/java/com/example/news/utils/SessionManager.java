package com.example.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.databinding.BaseObservable;

import static android.content.Context.MODE_PRIVATE;

public class SessionManager extends BaseObservable {
    private static SharedPreferences shared;
    private static SharedPreferences.Editor editor;
    private static SessionManager session;


    private final String USER_ID = "user_id";
    private final String AUTH_TOKEN = "auth_token";
    private final String IS_LOGIN = "is_login";


    public static SessionManager getInstance(Context context) {
        if (session == null) {
            session = new SessionManager();
        }
        if (shared == null) {
            shared = context.getSharedPreferences("NewsApp", MODE_PRIVATE);
            editor = shared.edit();
        }
        return session;
    }

    public String getAuthToken() {
        return shared.getString(AUTH_TOKEN, "");
    }

    public void setAuthToken(String authToken) {
        editor.putString(AUTH_TOKEN, authToken);
        editor.commit();
    }

    public boolean isLogin() {
        return shared.getBoolean(IS_LOGIN, false);
    }

    public void setLogin() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }


    public String getUSER_ID() {
        return shared.getString(USER_ID, "");
    }

    public void setUSER_ID(String userId) {
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public void logout() {
        editor.putString(USER_ID, "");
        editor.putString(AUTH_TOKEN, "");
        editor.putBoolean(IS_LOGIN, false);
        editor.commit();
    }
}