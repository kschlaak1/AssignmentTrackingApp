package com.example.assignmenttrackingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager {
    // the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodinghsaredpref";
    private static final String KEY_USERNAME = "keyusername";
//    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_FIRST_NAME = "keyfirstname";
    private static final String KEY_LAST_NAME = "keylastname";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null)  {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    // method to let the user login
    // this method will store the user data in shared preferences
    public void userLogin(Teacher teacher) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, teacher.getUserName());
        editor.putString(KEY_FIRST_NAME, teacher.getFirstName());
        editor.putString(KEY_LAST_NAME, teacher.getLastName());
//        editor.putString(KEY_PASSWORD, teacher.getPassword());
        editor.apply();
    }

    // this method will check whether the user is already logged in
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    // this method will give the logged in teacher
    public Teacher getTeacher() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Teacher(
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_FIRST_NAME, null),
                sharedPreferences.getString(KEY_LAST_NAME, null)
//                sharedPreferences.getString(KEY_PASSWORD, null)
        );
    }

    // this method will log the user out
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Main.class));
    }
}
