package com.datasaving;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class information extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5,e6;
    Button b;
    ImageButton f1;
    dbConnect dbConnect;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        dbConnect = new dbConnect(this);

        b = findViewById(R.id.b1);
        f1 = findViewById(R.id.imageButton);

        e1 = findViewById(R.id.ett0);
        e2 = findViewById(R.id.ett1);
        e3 = findViewById(R.id.ett2);
        e4 = findViewById(R.id.ett3);
        e5 = findViewById(R.id.ett4);
        e6 = findViewById(R.id.ett5);



        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nc = e1.getText().toString();
                String fn = e2.getText().toString();
                String ln = e3.getText().toString();
                String ma = e4.getText().toString();
                String cn = e5.getText().toString();
                String ad = e6.getText().toString();
                if (nc.isEmpty()){
                    Toast.makeText(information.this,"please enter the NIC number",Toast.LENGTH_SHORT).show();
                }
                else if (fn.isEmpty()){
                    Toast.makeText(information.this,"please enter the FIRST NAME number",Toast.LENGTH_SHORT).show();
                }
                else if (ln.isEmpty()){
                    Toast.makeText(information.this,"please enter the LAST NAME number",Toast.LENGTH_SHORT).show();
                }
                else if (ma.isEmpty()){
                    Toast.makeText(information.this,"please enter the EMAIL number",Toast.LENGTH_SHORT).show();
                 }
                else if (cn.isEmpty()){
                    Toast.makeText(information.this,"please enter the CONTACT NUMBER number",Toast.LENGTH_SHORT).show();
                }
                else if (ad.isEmpty()){
                    Toast.makeText(information.this,"please enter the ADDRESS number",Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean check = dbConnect.addData(nc,fn,ln,ma,cn,ad);
                    if (check==true){
                        Toast.makeText(information.this,"Successfully!",Toast.LENGTH_SHORT).show();
                        e1.setText(null);
                        e2.setText(null);
                        e3.setText(null);
                        e4.setText(null);
                        e5.setText(null);
                        e6.setText(null);

                        return;

                    }else {
                        Toast.makeText(information.this,"Error",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }











}