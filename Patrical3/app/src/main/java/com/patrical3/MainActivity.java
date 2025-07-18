package com.patrical3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean isNewOP = true;
    Button clear;
    EditText textView;

    String Op = "+";
    String OldNumber = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        clear = findViewById(R.id.clear);
        textView = findViewById(R.id.numberTV);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(null);
            }
        });
    }
    public void nuberEvent(View view){
        if (isNewOP){
            textView.setText("");
            isNewOP = false;
        }

        String number = textView.getText().toString();
        switch (view.getId()){
            case R.id.but0:
                number += "0";
                break;
            case R.id.but1:
                number += "1";
                break;
            case R.id.but2:
                number += "2";
                break;
            case R.id.but3:
                number += "3";
                break;
            case R.id.but4:
                number += "4";
                break;
            case R.id.but5:
                number += "5";
                break;
            case R.id.but6:
                number += "6";
                break;
            case R.id.but7:
                number += "7";
                break;
            case R.id.but8:
                number += "8";
                break;
            case R.id.but9:
                number += "9";
                break;
            case R.id.dot:
                number += ".";
                break;

        }
        textView.setText(number);
    }

    public void operatEvant(View view) { //මෙය නිර්මානය කරගත් පසූ MainActivity class එකතුල බුලියන් අකාරයට  newopter true කරගන්න
        isNewOP = true;  //මෙය ලිවිමෙන් පසු ඔපරෙට් දෙකක් එකතූ කිරිමට String Op එකක් සහා පරණ නම්බර් එක මතක තබා ගැනිමට String oldenum එකක් class එක තුල හදගන්න
        OldNumber = textView.getText().toString();
        switch (view.getId()){
            case R.id.Cbut1:Op = "+"; break;
            case R.id.Cbut2:Op = "-"; break;
            case R.id.Cbut3:Op = "/"; break;
            case R.id.Cbut4:Op = "*"; break;
        }


    }

    public void sumEvant(View view) {
        String newNuber = textView.getText().toString();
        if (newNuber.isEmpty()) {
            Toast.makeText(this, "Enter Values", Toast.LENGTH_SHORT).show();
        } else {
            double result = 0.0;
            switch (Op) {
                case "+":
                    result = Double.parseDouble(OldNumber) + Double.parseDouble(newNuber);
                    break;
                case "-":
                    result = Double.parseDouble(OldNumber) - Double.parseDouble(newNuber);
                    break;
                case "/":
                    result = Double.parseDouble(OldNumber) / Double.parseDouble(newNuber);
                    break;
                case "*":
                    result = Double.parseDouble(OldNumber) * Double.parseDouble(newNuber);
                    break;
            }
            textView.setText(result + "");
        }
    }
}