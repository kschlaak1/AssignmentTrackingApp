package com.example.assignmenttrackingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class StudentLogin extends AppCompatActivity {
    EditText studentUsername, studentPassword;
    TextView registerLink;
    MaterialButton studentLoginBT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        studentUsername = findViewById(R.id.studentEditText);
        studentPassword = findViewById(R.id.passwordEditText);
        registerLink = findViewById(R.id.registerLink);
        studentLoginBT = findViewById(R.id.studentLoginBT);


        String studentName;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                studentName = "null";
            } else {
                studentName = extras.getString("TRY_THIS");
            }
        } else {
            studentName = (String) savedInstanceState.getSerializable("TRY_THIS");
        }


        String text = registerLink.getText().toString();
        Spannable ss = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentLogin.this, StudentRegistration.class);
                startActivity(intent);
            }
        };

        ss.setSpan(clickableSpan, 25, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        registerLink.setText(ss);
        registerLink.setMovementMethod(LinkMovementMethod.getInstance());

        studentLoginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username, password;
                username = studentUsername.getText().toString();
                password = studentPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Check Missing Input Value!!!", Toast.LENGTH_SHORT).show();

                } else {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";

                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;

                            PutData putData = new PutData("http://10.0.0.149/AssignmentTracking/AssignmentTrackingScripts/studentLogin.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (!result.equals("Username or password incorrect") && !result.equals("Error: Database connection") && !result.equals("All fields are required")) {
//                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), StudentHome.class);     // takes user to the student home page
//                                        String sl_studentName = studentName;
                                        intent.putExtra("STRING_I_NEED", result);          // passes value to the next landing page
                                        startActivity(intent);
                                        finish();
                                    } else Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}
