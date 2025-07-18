package com.tvacstudio.textfields;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout textInputLayout;

    private AutoCompleteTextView dropDownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInputLayout = findViewById(R.id.text_input_layout);
        dropDownText = findViewById(R.id.dropdown_text);

        String[] items = new String[] {
                "Item 1",
                "Item 2",
                "Item 3",
                "Others"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                MainActivity.this,
                R.layout.dropdown_item,
                items
        );

        dropDownText.setAdapter(adapter);



    }
}
