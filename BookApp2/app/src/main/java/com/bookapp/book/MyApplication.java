package com.bookapp.book;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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

import static com.bookapp.book.Constants.MAX_BYTES_PDF;

public class MyApplication extends Application {


    String nameWithExtension;

    public static final String formatTimestamp(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timestamp);

        String data = DateFormat.format("dd/MM/yyyy", calendar).toString();

        return data;
    }

    public static void loadPdfSize(String pdfUrl, String pdfTitle, TextView sizeTv) {
        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);
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

    public static void loadPdfFromUrlSinglePage(String pdfUrl, String pdfTitle, PDFView pdfView, ProgressBar progressBar) {
        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);
        ref.getBytes(MAX_BYTES_PDF)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        pdfView.fromBytes(bytes)
                                .pages(0)
                                .spacing(0)
                                .swipeHorizontal(false)
                                .enableSwipe(false)
                                .onError(new OnErrorListener() {
                                    @Override
                                    public void onError(Throwable t) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                })
                                .onPageError(new OnPageErrorListener() {
                                    @Override
                                    public void onPageError(int page, Throwable t) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                })
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                })
                                .load();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    public static void loadCategory(String categoryId, TextView categoryTv) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.child(categoryId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String category = "" + snapshot.child("category").getValue();
                        categoryTv.setText(category);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public static void BookViewContent(String bookId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String viewContent = "" + snapshot.child("viewCount").getValue();
                        if (viewContent.equals("") || viewContent.equals("null")) {
                            viewContent = "0";
                        }

                        long newViewCount = Long.parseLong(viewContent) + 1;

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("viewCount", newViewCount);

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
                        ref.child(bookId)
                                .updateChildren(hashMap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public static void DownloadBook(Context context, String bookId, String bookTitle, String bookUrl) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Downloading" + bookTitle + "...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String nameWithExtension = bookTitle + ".pdf";

        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(bookUrl);
        ref.getBytes(MAX_BYTES_PDF)
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

                            incrementBookDownloadCount(bookId);
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

    public static void incrementBookDownloadCount(String bookId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String downlodsCount = "" + snapshot.child("downlodsCount").getValue();

                        if (downlodsCount.equals("") || downlodsCount.equals("null")) {
                            downlodsCount = "0";
                        }

                        long newDownloadsCount = Long.parseLong(downlodsCount) + 1;

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("downlodsCount", newDownloadsCount);

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
                        ref.child(bookId).updateChildren(hashMap)
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

    public static void loadPdfPageCount(Context context, String pdfUrl, TextView pagesTv) {
        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);
        ref.getBytes(Constants.MAX_BYTES_PDF)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        PDFView pdfView = new PDFView(context, null);
                        pdfView.fromBytes(bytes)
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {
                                        pagesTv.setText("" + nbPages);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    public static void addToFavorite(Context context, String bookId) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(context, "Your Not logged in", Toast.LENGTH_SHORT).show();
        } else {
            long timestamp = System.currentTimeMillis();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("bookId", "" + bookId);
            hashMap.put("timestamp", "" + timestamp);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseAuth.getUid()).child("Favorites").child(bookId)
                    .setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Add to your Favorites list", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Failed to add to Favorites due to", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public static void removeFromFavorite(Context context, String bookId) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(context, "Your Not logged in", Toast.LENGTH_SHORT).show();
        } else {
            long timestamp = System.currentTimeMillis();


            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseAuth.getUid()).child("Favorites").child(bookId)
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Remove your Favorites list", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Failed to Remove to Favorites due to", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

}
