package com.huzaifa.prayerqibla;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthManager {
    private static final String PREF_NAME = "PrayerQiblaAuth";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_LOGGED_IN_EMAIL = "loggedInEmail";
    private static final String KEY_LOGGED_IN_NAME = "loggedInName";
    
    private static AuthManager instance;
    private final SharedPreferences prefs;

    private AuthManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        seedDefaultUser();
    }

    public static synchronized AuthManager getInstance(Context context) {
        if (instance == null) {
            instance = new AuthManager(context);
        }
        return instance;
    }

    private void seedDefaultUser() {
        if (!prefs.contains("pass_admin@example.com")) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("pass_admin@example.com", "password123");
            editor.putString("name_admin@example.com", "Huzaifa User");
            editor.apply();
        }
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setLoggedIn(boolean isLoggedIn, String email, String name) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        if (isLoggedIn) {
            editor.putString(KEY_LOGGED_IN_EMAIL, email);
            editor.putString(KEY_LOGGED_IN_NAME, name);
        } else {
            editor.remove(KEY_LOGGED_IN_EMAIL);
            editor.remove(KEY_LOGGED_IN_NAME);
        }
        editor.apply();
    }

    public boolean registerUser(String name, String email, String password) {
        String keyEmail = email.trim().toLowerCase();
        if (prefs.contains("pass_" + keyEmail)) {
            return false; // Email already registered
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("pass_" + keyEmail, password);
        editor.putString("name_" + keyEmail, name);
        editor.apply();
        return true;
    }

    public boolean validateCredentials(String email, String password) {
        String keyEmail = email.trim().toLowerCase();
        String savedPassword = prefs.getString("pass_" + keyEmail, null);
        return savedPassword != null && savedPassword.equals(password);
    }

    public boolean resetPassword(String email, String newPassword) {
        String keyEmail = email.trim().toLowerCase();
        if (!prefs.contains("pass_" + keyEmail)) {
            return false;
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("pass_" + keyEmail, newPassword);
        editor.apply();
        return true;
    }

    public String getUserName(String email) {
        String keyEmail = email.trim().toLowerCase();
        return prefs.getString("name_" + keyEmail, "User");
    }

    public String getLoggedInEmail() {
        return prefs.getString(KEY_LOGGED_IN_EMAIL, "admin@example.com");
    }

    public String getLoggedInName() {
        return prefs.getString(KEY_LOGGED_IN_NAME, getUserName(getLoggedInEmail()));
    }

    public void logout() {
        setLoggedIn(false, null, null);
    }
}