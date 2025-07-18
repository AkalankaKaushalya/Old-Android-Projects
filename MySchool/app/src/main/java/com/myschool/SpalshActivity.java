package com.myschool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myschool.Dahsbords.Student_DashbordActivity;
import com.myschool.Dahsbords.Teacher_DashbordActivity;

public class SpalshActivity extends AppCompatActivity {
    SpinKitView progresBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        firebaseAuth = FirebaseAuth.getInstance();
        //Start main screen after 2seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    startActivity(new Intent(SpalshActivity.this, MainActivity.class));
                    finish();
                } else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                    ref.child(firebaseUser.getUid())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String userType = "" + snapshot.child("userType").getValue();
                                    if (userType.equals("std")) {
                                        startActivity(new Intent(SpalshActivity.this, Student_DashbordActivity.class));
                                        finish();

                                    } else if (userType.equals("tec")) {
                                        startActivity(new Intent(SpalshActivity.this, Teacher_DashbordActivity.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(SpalshActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        }, 2000);
    }
}