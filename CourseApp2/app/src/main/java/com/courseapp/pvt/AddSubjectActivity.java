package com.courseapp.pvt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.courseapp.pvt.DataBase.DBHelper;
import com.courseapp.pvt.DataBase.SubDBHelper;

public class AddSubjectActivity extends AppCompatActivity {
    TextView NameView;
    EditText Sudject, Message;
    Button Send;

    SubDBHelper subDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        NameView = findViewById(R.id.text2);
        Sudject = findViewById(R.id.Tsubject);
        Message = findViewById(R.id.Tmessage);
        Send = findViewById(R.id.send);

        subDBHelper = new SubDBHelper(this);

        Intent i = getIntent();
        String name = i.getStringExtra("UserName");
        NameView.setText(name);


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sudject = Sudject.getText().toString();
                String message = Message.getText().toString();
                String uname = name;

                if (sudject.isEmpty()){
                    Toast.makeText(AddSubjectActivity.this, "Add Your Subject", Toast.LENGTH_SHORT).show();
                }else if (message.isEmpty()){
                    Toast.makeText(AddSubjectActivity.this, "Add Your Message", Toast.LENGTH_SHORT).show();
                }else if (uname == "Welcome null"){
                    Intent intent = new Intent(AddSubjectActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    boolean chake = subDBHelper.addMessage(uname, sudject, message);
                    if (chake == true) {
                        Toast.makeText(AddSubjectActivity.this, "Send Your Subject", Toast.LENGTH_SHORT).show();
                        Sudject.setText(null);
                        Message.setText(null);
                    } else {
                        Toast.makeText(AddSubjectActivity.this, "Not Send...", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }


}