package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splsh);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            startActivity(new Intent(SplshActivity.this,MainActivity.class));
            finish();
            }
        },1000);
    }
}