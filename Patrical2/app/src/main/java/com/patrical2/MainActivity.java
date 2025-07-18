package com.patrical2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText Edtxt,Edtxt1,Edtxt2;
Button Btu1,Btu2,Btu3,Btu4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Edtxt = findViewById(R.id.txtED);
        Edtxt1 = findViewById(R.id.txtED1);
        Edtxt2 = findViewById(R.id.txtED2);
        Btu1 = findViewById(R.id.button1);
        Btu2 = findViewById(R.id.button2);
        Btu3 = findViewById(R.id.button3);
        Btu4 = findViewById(R.id.button4);


        Btu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt1 = Edtxt1.getText().toString();
                String txt2 = Edtxt2.getText().toString();
                if (txt1.isEmpty()){
                    Toast.makeText(MainActivity.this, "first row Empty", Toast.LENGTH_SHORT).show();
                }
                else if (txt2.isEmpty()){
                    Toast.makeText(MainActivity.this, "sucand row Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    int n1 = Integer.parseInt(Edtxt1.getText().toString());
                    int n2 = Integer.parseInt(Edtxt2.getText().toString());
                    int sum = n1 + n2 ;
                    Edtxt.setText(String.valueOf(sum));

                }
            }
        });

        Btu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt1 = Edtxt1.getText().toString();
                String txt2 = Edtxt2.getText().toString();
                if (txt1.isEmpty()){
                    Toast.makeText(MainActivity.this, "first row Empty", Toast.LENGTH_SHORT).show();
                }
                else if (txt2.isEmpty()){
                    Toast.makeText(MainActivity.this, "sucand row Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    int n1 = Integer.parseInt(Edtxt1.getText().toString());
                    int n2 = Integer.parseInt(Edtxt2.getText().toString());
                    int sum = n1 - n2 ;
                    Edtxt.setText(String.valueOf(sum));

                }
            }
        });

        Btu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt1 = Edtxt1.getText().toString();
                String txt2 = Edtxt2.getText().toString();
                if (txt1.isEmpty()){
                    Toast.makeText(MainActivity.this, "first row Empty", Toast.LENGTH_SHORT).show();
                }
                else if (txt2.isEmpty()){
                    Toast.makeText(MainActivity.this, "sucand row Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    int n1 = Integer.parseInt(Edtxt1.getText().toString());
                    int n2 = Integer.parseInt(Edtxt2.getText().toString());
                    int sum = n1 / n2 ;
                    Edtxt.setText(String.valueOf(sum));

                }
            }
        });

        Btu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt1 = Edtxt1.getText().toString();
                String txt2 = Edtxt2.getText().toString();
                if (txt1.isEmpty()){
                    Toast.makeText(MainActivity.this, "first row Empty", Toast.LENGTH_SHORT).show();
                }
                else if (txt2.isEmpty()){
                    Toast.makeText(MainActivity.this, "sucand row Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    int n1 = Integer.parseInt(Edtxt1.getText().toString());
                    int n2 = Integer.parseInt(Edtxt2.getText().toString());
                    int sum = n1 * n2 ;
                    Edtxt.setText(String.valueOf(sum));

                }
            }
        });
    }
}