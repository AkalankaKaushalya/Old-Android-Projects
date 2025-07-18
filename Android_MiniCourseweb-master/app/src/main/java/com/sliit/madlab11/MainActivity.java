package com.sliit.madlab11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sliit.madlab11.database.DBHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_login, btn_register;
    EditText inp_username, inp_password;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        inp_username = findViewById(R.id.input_username);
        inp_password = findViewById(R.id.input_password);
    }

    @Override
    protected void onResume() {
        super.onResume();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBHandler dbHandler = new DBHandler(getApplicationContext());

                List USERNAME = dbHandler.readUserInfo("user");
                List PASSWORD = dbHandler.readUserInfo("password");
                List TYPE = dbHandler.readUserInfo("type");

                username = inp_username.getText().toString().trim();
                password = inp_password.getText().toString().trim();

                if (USERNAME.indexOf(username) >= 0) {
                    if (PASSWORD.get(USERNAME.indexOf(username)).equals(password)) {

                        if (TYPE.get(USERNAME.indexOf(username)).equals("Teacher")) {
                            Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Teacher.class);
                            intent.putExtra( "username", username);
                            startActivity(intent);
                            clear();
                        }
                        else if (TYPE.get(USERNAME.indexOf(username)).equals("Student")) {
                            Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Student.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                            clear();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Login Failed When un & pw matched!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Login Function Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });
    }

    public void clear() {
        inp_username.setText(null);
        inp_password.setText(null);
    }
}