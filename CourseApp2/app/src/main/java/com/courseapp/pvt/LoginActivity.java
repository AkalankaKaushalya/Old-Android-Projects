package com.courseapp.pvt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.courseapp.pvt.DataBase.DBHelper;

public class LoginActivity extends AppCompatActivity {
    TextView turnLogin;
    EditText Usaername, Password;
    Button LogingBtu;

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        turnLogin = findViewById(R.id.Backregis);
        Usaername = findViewById(R.id.UUsername);
        Password = findViewById(R.id.Upassword);
        LogingBtu = findViewById(R.id.loging);

        dbHelper = new DBHelper(this);


        turnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        //login මෙතඩ් එක
        LogingBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = Usaername.getText().toString();
                String upass = Password.getText().toString();
                if (uname.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }else if (upass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean chake = dbHelper.login(uname, upass);
                    if (chake == true) {
                        Intent intent = new Intent(LoginActivity.this, DashbordActivity.class);
                        intent.putExtra("UserName", uname);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Not Register", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}