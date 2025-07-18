package com.bigknowledge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
        EditText mEmailEt,mPasswoerdEt;
        TextView NothaveAccntTv,FogetPassword;
        Button mLoginBut;
        ProgressDialog progressDialog;
        FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        mEmailEt = findViewById(R.id.emailEt);
        mPasswoerdEt = findViewById(R.id.passwordEt);
        NothaveAccntTv= findViewById(R.id.nothaveaccountTv);
        FogetPassword = findViewById(R.id.recoverPassTv);
        mLoginBut = findViewById(R.id.loginBut);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Login in....");

        NothaveAccntTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        mLoginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEt.getText().toString().trim();
                String passwoerd = mPasswoerdEt.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmailEt.setText("Invalid Email");
                    mEmailEt.setFocusable(true);
                }
                else if (passwoerd.length()<6){
                    mEmailEt.setText("6 Charecters");
                    mEmailEt.setFocusable(true);
                }
                else {
                    LoginUser(email,passwoerd);
                }
            }
        });

        FogetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });

    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

        LinearLayout linearLayout = new LinearLayout(this);

        EditText Remail = new EditText(this);
        Remail.setHint("Email");
        Remail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        // set Ten minits run //
        Remail.setMinEms(10);

        linearLayout.addView(Remail);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = Remail.getText().toString().trim();
                beginRecovery(email);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // Show Recover Dialog //
        builder.create().show();
    }

    private void beginRecovery(String email) {
        progressDialog.show();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Email sent", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Failde....", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this,""+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void LoginUser(String email, String passwoerd) {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, passwoerd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, DashbordActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}