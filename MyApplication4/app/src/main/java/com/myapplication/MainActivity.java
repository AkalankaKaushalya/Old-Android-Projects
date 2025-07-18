package com.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

EditText Ed1, Ed2;
Button but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Ed1 = findViewById(R.id.editTextTextPersonName);
        Ed2 = findViewById(R.id.editTextTextPersonName2);
        but = findViewById(R.id.button);



        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gtx = Ed1.getText().toString();

                if (gtx.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter Text", Toast.LENGTH_SHORT).show();
                }
                else {
                    Ed2.setText(gtx);

                }
            }
        });


    }

}