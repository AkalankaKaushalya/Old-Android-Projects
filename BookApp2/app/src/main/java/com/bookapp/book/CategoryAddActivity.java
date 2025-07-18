package com.bookapp.book;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bookapp.book.databinding.ActivityCategoryAddBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CategoryAddActivity extends AppCompatActivity {
    ActivityCategoryAddBinding binding;

    FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = "";

                category = binding.categoryEt.getText().toString().trim();
                if (TextUtils.isEmpty(category)) {
                    Toast.makeText(CategoryAddActivity.this, "Please enter Category...!", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("Adding category...");
                    progressDialog.show();

                    long timestamp = System.currentTimeMillis();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", "" + timestamp);
                    hashMap.put("category", "" + category);
                    hashMap.put("timestamp", timestamp);
                    hashMap.put("uid", "" + firebaseAuth.getUid());

                    DatabaseReference rfe = FirebaseDatabase.getInstance().getReference("Categories");
                    rfe.child("" + timestamp)
                            .setValue(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    binding.categoryEt.setText(null);
                                    Toast.makeText(CategoryAddActivity.this, "Add Category Successfuly...", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CategoryAddActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}