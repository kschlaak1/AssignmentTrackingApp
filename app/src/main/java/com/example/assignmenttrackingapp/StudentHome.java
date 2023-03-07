package com.example.assignmenttrackingapp;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StudentHome extends AppCompatActivity {
    TextView enrollLink, name, logoutLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        name = findViewById(R.id.welcome); // set up the welcome message to display student name

        String studentName;     // this is all retrieving the string passed from the login
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                studentName = null;
            } else {
                studentName = extras.getString("STRING_I_NEED");
            }
        } else {
            studentName = (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }

        name.setText("Welcome " + studentName);      // includes student name in welcome message

        enrollLink = findViewById(R.id.enrollLink);

        String textEnroll = enrollLink.getText().toString();
        Spannable spanEnroll = new SpannableString(textEnroll);

        ClickableSpan clickableSpanEnroll = new ClickableSpan() {     // takes user to the student enrollment page
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this, CourseEnroll.class);
                startActivity(intent);
            }
        };

        spanEnroll.setSpan(clickableSpanEnroll, 23, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // sets the word 'here' to be clickable
        enrollLink.setText(spanEnroll);
        enrollLink.setMovementMethod(LinkMovementMethod.getInstance());

        logoutLink = findViewById(R.id.logoutLink);

        String textLogout = logoutLink.getText().toString();
        Spannable spanLogout = new SpannableString(textLogout);

        ClickableSpan clickableSpanLogout = new ClickableSpan() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentHome.this, Main.class);
                startActivity(intent);
            }
        };

        spanLogout.setSpan(clickableSpanLogout, 0, textLogout.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        logoutLink.setText(spanLogout);
        logoutLink.setMovementMethod(LinkMovementMethod.getInstance());

    }

}
