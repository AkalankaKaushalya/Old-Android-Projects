package com.courseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.courseapp.Helper.DBHelper;

public class TeacherActivity extends AppCompatActivity {
    EditText TSud,TMess;
    Button Send;
    TextView Text;

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        Text = findViewById(R.id.text3);
        TSud = findViewById(R.id.Tsubject);
        TMess = findViewById(R.id.Tmessage);
        Send = findViewById(R.id.send);

        dbHelper = new DBHelper(this);

        Intent i = getIntent();
        String name = i.getStringExtra("uName");
        Text.setText("Welcome "+name);


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = TSud.getText().toString();
                String message = TMess.getText().toString();
                if (subject.isEmpty()){
                    Toast.makeText(TeacherActivity.this, "Enter Subject", Toast.LENGTH_SHORT).show();
                }else if (message.isEmpty()){
                    Toast.makeText(TeacherActivity.this, "Enter Message", Toast.LENGTH_SHORT).show();
                }else if (name.isEmpty()){
                    Toast.makeText(TeacherActivity.this, "Please Come Register", Toast.LENGTH_SHORT).show();
                }else {
                    boolean chake = dbHelper.send(name, subject, message);
                    if (chake == true) {
                        Toast.makeText(TeacherActivity.this, "Send", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TeacherActivity.this, "Not Send", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    //  inflate Option Menu //
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id  = item.getItemId();
        if (id == R.id.add_but){
            Intent i = new Intent(TeacherActivity.this, StudentActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


}