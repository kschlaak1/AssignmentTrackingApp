package com.example.assignmenttrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class StudentRegistration extends AppCompatActivity {
    EditText studentUsername, studentFirstname, studentLastname, studentPassword;
    MaterialButton studentRegistrationBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        studentUsername = findViewById(R.id.studentUsername);
        studentFirstname = findViewById(R.id.studentFirstname);
        studentLastname = findViewById(R.id.studentLastname);
        studentPassword = findViewById(R.id.studentPassword);
        studentRegistrationBT = findViewById(R.id.studentRegistrationBT);

        studentRegistrationBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username, firstname, lastname, password, table;
                username = studentUsername.getText().toString();
                firstname = studentFirstname.getText().toString();
                lastname = studentLastname.getText().toString();
                password = studentPassword.getText().toString();
                table = "Student";

                if (username.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Check Missing Input Value!!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "username";
                            field[1] = "firstname";
                            field[2] = "lastname";
                            field[3] = "password";
                            field[4] = "table";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = username;
                            data[1] = firstname;
                            data[2] = lastname;
                            data[3] = password;
                            data[4] = table;
                            PutData putData = new PutData("http://10.0.0.149/AssignmentTrackingApp/studentRegistration.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Sign up success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), StudentLogin.class);
                                        String sr_studentName = firstname + ' ' + lastname;
                                        intent.putExtra("TRY_THIS", sr_studentName);
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
