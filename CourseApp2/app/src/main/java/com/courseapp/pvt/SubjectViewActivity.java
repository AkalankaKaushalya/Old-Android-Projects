package com.courseapp.pvt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SubjectViewActivity extends AppCompatActivity {

    TextView Ssubject, Smessage, Suser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_view);
        Ssubject = findViewById(R.id.SubjectShow);
        Smessage = findViewById(R.id.MessageShow);
        Suser    = findViewById(R.id.UserShow);

        Intent i = getIntent();
        String sub = i.getStringExtra("subject");
        String msg = i.getStringExtra("message");
        String user = i.getStringExtra("user");
        Ssubject.setText(sub);
        Smessage.setText(msg);
        Suser   .setText("Uplode By "+user);
    }
}