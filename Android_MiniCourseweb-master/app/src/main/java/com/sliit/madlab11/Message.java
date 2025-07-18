package com.sliit.madlab11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Message extends AppCompatActivity {

    TextView subject, message, user;
    String txt_subject, txt_message, txt_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        subject = findViewById(R.id.txt_subject);
        message = findViewById(R.id.txt_message);
        user = findViewById(R.id.txt_lecturer);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            txt_subject = (String) bundle.get("subject");
            txt_message = (String) bundle.get("message");
            txt_user = (String) bundle.get("user");
            subject.setText(txt_subject);
            message.setText(txt_message);
            user.setText(txt_user);
        }
    }
}