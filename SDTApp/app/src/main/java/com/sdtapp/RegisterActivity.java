package com.sdtapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText UserName, PassWord;
    CheckBox Teacher,Student;
    Button Register;
    DBHelper dbHelper;

    String tec = "Teacher";
    String std = "Student";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UserName = findViewById(R.id.userName);
        PassWord = findViewById(R.id.userPassword);
        Teacher = findViewById(R.id.techer);
        Student = findViewById(R.id.student);
        Register = findViewById(R.id.register);

        dbHelper = new DBHelper(this); // මෙයින් db helper අප මෙය තුල පෙන්විමට ගන්නවා

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Utype ="";
                String Uname = UserName.getText().toString();
                String Upassword = PassWord.getText().toString();


                if (Uname.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }else if (Upassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else {

                        Boolean checkinsert = dbHelper.registerdata(Uname, Upassword, Utype);
                        if (checkinsert==true){
                            Toast.makeText(RegisterActivity.this, "Registerd", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterActivity.this, "Not Registerd", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


        });

    }
}