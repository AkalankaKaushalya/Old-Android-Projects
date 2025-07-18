package com.sliit.madlab11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sliit.madlab11.database.DBHandler;

public class Register extends AppCompatActivity {

    EditText inp_username, inp_password;
    RadioGroup userType;
    RadioButton teacher, student;
    Button btn_register;
    String username, password, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inp_username = findViewById(R.id.reg_username);
        inp_password = findViewById(R.id.reg_password);
        teacher = findViewById(R.id.rd_teacher);
        student = findViewById(R.id.rd_student);
        btn_register = findViewById(R.id.btn_reg);
    }

    @Override
    protected void onResume() {
        super.onResume();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler dbHandler = new DBHandler(getApplicationContext());

                username = inp_username.getText().toString().trim();
                password = inp_password.getText().toString().trim();

                if (teacher.isChecked()) {
                    type = "Teacher";
                } else {
                    type = "Student";
                }

                long result = dbHandler.addUser(username,password,type);

                if (result > 0) {
                    Toast.makeText(Register.this, "Registration Successfully Done!", Toast.LENGTH_SHORT).show();
                    clear();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(Register.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public void clear() {
        inp_username.setText(null);
        inp_password.setText(null);
    }

}