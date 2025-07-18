package com.bigknowledge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
        EditText mEmailEt,mPasswordEt;
        Button mRegisterBut;
        TextView haveAccount;
        ProgressDialog progressDialog;
        FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        mEmailEt = findViewById(R.id.emailEt);
        mPasswordEt = findViewById(R.id.passwordEt);
        mRegisterBut = findViewById(R.id.registerBut);
        haveAccount = findViewById(R.id.have_accountTv);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User....");

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        mRegisterBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEt.getText().toString().trim();
                String passwoerd = mPasswordEt.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmailEt.setText("Invalid Email");
                    mEmailEt.setFocusable(true);
                }
                else if (passwoerd.length()<6){
                    mEmailEt.setText("6 Charecters");
                    mEmailEt.setFocusable(true);
                }
                else {
                    registerUser(email,passwoerd);
                }
            }
        });
    }

    private void registerUser(String email, String passwoerd) {
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, passwoerd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                FirebaseUser user = mAuth.getCurrentUser();

                                String email = user.getEmail();
                                String uid  = user.getUid();

                                HashMap<Object, String> hashMap = new HashMap<>();
                                hashMap.put("email",email);
                                hashMap.put("uid",uid);
                                hashMap.put("name","");
                                hashMap.put("onlineStatuse","online");
                                hashMap.put("typingTo","noOne");
                                hashMap.put("phone","");
                                hashMap.put("image","");
                                hashMap.put("cover","");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(hashMap);

                                Toast.makeText(RegisterActivity.this, "Welcome..\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, DashbordActivity.class));
                                finish();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

    }


    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}