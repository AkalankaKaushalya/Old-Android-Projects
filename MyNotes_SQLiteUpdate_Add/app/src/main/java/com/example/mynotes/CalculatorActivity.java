package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CalculatorActivity extends AppCompatActivity {

    boolean isNewOp = true;

    String oldNumber = "";
    String op = "";

    EditText et1;
    Button clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        et1 = findViewById(R.id.numberTV);
        clear = findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { et1.setText(null); }
        });
    }

    public void nuberEvent(View view) {
        if (isNewOp){
            et1.setText("");
            isNewOp = false;
        }
        String number = et1.getText().toString();
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
        et1.setText(number);
    }

    public void operatEvant(View view) {
        isNewOp = true;
        oldNumber = et1.getText().toString();

        switch (view.getId()){
            case R.id.Cbut1:
                op = "+";
                break;
            case R.id.Cbut2:
                op = "-";
                break;
            case R.id.Cbut3:
                op = "/";
                break;
            case R.id.Cbut4:
                op = "*";
                break;

        }
    }

    public void sumEvant(View view) {
        String newNumber = et1.getText().toString();

        double result = 0.0;

        switch (op){
            case "+":
                result = Double.parseDouble(oldNumber) + Double.parseDouble(newNumber);
                break;
            case "-":
                result = Double.parseDouble(oldNumber) - Double.parseDouble(newNumber);
                break;
            case "/":
                result = Double.parseDouble(oldNumber) / Double.parseDouble(newNumber);
                break;
            case "*":
                result = Double.parseDouble(oldNumber) * Double.parseDouble(newNumber);
                break;
        }

        et1.setText(result + "");
    }
}