package com.courseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.courseapp.Helper.DBHelper;

public class loginActivity extends AppCompatActivity {
    EditText Usaername, Password;
    TextView turunRegister;
    Button Loginbtu;

    DBHelper dbHelper;

    String utype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        turunRegister = findViewById(R.id.Backregis);
        Usaername = findViewById(R.id.UUsername);
        Password = findViewById(R.id.Upassword);
        Loginbtu = findViewById(R.id.loginbut);

        dbHelper = new DBHelper(this);



        turunRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Loginbtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = Usaername.getText().toString();
                String upass = Password.getText().toString();
                String tp;

                if (uname.isEmpty()){
                    Toast.makeText(loginActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }else if (upass.isEmpty()) {
                    Toast.makeText(loginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean chake = dbHelper.login(uname, upass);
                    if (chake==true) {
                        Intent i = new Intent(loginActivity.this, TeacherActivity.class);
                        i.putExtra("uName",uname);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(loginActivity.this, "Dsent not mach", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


    }
}

/*Intent i = new Intent(loginActivity.this, StudentActivity.class);
    startActivity(i);
    finish();*/