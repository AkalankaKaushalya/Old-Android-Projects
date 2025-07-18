package com.maliya.tool_master;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maliya.tool_master.databinding.ActivityLoginBinding;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    Window window;
    ActivityLoginBinding binding;
    String username = "", email = "", password = "";

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();


        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.stutas_bar));

        }
        binding.noAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.LoginandRegisterTv.setText("Register Account");
                binding.nameTil.setVisibility(View.VISIBLE);
                binding.registerBtn.setVisibility(View.VISIBLE);
                binding.haveAccountTv.setVisibility(View.VISIBLE);
                binding.lognBtn.setVisibility(View.GONE);
                binding.noAccountTv.setVisibility(View.GONE);
                binding.forgetpssTV.setVisibility(View.GONE);
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
                binding.forgetpssTV.setVisibility(View.VISIBLE);
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = binding.nameEt.getText().toString();
                email = binding.emailEt.getText().toString();
                password = binding.passwordEt.getText().toString();
                if (username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter UserName", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(LoginActivity.this, "Invalid Email Pattern..", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter Password...", Toast.LENGTH_SHORT).show();
                } else {
                    // progressDialog.setMessage("Account Create..");
                    // progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    //progressDialog.setMessage("Seving user info...");

                                    long timestamp = System.currentTimeMillis();

                                    String uid = firebaseAuth.getUid();

                                    HashMap<String, Object> hashMap = new HashMap<>();
                                    hashMap.put("uid", uid);
                                    hashMap.put("name", username);
                                    hashMap.put("email", email);
                                    hashMap.put("profileImage", "");
                                    hashMap.put("userType", "user");
                                    hashMap.put("timesTamp", timestamp);

                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                    reference.child(uid)
                                            .setValue(hashMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    //progressDialog.dismiss();
                                                    Toast.makeText(LoginActivity.this, "Account Created..", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(LoginActivity.this, DashbordActivity.class));
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // progressDialog.dismiss();
                                                    Toast.makeText(LoginActivity.this, "Erro" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

        binding.lognBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = binding.emailEt.getText().toString();
                password = binding.passwordEt.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(LoginActivity.this, "Invalid Email Pattern..", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter Password...", Toast.LENGTH_SHORT).show();
                } else {
                    // progressDialog.setMessage("Loging In...");
                    // progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    // progressDialog.setMessage("Checking User...");
                                   /* FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                    ref.child(firebaseUser.getUid())
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                   // progressDialog.dismiss();
                                                    String userType = "" + snapshot.child("userType").getValue();
                                                    if (userType.equals("user")) {
                                                        startActivity(new Intent(LoginActivity.this, DashbordUserActivity.class));
                                                        finish();

                                                    } else if (userType.equals("admin")) {
                                                        startActivity(new Intent(LoginActivity.this, DashbordAdminActivity.class));
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                   // progressDialog.dismiss();
                                                    Toast.makeText(LoginActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });*/
                                    startActivity(new Intent(LoginActivity.this, DashbordActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

}