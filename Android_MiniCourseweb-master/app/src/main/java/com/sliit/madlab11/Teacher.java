package com.sliit.madlab11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sliit.madlab11.database.DBHandler;

public class Teacher extends AppCompatActivity {

    EditText inp_subject, inp_message;
    String subject, message, username;
    TextView user;
    Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        inp_subject = findViewById(R.id.input_subject);
        inp_message = findViewById(R.id.input_message);
        btn_send = findViewById(R.id.btn_send);
        user = findViewById(R.id.txt_teacher);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            username = (String) bundle.get("username");
            user.setText(username);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler dbHandler = new DBHandler(getApplicationContext());

                subject = inp_subject.getText().toString().trim();
                message = inp_message.getText().toString().trim();

                long result = dbHandler.addMessage(username,subject,message);

                if (result > 0) {
                    Toast.makeText(Teacher.this, "Message Send!", Toast.LENGTH_SHORT).show();
                    clear();
                } else {
                    Toast.makeText(Teacher.this, "Message Send Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clear() {
        inp_subject.setText(null);
        inp_message.setText(null);
    }
}