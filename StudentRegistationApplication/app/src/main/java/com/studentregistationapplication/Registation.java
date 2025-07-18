package com.studentregistationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registation extends AppCompatActivity {
EditText title,subjec;
Button Addnote;
DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);

        title = findViewById(R.id.NoteTitle);
        subjec = findViewById(R.id.NoteSubject);
        Addnote = findViewById(R.id.addnote);

        dbHelper = new DBHelper(this);

        Addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = title.getText().toString();
                String s = subjec.getText().toString();

                if (t.isEmpty()) {
                    Toast.makeText(Registation.this, "Enter Title", Toast.LENGTH_SHORT).show();
                } else if (s.isEmpty()) {
                    Toast.makeText(Registation.this, "Enter Discription", Toast.LENGTH_SHORT).show();
                } else {
                    boolean chake = dbHelper.addnote(t, s);
                    if (chake == true) {
                        Toast.makeText(Registation.this, "Send Your Subject", Toast.LENGTH_SHORT).show();
                       title.setText(null);
                       subjec.setText(null);
                    } else {
                        Toast.makeText(Registation.this, "Not Send...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}