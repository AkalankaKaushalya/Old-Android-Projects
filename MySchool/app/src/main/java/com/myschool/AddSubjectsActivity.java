package com.myschool;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myschool.databinding.ActivityAddSubjectsBinding;

import java.util.HashMap;

public class AddSubjectsActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ActivityAddSubjectsBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddSubjectsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.stitleEt.getText().toString();
                String sub = binding.smassageEt.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(AddSubjectsActivity.this, "Pleas Add Title..", Toast.LENGTH_SHORT).show();
                } else if (sub.isEmpty()) {
                    Toast.makeText(AddSubjectsActivity.this, "Pleas Add Subject..", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progressdialog_01);

                    long timestamp = System.currentTimeMillis();

                    String uid = firebaseAuth.getUid();

                    String nid = timestamp + uid;

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("uid", uid);
                    hashMap.put("Title", title);
                    hashMap.put("Subject", sub);
                    hashMap.put("NID", nid);
                    hashMap.put("Date", timestamp);

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lesons");
                    ref.child(nid)
                            .setValue(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddSubjectsActivity.this, "Successfully Uploaded...", Toast.LENGTH_SHORT).show();
                                    binding.stitleEt.setText(null);
                                    binding.smassageEt.setText(null);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddSubjectsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });
    }
}