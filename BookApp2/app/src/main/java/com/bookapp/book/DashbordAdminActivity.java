package com.bookapp.book;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bookapp.book.Adapters.AdapterCategory;
import com.bookapp.book.Models.ModelCategory;
import com.bookapp.book.databinding.ActivityDashbordAdminBinding;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashbordAdminActivity extends AppCompatActivity {

    ActivityDashbordAdminBinding binding;

    FirebaseAuth firebaseAuth;

    ArrayList<ModelCategory> categoryArrayList;
    AdapterCategory adapterCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashbordAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        loadCategories();

        loadUserProfile();


        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterCategory.getFilter().filter(s);
                } catch (Exception e) {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        binding.addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashbordAdminActivity.this, CategoryAddActivity.class));
            }
        });

        binding.addPdfFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashbordAdminActivity.this, PdfAddActivity.class));
            }
        });

        binding.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashbordAdminActivity.this, ProfileActivity.class));
            }
        });
    }
    //-------------------------------------------------------------------------------------------//


    private void loadUserProfile() {
        if (firebaseAuth.getCurrentUser() == null) {

        } else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseAuth.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String profileImage = "" + snapshot.child("profileImage").getValue();


                            Glide.with(DashbordAdminActivity.this)
                                    .load(profileImage)
                                    .placeholder(R.drawable.profile_imag)
                                    .into(binding.profileBtn);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }

    private void loadCategories() {
        categoryArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ModelCategory modelCategory = ds.getValue(ModelCategory.class);
                    categoryArrayList.add(modelCategory);
                }
                adapterCategory = new AdapterCategory(DashbordAdminActivity.this, categoryArrayList);
                binding.categoriesRv.setAdapter(adapterCategory);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            String email = firebaseUser.getEmail();
            binding.subTitleTv.setText(email);
        }
    }
}