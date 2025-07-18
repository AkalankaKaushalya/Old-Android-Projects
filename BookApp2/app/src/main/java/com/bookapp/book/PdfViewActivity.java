package com.bookapp.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bookapp.book.databinding.ActivityPdfViewBinding;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PdfViewActivity extends AppCompatActivity {
    ActivityPdfViewBinding binding;
    String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");

        loadBookDetails();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void loadBookDetails() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String pdfUrl = "" + snapshot.child("url").getValue();

                        StorageReference sref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);
                        sref.getBytes(Constants.MAX_BYTES_PDF)
                                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        binding.progressBar.setVisibility(View.GONE);
                                        binding.pdfView.fromBytes(bytes)
                                                .swipeHorizontal(false)
                                                .onPageChange(new OnPageChangeListener() {
                                                    @Override
                                                    public void onPageChanged(int page, int pageCount) {
                                                        int currentPage = (page + 1);
                                                        binding.toolbarSubtitleTv.setText(currentPage + "/" + pageCount);
                                                    }
                                                })
                                                .onError(new OnErrorListener() {
                                                    @Override
                                                    public void onError(Throwable t) {
                                                        Toast.makeText(PdfViewActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .onPageError(new OnPageErrorListener() {
                                                    @Override
                                                    public void onPageError(int page, Throwable t) {
                                                        Toast.makeText(PdfViewActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }).load();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        binding.progressBar.setVisibility(View.GONE);
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}