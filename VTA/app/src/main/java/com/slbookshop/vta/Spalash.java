package com.slbookshop.vta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Spalash extends AppCompatActivity {
EditText edt;
Button btn;
TextView Tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash);

        edt = findViewById(R.id.Edt);
        btn = findViewById(R.id.button);
        Tv = findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = edt.getText().toString();
                Tv.setText(txt);
            }
        });
    }
}