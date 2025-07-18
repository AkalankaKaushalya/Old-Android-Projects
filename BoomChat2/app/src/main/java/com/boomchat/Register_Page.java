package com.boomchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
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

public class Register_Page extends AppCompatActivity {
      EditText rUsername,rEmail,rPassword;
      TextView txt,rbut;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth; // Insert Firebase Auth
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register__page);
        TextView log = findViewById(R.id.btnLogin);

        // Regi Component Fined id //
        EditText rUsername = findViewById(R.id.Rusername);
        EditText rEmail = findViewById(R.id.Remail);
        EditText rPassword = findViewById(R.id.Rpassword);
        TextView  rbut = findViewById(R.id.reg_but);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Progress Dialog
        progressDialog = new ProgressDialog(Register_Page.this);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Login_Page.class);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                startActivity(i);
            }
        });
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = rEmail.getText().toString().trim();
                String password = rPassword.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    rEmail.setError("Invalid Email");
                    rEmail.setFocusable(true);
                }
                else if (password.length()<6){
                    rPassword.setError("Password Length");
                    rPassword.setFocusable(true);
                }

                else{
                    registerUser(email, password);
                }
            }
        });
    }


    private void registerUser(String email, String password) {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progres_dilog);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                           FirebaseUser user = mAuth.getCurrentUser();
                           // Get User User Uid & Email //
                           String email = user.getEmail();
                           String uid  = user.getUid();

                           // Using HashMap //
                            HashMap<Object, String> hashMap = new HashMap<>();
                            // put infom in Map  //
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name", "");
                            hashMap.put("phone", "");
                            hashMap.put("image", "");
                            hashMap.put("coverimg", "");
                            // FireBase Data istance //
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            // path to store data name "User"
                            DatabaseReference reference = database.getReference("Users");
                            // put data with hash map in database //
                            reference.child(uid).setValue(hashMap);

                            Toast.makeText(Register_Page.this, "Registered... \n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(Register_Page.this, Dashboard_Activity.class));
                           finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(Register_Page.this, "You Creat Account in This Mail..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });

    }
}