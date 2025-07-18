package com.ak.unitycode.sl.MoreLodings;


import static com.ak.unitycode.sl.MoreLodings.Constants.MAX_BYTES_ZIP;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class MoreLodings extends Application {


    public static void loadeUserName(String uid, TextView usernameTv) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String username = "" + snapshot.child("Name").getValue();
                        usernameTv.setText(username);
                        //holder.username.setText(username);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public static void ViewContent(String LId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lessons");
        ref.child(LId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String viewContent = "" + snapshot.child("views").getValue();
                        if (viewContent.equals("") || viewContent.equals("null")) {
                            viewContent = "0";
                        }

                        long newViewCount = Long.parseLong(viewContent) + 1;

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("views", "" + newViewCount);

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lessons");
                        ref.child(LId)
                                .updateChildren(hashMap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public static void loadZipSize(String zipUrl, TextView sizeTv) {
        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(zipUrl);
        reference.getMetadata()
                .addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                    @Override
                    public void onSuccess(StorageMetadata storageMetadata) {
                        double bytes = storageMetadata.getSizeBytes();
                        double kb = bytes / 1024;
                        double mb = kb / 1024;
                        if (mb >= 1) {
                            sizeTv.setText(String.format("%.2f", mb) + " MB");
                        } else if (kb >= 1) {
                            sizeTv.setText(String.format("%.2f", mb) + " KB");
                        } else {
                            sizeTv.setText(String.format("%.2f", bytes) + " bytes");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void formatTimestamp(long timestamp, TextView viewdate) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timestamp);

        String data = DateFormat.format("dd/MM/yyyy", calendar).toString();
        viewdate.setText(data);

    }

    public static void CodeViewContent(String codeid) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Codes");
        ref.child(codeid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String viewContent = "" + snapshot.child("viewsCount").getValue();
                        if (viewContent.equals("") || viewContent.equals("null")) {
                            viewContent = "0";
                        }

                        long newViewCount = Long.parseLong(viewContent) + 1;

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("viewsCount", "" + newViewCount);

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Codes");
                        ref.child(codeid)
                                .updateChildren(hashMap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public static void DownloadBook(Context context, String codeId, String codeTitle, String zipUrl) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Downloading " + codeTitle + "...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String nameWithExtension = codeTitle + ".zip";

        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(zipUrl);
        ref.getBytes(MAX_BYTES_ZIP)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        try {
                            File downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                            downloadsFolder.mkdirs();

                            String filePath = downloadsFolder.getPath() + "/" + nameWithExtension;

                            FileOutputStream out = new FileOutputStream(filePath);
                            out.write(bytes);
                            out.close();

                            progressDialog.dismiss();

                            incrementBookDownloadCount(codeId);
                        } catch (Exception e) {

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    private static void incrementBookDownloadCount(String codeId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Codes");
        ref.child(codeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String downlodsCount = "" + snapshot.child("downlodsCount").getValue();

                        if (downlodsCount.equals("") || downlodsCount.equals("null")) {
                            downlodsCount = "0";
                        }

                        long newDownloadsCount = Long.parseLong(downlodsCount) + 1;

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("downlodsCount", "" + newDownloadsCount);

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Codes");
                        ref.child(codeId).updateChildren(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
