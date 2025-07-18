package com.bookapp.book.Adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bookapp.book.Filters.FilterPdfAdmin;
import com.bookapp.book.Models.ModelPdf;
import com.bookapp.book.MyApplication;
import com.bookapp.book.PdfDetailActivity;
import com.bookapp.book.PdfEditActivity;
import com.bookapp.book.databinding.RowPdfAdminBinding;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AdapterPdfAdmin extends RecyclerView.Adapter<AdapterPdfAdmin.HolderPdfAdmin> implements Filterable {
    public ArrayList<ModelPdf> pdfArrayList, filterList;
    Context context;
    RowPdfAdminBinding binding;

    FilterPdfAdmin filter;

    ProgressDialog progressDialog;


    public AdapterPdfAdmin(Context context, ArrayList<ModelPdf> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
        this.filterList = pdfArrayList;

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Pleas wait");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @NonNull
    @Override
    public HolderPdfAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowPdfAdminBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderPdfAdmin(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPdfAdmin holder, int position) {

        ModelPdf model = pdfArrayList.get(position);

        String pdfId = model.getId();
        String titel = model.getTitle();
        String description = model.getDescription();
        String categoryId = model.getCategoryId();
        String pdfurl = model.getUrl();
        long timestamp = model.getTimestamp();
        String formattedData = MyApplication.formatTimestamp(timestamp);

        holder.titelTv.setText(titel);
        holder.descriptionTv.setText(description);
        holder.dataTv.setText(formattedData);


        MyApplication.loadPdfSize("" + pdfurl, "" + titel, holder.sizeTv);
        MyApplication.loadPdfFromUrlSinglePage("" + pdfurl, "" + titel, holder.pdfView, holder.progressBar);  //මෙයි කතා වෙන්නෙ MyApplication ක්ලාස් එකෙ පබ්ලික් කර ඇති
        MyApplication.loadCategory("" + categoryId, holder.categoryTv);                              //මෙතර්ඩ්ස් කොල් කිරිමයි..


        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookId = model.getId();
                String bookUrl = model.getUrl();
                String bookTitle = model.getTitle();

                String[] option = {"Edit", "Delete"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose Options")
                        .setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent = new Intent(context, PdfEditActivity.class);
                                    intent.putExtra("bookId", bookId);
                                    context.startActivity(intent);
                                } else if (which == 1) {
                                    String bookId = model.getId();
                                    String bookUrl = model.getUrl();
                                    String bookTitle = model.getTitle();
                                    progressDialog.setMessage("Deleting " + bookTitle + " ...");
                                    progressDialog.show();
                                    StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(bookUrl);
                                    ref.delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books");
                                                    reference.child(bookId)
                                                            .removeValue()
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(context, "Deleted Successfully..", Toast.LENGTH_SHORT).show();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(context, "Deleted Error..", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
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
                        })
                        .show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PdfDetailActivity.class);
                intent.putExtra("bookId", pdfId);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return pdfArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FilterPdfAdmin(filterList, this);
        }
        return filter;
    }


    class HolderPdfAdmin extends RecyclerView.ViewHolder {
        PDFView pdfView;
        ProgressBar progressBar;
        TextView titelTv, descriptionTv, categoryTv, sizeTv, dataTv;
        ImageButton moreBtn;

        public HolderPdfAdmin(@NonNull View itemView) {
            super(itemView);

            pdfView = binding.pdfView;
            progressBar = binding.progressBar;
            titelTv = binding.titleTv;
            descriptionTv = binding.descriptionTv;
            categoryTv = binding.categoryTv;
            sizeTv = binding.sizeTv;
            dataTv = binding.dateTv;
            moreBtn = binding.moreBtn;
        }
    }
}
