package com.myfirstapplication.pvt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

        TextView tx1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx1 = findViewById(R.id.txt1);

        //tx1.setVisibility(View.GONE);
        tx1.setVisibility(View.VISIBLE);

    }


}