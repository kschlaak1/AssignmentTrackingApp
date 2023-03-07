package com.example.assignmenttrackingapp;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TeacherLogin extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    MaterialButton teacherLoginBT;
    TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        editTextUsername = (EditText) findViewById(R.id.teacherEditText);
        editTextPassword = (EditText) findViewById(R.id.passwordEditText);
        teacherLoginBT = (MaterialButton) findViewById(R.id.teacherLoginBT);
        registerLink = findViewById(R.id.registerLink);

        // login button calls login method
        teacherLoginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        // sets span for registering
        String text = registerLink.getText().toString();
        Spannable ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), TeacherRegistration.class));
            }
        };

        ss.setSpan(clickableSpan, 25, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerLink.setText(ss);
        registerLink.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void userLogin() {
        // first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String table = "teacher";

        // validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        // if it passes all the validations
        class UserLogin extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {

                // creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                // creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("table", table);

                // returns the response
                String teacher_login_url = "http://10.0.0.149/AssignmentTracking/AssignmentTrackingScripts/teacherLogin.php";
                return requestHandler.sendPostRequest(teacher_login_url, params);
            }

//            ProgressBar progressBar;

//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                progressBar = (ProgressBar) findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
//            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the teacher from the response
                        JSONObject teacherJson = obj.getJSONObject("teacher");

                        //creating a new teacher object
                        Teacher teacher = new Teacher(
                                teacherJson.getString("username"),
                                teacherJson.getString("firstname"),
                                teacherJson.getString("lastname")
//                                teacherJson.getString("password")
                        );

                        // storing the teacher in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(teacher);

                        // starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), TeacherHome.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // executing the async task
        UserLogin ul = new UserLogin();
        ul.execute();
    }
}
