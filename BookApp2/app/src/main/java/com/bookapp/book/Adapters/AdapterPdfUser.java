package com.bookapp.book.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bookapp.book.Filters.FilterPdfUser;
import com.bookapp.book.Models.ModelPdf;
import com.bookapp.book.MyApplication;
import com.bookapp.book.PdfDetailActivity;
import com.bookapp.book.databinding.RowPdfUserBinding;
import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

public class AdapterPdfUser extends RecyclerView.Adapter<AdapterPdfUser.HolderPdfUser> implements Filterable {

    public Context context;
    public ArrayList<ModelPdf> pdfArrayList, filterList;

    RowPdfUserBinding binding;

    FilterPdfUser filter;

    ProgressDialog progressDialog;

    public AdapterPdfUser(Context context, ArrayList<ModelPdf> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
        this.filterList = pdfArrayList;
    }

    @NonNull
    @Override
    public HolderPdfUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowPdfUserBinding.inflate(LayoutInflater.from(context), parent, false);
        return new AdapterPdfUser.HolderPdfUser(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPdfUser holder, int position) {
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
        MyApplication.loadCategory("" + categoryId, holder.categoryTv);


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

    public Filter getFilter() {
        if (filter == null) {
            filter = new FilterPdfUser(filterList, this);
        }
        return filter;
    }

    class HolderPdfUser extends RecyclerView.ViewHolder {
        PDFView pdfView;
        ProgressBar progressBar;
        TextView titelTv, descriptionTv, categoryTv, sizeTv, dataTv;

        public HolderPdfUser(@NonNull View itemView) {
            super(itemView);

            pdfView = binding.pdfView;
            progressBar = binding.progressBar;
            titelTv = binding.titleTv;
            descriptionTv = binding.descriptionTv;
            categoryTv = binding.categoryTv;
            sizeTv = binding.sizeTv;
            dataTv = binding.dateTv;

        }
    }
}
