package com.example.assignmenttrackingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TeacherRegistration extends AppCompatActivity {
    EditText teacherUsername, teacherFirstname, teacherLastname, teacherPassword;
    MaterialButton teacherRegistrationBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        // directly start the profile activity if user is already logged in
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity((new Intent(this, TeacherHome.class)));
            return;
        }

        teacherUsername = findViewById(R.id.teacherUsername);
        teacherFirstname = findViewById(R.id.teacherFirstname);
        teacherLastname = findViewById(R.id.teacherLastname);
        teacherPassword = findViewById(R.id.teacherPassword);
        teacherRegistrationBT = findViewById(R.id.teacherRegistrationBT);

        // register button calls register method
        teacherRegistrationBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        // first getting the values
        final String username = teacherUsername.getText().toString();
        final String firstname = teacherFirstname.getText().toString();
        final String lastname = teacherLastname.getText().toString();
        final String password = teacherPassword.getText().toString();
        final String table = "teacher";

        // validating inputs
        if (TextUtils.isEmpty(username)) {
            teacherUsername.setError("Please enter username");
            teacherUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(firstname)) {
            teacherFirstname.setError("Please enter your first name");
            teacherFirstname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastname)) {
            teacherLastname.setError("Please enter your last name");
            teacherLastname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            teacherPassword.setError("Please enter your password");
            teacherPassword.requestFocus();
            return;
        }

        // if it passes all the validations
        class RegisterUser extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {

                // creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                // creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("firstname", firstname);
                params.put("lastname", lastname);
                params.put("password", password);
                params.put("table", table);

                // returns the response
                String teacher_register_url = "http://10.0.0.149/AssignmentTrackingApp/teacherRegistration.php";
                return requestHandler.sendPostRequest(teacher_register_url, params);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    // converting response to json object
                    JSONObject obj = new JSONObject(s);

                    // if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        // getting the user from the response
                        JSONObject teacherJson = obj.getJSONObject("teacher");

                        // creating a new teacher object
                        Teacher teacher = new Teacher(
                                teacherJson.getString("username"),
                                teacherJson.getString("firstname"),
                                teacherJson.getString("lastname")
//                                teacherJson.getString("password")
                        );

                        // storing teacher in preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(teacher);

                        // starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), TeacherHome.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }
}
