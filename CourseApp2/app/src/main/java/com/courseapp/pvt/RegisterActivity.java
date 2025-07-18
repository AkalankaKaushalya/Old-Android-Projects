package com.courseapp.pvt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.courseapp.pvt.DataBase.DBHelper;

public class RegisterActivity extends AppCompatActivity {
    TextView turnLogin;
    EditText Usaername, Password;
    Button Register;

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        turnLogin = findViewById(R.id.Bcklogin);
        Usaername = findViewById(R.id.UUsername);
        Password = findViewById(R.id.Upassword);
        Register = findViewById(R.id.register);

        dbHelper = new DBHelper(this);


        turnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        //register මෙතඩ් එක
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = Usaername.getText().toString();
                String upass = Password.getText().toString();
                if (uname.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }else if (upass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean chake = dbHelper.register(uname, upass);
                    if (chake == true) {
                        Intent intent = new Intent(RegisterActivity.this, DashbordActivity.class);
                        intent.putExtra("UserName", uname);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Not Register", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}