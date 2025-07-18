package com.bookapp.book;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bookapp.book.databinding.ActivityPdfEditBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PdfEditActivity extends AppCompatActivity {

    ActivityPdfEditBinding binding;

    ArrayList<String> categoryTitleArryList, categoryIdArrayList;

    String bookId;
    ProgressDialog progressDialog;
    String title = "", description = "";
    String selectedCategoryId = "", selectedCategoryTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Pleas wait");
        progressDialog.setCanceledOnTouchOutside(false);


        bookId = getIntent().getStringExtra("bookId");

        loadPdfCategories();
        loadBookInfo();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.categoryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryPicerDialog();
            }
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private void loadBookInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        selectedCategoryId = "" + snapshot.child("categoryId").getValue();
                        String description = "" + snapshot.child("description").getValue();
                        String title = "" + snapshot.child("title").getValue();

                        binding.titleEt.setText(title);
                        binding.descriptionEt.setText(description);

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
                        ref.child(selectedCategoryId)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String category = "" + snapshot.child("category").getValue();
                                        binding.categoryTv.setText(category);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void validateData() {
        title = binding.titleEt.getText().toString();
        description = binding.descriptionEt.getText().toString();

        if (TextUtils.isEmpty(title)) {
            Toast.makeText(PdfEditActivity.this, "Enter Title...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(description)) {
            Toast.makeText(PdfEditActivity.this, "Enter Description...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(selectedCategoryTitle)) {
            Toast.makeText(PdfEditActivity.this, "Enter Category...", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Updating book info...");
            progressDialog.show();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("title", "" + title);
            hashMap.put("description", "" + description);
            hashMap.put("categoryId", "" + selectedCategoryId);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
            ref.child(bookId)
                    .updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(PdfEditActivity.this, "Book info Update...", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                        }
                    });
        }

    }

    private void loadPdfCategories() {
        categoryTitleArryList = new ArrayList<>();
        categoryIdArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryTitleArryList.clear();
                categoryIdArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String categoryId = "" + ds.child("id").getValue();
                    String categoryTitle = "" + ds.child("category").getValue();

                    categoryTitleArryList.add(categoryTitle);
                    categoryIdArrayList.add(categoryId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void categoryPicerDialog() {
        String[] categoryArray = new String[categoryTitleArryList.size()];
        for (int i = 0; i < categoryTitleArryList.size(); i++) {
            categoryArray[i] = categoryTitleArryList.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Category")
                .setItems(categoryArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedCategoryTitle = categoryTitleArryList.get(which);
                        selectedCategoryId = categoryIdArrayList.get(which);

                        binding.categoryTv.setText(selectedCategoryTitle);// set Text in Textview
                    }
                })
                .show();
    }
}