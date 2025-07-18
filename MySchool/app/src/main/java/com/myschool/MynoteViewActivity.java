package com.myschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myschool.DBHelper.DBhelper;
import com.myschool.Models.Model;

import java.util.ArrayList;

public class MynoteViewActivity extends AppCompatActivity {
    TextView idTv, titleTv, MasseTv;
    ImageButton bacBtn, deletBtn;

    DBhelper dbHelper;
    ArrayList<Model> arrayList;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynote_view);
        idTv = findViewById(R.id.noteidTv);
        titleTv = findViewById(R.id.NtitleTv);
        MasseTv = findViewById(R.id.NmasseageTv);
        bacBtn = findViewById(R.id.backBtn);
        deletBtn = findViewById(R.id.deletBtn);

        dbHelper = new DBhelper(this);


        Intent i = getIntent();
        String tit = i.getStringExtra("titl");
        String mass = i.getStringExtra("mess");
        // This is Cach in Int Valus in Intent
        Bundle b = this.getIntent().getExtras();
        int view = b.getInt("Id");

        titleTv.setText(tit);
        MasseTv.setText(mass);
        idTv.setText(String.valueOf(view));

        deletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Model model = arrayList.get(view);
                // int i = model.getID();

                boolean dchek = dbHelper.notesdelete(view); //ඔබට පුලුවන් ඔනනෙ නම් වැලිඩෙශන් යොදගන්න
                if (dchek == true) {
                    Toast.makeText(MynoteViewActivity.this, "Delete Data", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(MynoteViewActivity.this, "No Delete Data...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bacBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}