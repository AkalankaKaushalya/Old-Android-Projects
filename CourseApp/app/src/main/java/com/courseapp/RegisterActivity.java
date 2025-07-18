package com.courseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.courseapp.Helper.DBHelper;

import java.time.Instant;

public class RegisterActivity extends AppCompatActivity {
    TextView turnLogin;
    EditText Usaername, Password;
    //RadioButton Teacher, Student;
    RadioGroup Rgroup;
    RadioButton Rbut;
    Button RegBtu;

    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        turnLogin = findViewById(R.id.Backlogin);
        Usaername = findViewById(R.id.UUsername);
        Password = findViewById(R.id.Upassword);
        RegBtu = findViewById(R.id.Registerbut);

        //Teacher = findViewById(R.id.Teacherchek);
       // Student = findViewById(R.id.Studentchek);
        Rgroup = findViewById(R.id.rgroup);

        dbHelper = new DBHelper(this);



        turnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });


        //register මෙතඩ් එක
        RegBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = Usaername.getText().toString();
                String upass = Password.getText().toString();

                /*int radioID = Rgroup.getCheckedRadioButtonId();
                Rbut = findViewById(radioID);
                String utyp= Rbut.getText().toString();  //අපී මේ කරලා තියෙන්නෙ ඉන්ට්ජර් විදිහට වෙරියබල් එකක් හදගන්නවා පසුව එයට radiogroup එක මගින්
                                                            //අපි සෙලෙක්ට් කරපු රෙඩියො බට්න් එකෙ id එක ලබාගෙන එයට රෙඩියොබටන් පොදුවෙ ලබගන්නවා ඒ ලබ්ගත්
                                                    // පොදු වෙරියබල් එක අපි ස්ට්‍රින් ටයිප් එකෙන් ගත්ත යුසර් ටයිප් එකට සෙට් කොට එයින් ලබගන්නා වලියුව් එක ඩෙට බෙස් එකට ඇඩ් කරනව*/
                if (uname.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }else if (upass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean chake = dbHelper.register(uname, upass);
                    if (chake == true) {
                        Intent intent = new Intent(RegisterActivity.this, loginActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(RegisterActivity.this, "Registerd ", Toast.LENGTH_SHORT).show();
                        Toast.makeText(RegisterActivity.this, "You Can Now Login ", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RegisterActivity.this, "Not Register", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });





    }
}


/*else {
                    boolean chake = dbHelper.register(uname, upass, utyp);
                    if (chake == true) {
                        Intent intent = new Intent(RegisterActivity.this, loginActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(RegisterActivity.this, "Registerd ", Toast.LENGTH_SHORT).show();
                        Toast.makeText(RegisterActivity.this, "You Can Now Login ", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RegisterActivity.this, "Not Register", Toast.LENGTH_SHORT).show();
                    }
                }*/