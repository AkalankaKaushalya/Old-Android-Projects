package com.canerkaya.asciiconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    EditText textingText,binaryText,octalText,asciiText,hexaText;
    String convert;
    byte[] bytes;
    BigInteger bi;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textingText=findViewById(R.id.textingText);
        binaryText=findViewById(R.id.binaryText);
        octalText=findViewById(R.id.octalText);
        asciiText=findViewById(R.id.asciiText);
        hexaText=findViewById(R.id.hexaText);


        textingText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textingText.isFocused()) {
                    if (!textingText.getText().toString().matches("")) {
                        convert = textingText.getText().toString();
                        bytes = convert.getBytes();
                        bi = new BigInteger(bytes);
                        binaryText.setText("0" +bi.toString(2));
                        octalText.setText(bi.toString(8));
                        asciiText.setText(bi.toString(10));
                        hexaText.setText(bi.toString(16));
                    }
//                    else {
//                        binaryText.setText("");
//                        octalText.setText("");
//                        asciiText.setText("");
//                        hexaText.setText("");
//                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        binaryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binaryText.isFocused()){
                    String info=binaryText.getText().toString();
                    if (info.length()!=0){
                        int charCode=Integer.parseInt(info,2);
                        //String charCode = info;
                        String result=new Character((char) charCode).toString();
                        textingText.setText(result);
                        bytes=result.getBytes();
                        bi=new BigInteger(bytes);
                        octalText.setText(bi.toString(8));
                        asciiText.setText(bi.toString(10));
                        hexaText.setText(bi.toString(16));
                    }
//                    else {
//                        textingText.setText("");
//                        asciiText.setText("");
//                        octalText.setText("");
//                        hexaText.setText("");
//                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        octalText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (octalText.isFocused()) {
                    String info = octalText.getText().toString();
                    if (info.length() != 0) {
                        int charCode = Integer.parseInt(info, 8);
                        String result = new Character((char) charCode).toString();
                        textingText.setText(result);
                        bytes=result.getBytes();
                        bi=new BigInteger(bytes);
                        binaryText.setText(bi.toString(2));
                        asciiText.setText(bi.toString(10));
                        hexaText.setText(bi.toString(16));
                    }
//                    else {
//                        textingText.setText("");
//                        binaryText.setText("");
//                        asciiText.setText("");
//                        hexaText.setText("");
//                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        asciiText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (asciiText.isFocused()){
                    String info=asciiText.getText().toString();
                    if (info.length()!=0){
                        int charCode=Integer.parseInt(info,10);
                        String result=new Character((char)charCode).toString();
                        textingText.setText(result);
                        bytes=result.getBytes();
                        bi=new BigInteger(bytes);
                        binaryText.setText(bi.toString(2));
                        octalText.setText(bi.toString(8));
                        hexaText.setText(bi.toString(16));
                    }
//                    else {
//                        textingText.setText("");
//                        binaryText.setText("");
//                        octalText.setText("");
//                        hexaText.setText("");
//                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        hexaText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (hexaText.isFocused()){
                    String info=hexaText.getText().toString();
                    if (info.length()!=0){
                        int charCode=Integer.parseInt(info,16);
                        String result=new Character((char)charCode).toString();
                        textingText.setText(result);
                        bytes=result.getBytes();
                        bi=new BigInteger(bytes);
                        binaryText.setText(bi.toString(2));
                        octalText.setText(bi.toString(8));
                        asciiText.setText(bi.toString(10));
                    }

//                    else {
//                        textingText.setText("");
//                        binaryText.setText("");
//                        octalText.setText("");
//                        asciiText.setText("");
//                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }


    public void reset(View view){
        textingText.setText("");
        binaryText.setText("");
        octalText.setText("");
        asciiText.setText("");
        hexaText.setText("");
    }
}
