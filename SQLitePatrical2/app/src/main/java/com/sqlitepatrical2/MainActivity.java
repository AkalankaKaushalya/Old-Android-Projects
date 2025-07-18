package com.sqlitepatrical2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText UserName, Age, NIC, Email;
    Button Insert, Update, Delete, View;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserName = findViewById(R.id.uname);
        Age = findViewById(R.id.age);
        NIC = findViewById(R.id.nic);
        Email = findViewById(R.id.email);

        Insert = findViewById(R.id.insertbtu);
        Update = findViewById(R.id.updatebtu);
        Delete = findViewById(R.id.deletebtu);
        View = findViewById(R.id.viewbtu);

        // මෙය සියලු id improt කරගතයු වන්නෙ dbHelper
        dbHelper = new DBHelper(this);

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String uName = UserName.getText().toString();
                String age = Age.getText().toString();
                String Nic = NIC.getText().toString();
                String email = Email.getText().toString();

                boolean ichek = dbHelper.insert(uName,age,Nic,email); //ඔබට පුලුවන් ඔනනෙ නම් වැලිඩෙශන් යොදගන්න
                if (ichek==true){
                    Toast.makeText(MainActivity.this, "Insert Data", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(MainActivity.this, "No Insert Data...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String uName = UserName.getText().toString();
                String age = Age.getText().toString();
                String Nic = NIC.getText().toString();
                String email = Email.getText().toString();

                boolean uchek = dbHelper.update(uName,age,Nic,email); //ඔබට පුලුවන් ඔනනෙ නම් වැලිඩෙශන් යොදගන්න
                if(uchek==true) {
                    Toast.makeText(MainActivity.this, "Update Data", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "No Update Data...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String Nic = NIC.getText().toString();

                boolean dchek = dbHelper.delete(Nic); //ඔබට පුලුවන් ඔනනෙ නම් වැලිඩෙශන් යොදගන්න
                if(dchek==true){
                    Toast.makeText(MainActivity.this, "Delete Data", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "No Delete Data...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // view මෙතඩ් එක
        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent i = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(i);
            }
        });
    }
}