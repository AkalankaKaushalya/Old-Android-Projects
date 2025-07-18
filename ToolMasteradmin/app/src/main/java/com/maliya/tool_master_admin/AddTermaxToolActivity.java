package com.maliya.tool_master_admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.maliya.tool_master_admin.Notifi.FcmNotificationsSender;
import com.maliya.tool_master_admin.databinding.ActivityAddTermaxToolBinding;
import com.maliya.tool_master_admin.databinding.ActivityMainBinding;

import java.util.HashMap;

public class AddTermaxToolActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
ActivityAddTermaxToolBinding binding;
    Uri uri = null;
    private static final int IMAGE_PICK_CODE = 1000;

    String toolname ="", description ="", command="", rtyoe;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTermaxToolBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseMessaging.getInstance().subscribeToTopic("all");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);

        Spinner spin = (Spinner) findViewById(R.id.ttype);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        binding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.setType("image/*"); //application/pdf
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), IMAGE_PICK_CODE);

            }
        });

        binding.uplodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolname = binding.toolnameEt.getText().toString();
                description = binding.tooldescripEt.getText().toString();
                command = binding.toolcommnedEt.getText().toString();

                if (TextUtils.isEmpty(toolname)) {
                    Toast.makeText(AddTermaxToolActivity.this, "Enter Title...", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(description)) {
                    Toast.makeText(AddTermaxToolActivity.this, "Enter Description...", Toast.LENGTH_SHORT).show();
                } else if (rtyoe.equals("Select Type")){
                    Toast.makeText(AddTermaxToolActivity.this, "Select Root Type...", Toast.LENGTH_SHORT).show();
                } else if (uri == null) {
                    Toast.makeText(AddTermaxToolActivity.this, "Pick image...", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImageToStorage();
                }
            }
        });





    }

    private void uploadImageToStorage() {
        progressDialog.setMessage("Uplogin Image...");
        progressDialog.show();
        long timestampe = System.currentTimeMillis();

        String filePathName = "Termaximg/" + timestampe;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName);
        storageReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        String uploadedPdfUrl = "" + uriTask.getResult();

                        iuploadDataInfoToDb(uploadedPdfUrl, timestampe);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AddTermaxToolActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void iuploadDataInfoToDb(String uploadedPdfUrl, long timestampe) {
        //String uid = firebaseAuth.getUid();
        progressDialog.setMessage("Uploding Tools...");
        progressDialog.show();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", "");
        hashMap.put("toolid", "" + timestampe);
        hashMap.put("title", "" + toolname);
        hashMap.put("description", "" + description);
        hashMap.put("commend", "" + command);
        hashMap.put("tooltype","" + rtyoe);
        hashMap.put("url", "" + uploadedPdfUrl);
        hashMap.put("timestamp", timestampe);
        hashMap.put("viewCount", 0);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Termux");
        ref.child("" + timestampe)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",
                                binding.toolnameEt.getText().toString(),
                                binding.tooldescripEt.getText().toString(), getApplicationContext(), AddTermaxToolActivity.this);
                        notificationsSender.SendNotifications();
                        Toast.makeText(AddTermaxToolActivity.this, "Successfully Uploaded...", Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AddTermaxToolActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
       // Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
        rtyoe = text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_CODE) {
                //Log.d(TAG, "onActivityResult: PDF Picerk");

                uri = data.getData();

                //Log.d(TAG, "onActivityResult: URI" + pdfUri);
            }
        } else {
           // Log.d(TAG, "onActivityResult: cancelled picking pdf");
            Toast.makeText(this, "cancelled picking Image", Toast.LENGTH_SHORT).show();
        }
    }
}