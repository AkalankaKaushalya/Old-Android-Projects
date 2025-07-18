package com.studentworknotes.vta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.studentworknotes.vta.DataBase.DBHelper;
import com.studentworknotes.vta.databinding.ActivityLoginandRegisterBinding;

public class LoginandRegisterActivity extends AppCompatActivity {

    ActivityLoginandRegisterBinding binding;
    DBHelper dbHelper;
    String Uname, Email, Passwoerd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginandRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.LoginandRegisterTv.setText("Welcome Back");
        dbHelper = new DBHelper(this);

        binding.noAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.LoginandRegisterTv.setText("New User Register");
                binding.nameTil.setVisibility(View.VISIBLE);
                binding.registerBtn.setVisibility(View.VISIBLE);
                binding.haveAccountTv.setVisibility(View.VISIBLE);
                binding.lognBtn.setVisibility(View.GONE);
                binding.noAccountTv.setVisibility(View.GONE);
            }
        });

        binding.haveAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.LoginandRegisterTv.setText("Welcome Back");
                binding.nameTil.setVisibility(View.GONE);
                binding.registerBtn.setVisibility(View.GONE);
                binding.haveAccountTv.setVisibility(View.GONE);
                binding.lognBtn.setVisibility(View.VISIBLE);
                binding.noAccountTv.setVisibility(View.VISIBLE);
            }
        });

        binding.lognBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = binding.emailEt.getText().toString();
                Passwoerd = binding.passwordEt.getText().toString();
                if (Email.isEmpty()) {
                    Toast.makeText(LoginandRegisterActivity.this, "Enter Email..", Toast.LENGTH_SHORT).show();
                } else if (Passwoerd.isEmpty()) {
                    Toast.makeText(LoginandRegisterActivity.this, "Enter Password..", Toast.LENGTH_SHORT).show();
                } else if (Passwoerd.length() < 6) {
                    Toast.makeText(LoginandRegisterActivity.this, "Password is Short", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    Toast.makeText(LoginandRegisterActivity.this, "Its Not Validet email", Toast.LENGTH_SHORT).show();
                } else {
                    boolean chake = dbHelper.login(Email, Passwoerd);
                    if (chake == true) {
                        Intent intent = new Intent(LoginandRegisterActivity.this, DashbordActivity.class);
                        intent.putExtra("UserEmail", Email);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginandRegisterActivity.this, "Not Register", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uname = binding.nameEt.getText().toString();
                Email = binding.emailEt.getText().toString();
                Passwoerd = binding.passwordEt.getText().toString();
                if (Email.isEmpty()) {
                    Toast.makeText(LoginandRegisterActivity.this, "Enter Email..", Toast.LENGTH_SHORT).show();
                } else if (Uname.isEmpty()) {
                    Toast.makeText(LoginandRegisterActivity.this, "Enter User Name..", Toast.LENGTH_SHORT).show();
                } else if (Passwoerd.isEmpty()) {
                    Toast.makeText(LoginandRegisterActivity.this, "Enter Password..", Toast.LENGTH_SHORT).show();
                } else if (Passwoerd.length() < 6) {
                    Toast.makeText(LoginandRegisterActivity.this, "Password is Short", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    Toast.makeText(LoginandRegisterActivity.this, "Its Not Validet email", Toast.LENGTH_SHORT).show();
                } else {
                    boolean chake = dbHelper.register(Uname, Email, Passwoerd);
                    if (chake == true) {
                        Intent intent = new Intent(LoginandRegisterActivity.this, DashbordActivity.class);
                        intent.putExtra("UserEmail", Email);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginandRegisterActivity.this, "Not Register", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}