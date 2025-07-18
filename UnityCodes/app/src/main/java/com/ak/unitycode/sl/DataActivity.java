package com.ak.unitycode.sl;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DataActivity extends AppCompatActivity {
    String uname, age, modile = "";
    private Window window;
    private FirebaseAuth firebaseAuth;
    private EditText Username, Age, Mobile;
    private ImageView Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Username = findViewById(R.id.usernameEt);
        Age = findViewById(R.id.ageEt);
        Mobile = findViewById(R.id.telnoEt);
        Next = findViewById(R.id.nextBtn);

        firebaseAuth = FirebaseAuth.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        }

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = Username.getText().toString().trim();
                age = Age.getText().toString().trim();
                modile = Mobile.getText().toString().trim();

                if (uname.isEmpty()) {
                    Toast.makeText(DataActivity.this, "Enter Name...", Toast.LENGTH_SHORT).show();
                } else if (age.isEmpty()) {
                    Toast.makeText(DataActivity.this, "Enter Age...", Toast.LENGTH_SHORT).show();
                } else if (modile.isEmpty()) {
                    Toast.makeText(DataActivity.this, "Enter Mobile...", Toast.LENGTH_SHORT).show();
                } else if (modile.length() < 10) {
                    Toast.makeText(DataActivity.this, "Enter Valid Number...", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Name", "" + uname);
                    hashMap.put("Age", "" + age);
                    hashMap.put("Phone", "" + modile);

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                    ref.child(firebaseAuth.getUid())
                            .updateChildren(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //progressDialog.dismiss();
                                    Toast.makeText(DataActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(DataActivity.this, AdminActivity.class));
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //progressDialog.dismiss();
                                    Toast.makeText(DataActivity.this, "Not Profile Updated", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}