package com.myschool.LogingRegisterActivitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myschool.Dahsbords.Student_DashbordActivity;
import com.myschool.R;
import com.myschool.databinding.ActivityRegisterBinding;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    // String name = "", email = "", password = "", phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.usernameEt.getText().toString();
                String email = binding.emailEt.getText().toString();
                String password = binding.passwordEt.getText().toString();
                String phone = binding.phoneEt.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(RegisterActivity.this, "Enter Your Name....", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(RegisterActivity.this, "Invalid Email Pattern..", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Enter Password...", Toast.LENGTH_SHORT).show();
                } else if (phone.length() < 10) {
                    Toast.makeText(RegisterActivity.this, "Enter Correct Nomber...", Toast.LENGTH_SHORT).show();
                } else {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.show();
                                    progressDialog.setContentView(R.layout.progressdialog_01);
                                    long timestamp = System.currentTimeMillis();

                                    String uid = firebaseAuth.getUid();

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("uid", uid);
                                    hashMap.put("name", name);
                                    hashMap.put("email", email);
                                    hashMap.put("password", password);
                                    hashMap.put("phone", phone);
                                    hashMap.put("profileImage", "");
                                    hashMap.put("userType", "std");
                                    hashMap.put("timesTamp", timestamp);

                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                    reference.child(uid)
                                            .setValue(hashMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(RegisterActivity.this, "Account Created..", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(RegisterActivity.this, Student_DashbordActivity.class));
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(RegisterActivity.this, "Erro" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}