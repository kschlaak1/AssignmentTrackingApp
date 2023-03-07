package com.example.assignmenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Main extends AppCompatActivity {
    MaterialButton teacherStart, studentStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teacherStart = findViewById(R.id.teacher);
        studentStart = findViewById(R.id.student);

        teacherStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TeacherLogin.class);
                startActivity(intent);
            }
        });

        studentStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StudentLogin.class);
                startActivity(intent);
            }
        });
    }
}
