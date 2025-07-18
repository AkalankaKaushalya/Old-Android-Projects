package com.bookapp.book;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bookapp.book.Adapters.AdapterComment;
import com.bookapp.book.Models.ModelComment;
import com.bookapp.book.databinding.ActivityPdfDetailBinding;
import com.bookapp.book.databinding.DialogCommentAddBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PdfDetailActivity extends AppCompatActivity {

    ActivityPdfDetailBinding binding;

    String bookId, bookTitle, bookUrl;

    boolean isInMyFavoite = false;
    FirebaseAuth firebaseAuth;


    ProgressDialog progressDialog;

    ArrayList<ModelComment> commentArrayList;
    AdapterComment adapterComment;
    String comment = "";
    private ActivityResultLauncher<String> resultLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    MyApplication.DownloadBook(this, "" + bookId, "" + bookTitle, "" + bookUrl);
                }

            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");

        binding.downloadBookBtn.setVisibility(View.GONE);

        loadBookDetails();
        loadComments();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        MyApplication.BookViewContent(bookId);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            checkIsFavorite();
        } else {

        }

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.reedBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PdfDetailActivity.this, PdfViewActivity.class);
                intent1.putExtra("bookId", bookId);
                startActivity(intent1);
            }
        });

        binding.downloadBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(PdfDetailActivity.this, "You re not logged in", Toast.LENGTH_SHORT).show();
                } else {
                    if (ContextCompat.checkSelfPermission(PdfDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        MyApplication.DownloadBook(PdfDetailActivity.this, "" + bookId, "" + bookTitle, "" + bookUrl);
                    } else {
                        resultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                }
            }
        });

        binding.favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(PdfDetailActivity.this, "You re not logged in", Toast.LENGTH_SHORT).show();
                } else {
                    if (isInMyFavoite) {
                        MyApplication.removeFromFavorite(PdfDetailActivity.this, bookId);
                    } else {
                        MyApplication.addToFavorite(PdfDetailActivity.this, bookId);
                    }
                }
            }
        });


        binding.addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(PdfDetailActivity.this, "Your not logged in....", Toast.LENGTH_SHORT).show();
                } else {
                    addCommnetDialog();
                }
            }
        });

    }

    private void loadComments() {
        commentArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId).child("Comments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        commentArrayList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            ModelComment modelComment = ds.getValue(ModelComment.class);

                            commentArrayList.add(modelComment);
                        }

                        adapterComment = new AdapterComment(PdfDetailActivity.this, commentArrayList);
                        binding.commentRv.setAdapter(adapterComment);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void addCommnetDialog() {

        DialogCommentAddBinding commentAddBinding = DialogCommentAddBinding.inflate(LayoutInflater.from(this));

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);
        builder.setView(commentAddBinding.getRoot());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        commentAddBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        commentAddBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = commentAddBinding.commentEt.getText().toString();
                if (comment.isEmpty()) {
                    Toast.makeText(PdfDetailActivity.this, "Text Box is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    alertDialog.dismiss();
                    addComment();
                }
            }
        });

    }

    private void addComment() {
        progressDialog.setMessage("Adding commnet...");
        progressDialog.show();

        String timestamp = "" + System.currentTimeMillis();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", "" + timestamp);
        hashMap.put("bookId", "" + bookId);
        hashMap.put("timestamp", "" + timestamp);
        hashMap.put("comment", "" + comment);
        hashMap.put("uid", "" + firebaseAuth.getUid());

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId).child("Comments").child(timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PdfDetailActivity.this, "Commnent added..", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PdfDetailActivity.this, "Failed Commnent " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });

    }

    private void loadBookDetails() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        bookTitle = "" + snapshot.child("title").getValue();
                        String description = "" + snapshot.child("description").getValue();
                        String categoryId = "" + snapshot.child("categoryId").getValue();
                        String viewCount = "" + snapshot.child("viewCount").getValue();
                        String downlodsCount = "" + snapshot.child("downlodsCount").getValue();
                        bookUrl = "" + snapshot.child("url").getValue();
                        String timestamp = "" + snapshot.child("timestamp").getValue();

                        binding.downloadBookBtn.setVisibility(View.VISIBLE);

                        String data = MyApplication.formatTimestamp(Long.parseLong(timestamp));
                        MyApplication.loadPdfSize("" + bookUrl, "" + bookTitle, binding.sizeTv);
                        MyApplication.loadPdfFromUrlSinglePage("" + bookUrl, "" + bookTitle, binding.pdfView, binding.progressBar);  //මෙයි කතා වෙන්නෙ MyApplication ක්ලාස් එකෙ පබ්ලික් කර ඇති
                        MyApplication.loadCategory("" + categoryId, binding.categoryTv);//මෙතර්ඩ්ස් කොල් කිරිමයි..
                        MyApplication.incrementBookDownloadCount("" + bookId);
                        MyApplication.loadPdfPageCount(PdfDetailActivity.this, "" + bookUrl, binding.pagesTv);


                        binding.titleTv.setText(bookTitle);
                        binding.descriptionTv.setText(description);
                        binding.viewsTv.setText(viewCount.replace("null", "N/A"));
                        binding.downloadsTv.setText(downlodsCount.replace("null", "N/A"));
                        binding.dateTv.setText(data);
                        binding.downloadsTv.setText(downlodsCount.replace("null", "N/A"));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void checkIsFavorite() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {

        } else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseAuth.getUid()).child("Favorites").child(bookId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            isInMyFavoite = snapshot.exists();
                            if (isInMyFavoite) {
                                binding.favoriteBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_baseline_favorite_24, 0, 0);
                                binding.favoriteBtn.setText("Remove Favorite");
                            } else {
                                binding.favoriteBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(0, R.drawable.ic_baseline_favorite_border_24, 0, 0);
                                //binding.favoriteBtn.setText("Add Favorite");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }

    }
}