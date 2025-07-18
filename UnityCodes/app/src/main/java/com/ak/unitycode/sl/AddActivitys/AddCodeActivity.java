package com.ak.unitycode.sl.AddActivitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ak.unitycode.sl.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AddCodeActivity extends AppCompatActivity {
    private static final int ZIP_PICK_CODE = 1000;
    private static final int IMG_PICK_CODE = 100;
    private EditText ctitle, cdecri;
    private Button addfile, submitbtn;
    private ImageView addimage;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Uri zipUri = null;
    private Uri imageUri = null;
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        imageUri = data.getData();
                        Picasso.get().load(imageUri).into(addimage);
                    } else {
                        Toast.makeText(AddCodeActivity.this, "Cacelled", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_code);
        ctitle = findViewById(R.id.codetitleEt);
        cdecri = findViewById(R.id.codedescriptionEt);
        addfile = findViewById(R.id.addzipfile);
        submitbtn = findViewById(R.id.codesubmitBtn);
        addimage = findViewById(R.id.addprevBtn);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        addfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zipPickIntenr();
            }
        });

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                galleryActivityResultLauncher.launch(intent);
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = ctitle.getText().toString();
                String descri = cdecri.getText().toString();
                if (title.isEmpty()) {
                    Toast.makeText(AddCodeActivity.this, "is Empty Name", Toast.LENGTH_SHORT).show();
                } else if (descri.isEmpty()) {
                    Toast.makeText(AddCodeActivity.this, "is Empty De", Toast.LENGTH_SHORT).show();
                } else if (imageUri == null) {
                    Toast.makeText(AddCodeActivity.this, "is Empty img", Toast.LENGTH_SHORT).show();
                } else if (zipUri == null) {
                    Toast.makeText(AddCodeActivity.this, "is Empty zip", Toast.LENGTH_SHORT).show();
                } else {
                    uploadZipToStorage(title, descri);
                }

            }
        });
    }

    private void uploadZipToStorage(String title, String descri) {
        progressDialog.setMessage("Uplogin Zip File...");
        progressDialog.show();
        long timestampe = System.currentTimeMillis();

        String filePathName = "Codefiles/" + timestampe;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName);
        storageReference.putFile(zipUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        String uploadedPdfUrl = "" + uriTask.getResult();
                        //uplode(uploadedPdfUrl,timestampe);
                        progressDialog.dismiss();
                        uploadPdfInfoToDb(uploadedPdfUrl, timestampe, title, descri);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCodeActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadPdfInfoToDb(String uploadedPdfUrl, long timestampe, String title, String descri) {
        progressDialog.setMessage("Uplogin Image...");
        progressDialog.show();

        String filePathAndName = "CodePriveImages/" + timestampe;
        StorageReference ref = FirebaseStorage.getInstance().getReference(filePathAndName);
        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        String uploadedImageUrl = "" + uriTask.getResult();
                        submitData(uploadedImageUrl, uploadedPdfUrl, timestampe, title, descri);
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCodeActivity.this, "Failed Upload Image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void submitData(String uploadedImageUrl, String uploadedPdfUrl, long timestampe, String title, String descri) {
        progressDialog.setMessage("Uplogin Data info...");
        progressDialog.show();

        String uid = firebaseAuth.getUid();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", "" + uid);
        hashMap.put("CodeID", "" + timestampe);
        hashMap.put("CodeName", "" + title);
        hashMap.put("Codedescr", "" + descri);
        hashMap.put("CodeUrl", "" + uploadedPdfUrl);
        hashMap.put("Preview_img", "" + uploadedImageUrl);
        hashMap.put("timestamp", timestampe);
        hashMap.put("viewsCount", "" + 0);
        hashMap.put("downlodsCount", "" + 0);
        hashMap.put("Likes", "" + 0);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Codes");
        ref.child("" + timestampe)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(AddCodeActivity.this, "Successfully Uploaded...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AddCodeActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void zipPickIntenr() {
        Intent intent = new Intent();
        intent.setType("application/zip");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Zip File"), ZIP_PICK_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ZIP_PICK_CODE) {
                zipUri = data.getData();
            }
        } else {
            Toast.makeText(this, "cancelled picking pdf", Toast.LENGTH_SHORT).show();
        }
    }
}