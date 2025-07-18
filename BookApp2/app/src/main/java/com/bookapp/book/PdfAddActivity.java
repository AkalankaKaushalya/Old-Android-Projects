
package com.bookapp.book;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bookapp.book.databinding.ActivityPdfAddBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class PdfAddActivity extends AppCompatActivity {

    private static final int PDF_PICK_CODE = 1000;
    ActivityPdfAddBinding binding;
    FirebaseAuth firebaseAuth;
    ArrayList<String> categoryTitleArrayList, categoryIdArrayList;
    ProgressDialog progressDialog;
    String TAG = "Add_pdf";
    private Uri pdfUri = null;
    private String title = "", description = "";
    private String selectedCategoryId, selectedCategoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        loadPdfCategories();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.attachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfPickIntenr();
            }
        });

        binding.categoryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryPicerDialog();
            }
        });


        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateDate();
            }
        });

    }

    private void validateDate() {
        title = binding.titleEt.getText().toString();
        description = binding.descriptionEt.getText().toString();


        if (TextUtils.isEmpty(title)) {
            Toast.makeText(PdfAddActivity.this, "Enter Title...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(description)) {
            Toast.makeText(PdfAddActivity.this, "Enter Description...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(selectedCategoryTitle)) {
            Toast.makeText(PdfAddActivity.this, "Enter Category...", Toast.LENGTH_SHORT).show();
        } else if (pdfUri == null) {
            Toast.makeText(PdfAddActivity.this, "Pick PDF...", Toast.LENGTH_SHORT).show();
        } else {
            uploadPdfToStorage();
        }


    }

    private void uploadPdfToStorage() {
        progressDialog.setMessage("Uplogin Pdf info...");
        progressDialog.show();

        long timestampe = System.currentTimeMillis();

        String filePathName = "Books/" + timestampe;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName);
        storageReference.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        String uploadedPdfUrl = "" + uriTask.getResult();

                        iuploadPdfInfoToDb(uploadedPdfUrl, timestampe);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PdfAddActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void iuploadPdfInfoToDb(String uploadedPdfUrl, long timestampe) {
        progressDialog.setMessage("Uploading pdf info....");

        String uid = firebaseAuth.getUid();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", "" + uid);
        hashMap.put("id", "" + timestampe);
        hashMap.put("title", "" + title);
        hashMap.put("description", "" + description);
        hashMap.put("categoryId", "" + selectedCategoryId);
        hashMap.put("url", "" + uploadedPdfUrl);
        hashMap.put("timestamp", timestampe);
        hashMap.put("viewCount", 0);
        hashMap.put("downlodsCount", 0);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child("" + timestampe)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(PdfAddActivity.this, "Successfully Uploaded...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(PdfAddActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void loadPdfCategories() {
        categoryTitleArrayList = new ArrayList<>();
        categoryIdArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryTitleArrayList.clear();
                categoryIdArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String categoryId = "" + ds.child("id").getValue();
                    String categoryTitle = "" + ds.child("category").getValue();

                    categoryTitleArrayList.add(categoryTitle);
                    categoryIdArrayList.add(categoryId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void categoryPicerDialog() {
        String[] categoryArray = new String[categoryTitleArrayList.size()];
        for (int i = 0; i < categoryTitleArrayList.size(); i++) {
            categoryArray[i] = categoryTitleArrayList.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Category")
                .setItems(categoryArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedCategoryTitle = categoryTitleArrayList.get(which);
                        selectedCategoryId = categoryIdArrayList.get(which);
                        binding.categoryTv.setText(selectedCategoryTitle);
                    }
                })
                .show();
    }


    private void pdfPickIntenr() {
        Log.d(TAG, "pdfPickIntent");

        Intent intent = new Intent();
        intent.setType("application/pdf"); //application/pdf
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PDF_PICK_CODE) {
                Log.d(TAG, "onActivityResult: PDF Picerk");

                pdfUri = data.getData();

                Log.d(TAG, "onActivityResult: URI" + pdfUri);
            }
        } else {
            Log.d(TAG, "onActivityResult: cancelled picking pdf");
            Toast.makeText(this, "cancelled picking pdf", Toast.LENGTH_SHORT).show();
        }
    }
}