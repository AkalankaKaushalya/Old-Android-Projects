package com.slbookshop.vta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText nameEt, ageEt, nicEt;
Button addBtn, upaBtn, delBtn;
DBconnet dBconnet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dBconnet = new DBconnet(this);

        nameEt = findViewById(R.id.name);
        ageEt = findViewById(R.id.age);
        nicEt = findViewById(R.id.nic);

        addBtn = findViewById(R.id.add);
        upaBtn =  findViewById(R.id.upadate);
        delBtn = findViewById(R.id.delete);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEt.getText().toString();
                String age = ageEt.getText().toString();
                String nic = nicEt.getText().toString();

                boolean i = dBconnet.add(name,age,nic);
                if(i==true){
                    Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}