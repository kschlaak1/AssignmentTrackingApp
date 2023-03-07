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

public class TeacherHome extends AppCompatActivity {
    TextView name, logoutLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        name = findViewById(R.id.welcome); // set up the welcome message to display teacher name

        // getting the current user
        Teacher teacher = SharedPrefManager.getInstance(this).getTeacher();

        // setting the values to the text-views
        name.setText("Welcome " + teacher.getFirstName() + " " + teacher.getLastName());

//        String teacherName;     // this is all retrieving the string passed from the login
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if (extras == null) {
//                teacherName = null;
//            } else {
//                teacherName = extras.getString("STRING_I_NEED");
//            }
//        } else {
//            teacherName = (String) savedInstanceState.getSerializable("STRING_I_NEED");
//        }
//
//        name.setText("Welcome " + teacherName);      // includes teacher name in welcome message
//
//        logoutLink = findViewById(R.id.logoutLink);
//
//        String textLogout = logoutLink.getText().toString();
//        Spannable spanLogout = new SpannableString(textLogout);
//
//        ClickableSpan clickableSpanLogout = new ClickableSpan() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TeacherHome.this, Main.class);
//                startActivity(intent);
//            }
//        };
//
//        spanLogout.setSpan(clickableSpanLogout, 0, textLogout.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        logoutLink.setText(spanLogout);
//        logoutLink.setMovementMethod(LinkMovementMethod.getInstance());

    }

}
