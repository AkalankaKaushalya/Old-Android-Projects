package com.sqlitepatrical3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewData extends AppCompatActivity {
TextView Name,Age,Nic,Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        Name = findViewById(R.id.MName);
        Age = findViewById(R.id.MAge);
        Nic = findViewById(R.id.MNic);
        Email = findViewById(R.id.MEmail);

        Intent i = getIntent();
        String name = i.getStringExtra("uName");
        String age = i.getStringExtra("age");
        String nic = i.getStringExtra("nic");
        String email = i.getStringExtra("email");
        Name.setText("Name : "+name);
        Age.setText("Age : "+age);
        Nic.setText("Nic "+nic);
        Email.setText("Email : "+email);
    }
}