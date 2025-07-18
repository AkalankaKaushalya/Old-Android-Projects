package com.mynote.nimsara;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewActivity extends AppCompatActivity {
    TextView idTv, titleTv, MasseTv;
    ImageButton bacBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        idTv = findViewById(R.id.noteidTv);
        titleTv = findViewById(R.id.NtitleTv);
        MasseTv = findViewById(R.id.NmasseageTv);
        bacBtn = findViewById(R.id.backBtn);


        Intent i = getIntent();
        String tit = i.getStringExtra("titl");
        String mass = i.getStringExtra("mess");
        // This is Cach in Int Valus in Intent
        Bundle b = this.getIntent().getExtras();
        int view = b.getInt("Id");

        titleTv.setText(tit);
        MasseTv.setText(mass);
        idTv.setText(String.valueOf(view));

        bacBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}