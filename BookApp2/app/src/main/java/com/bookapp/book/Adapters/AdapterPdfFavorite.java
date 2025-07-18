package com.bookapp.book.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bookapp.book.Models.ModelPdf;
import com.bookapp.book.MyApplication;
import com.bookapp.book.PdfDetailActivity;
import com.bookapp.book.databinding.RowPdfFavoriteBinding;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterPdfFavorite extends RecyclerView.Adapter<AdapterPdfFavorite.HloderPdfFavorite> {

    Context context;
    ArrayList<ModelPdf> pdfArrayList;

    RowPdfFavoriteBinding binding;

    public AdapterPdfFavorite(Context context, ArrayList<ModelPdf> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
    }

    @NonNull
    @Override
    public HloderPdfFavorite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowPdfFavoriteBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HloderPdfFavorite(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HloderPdfFavorite holder, int position) {
        ModelPdf model = pdfArrayList.get(position);


        loadBookDetails(model, holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PdfDetailActivity.class);
                intent.putExtra("bookId", model.getId());
                context.startActivity(intent);
            }
        });

        holder.removeFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.removeFromFavorite(context, model.getId());
            }
        });
    }

    private void loadBookDetails(ModelPdf model, HloderPdfFavorite holder) {
        String bookId = model.getId();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String bookTitle = "" + snapshot.child("title").getValue();
                        String description = "" + snapshot.child("description").getValue();
                        String categoryId = "" + snapshot.child("categoryId").getValue();
                        String bookUrl = "" + snapshot.child("url").getValue();
                        String uid = "" + snapshot.child("uid").getValue();
                        String timestamp = "" + snapshot.child("timestamp").getValue();
                        String viewCount = "" + snapshot.child("viewCount").getValue();
                        String downlodsCount = "" + snapshot.child("downlodsCount").getValue();

                        model.setFavorite(true);
                        model.setTitle(bookTitle);
                        model.setDescription(description);
                        model.setTimestamp(Long.parseLong(timestamp));
                        model.setCategoryId(categoryId);
                        model.setUid(uid);
                        model.setUrl(bookUrl);

                        String date = MyApplication.formatTimestamp(Long.parseLong(timestamp));
                        MyApplication.loadPdfSize("" + bookUrl, "" + bookTitle, binding.sizeTv);
                        MyApplication.loadPdfFromUrlSinglePage("" + bookUrl, "" + bookTitle, binding.pdfView, binding.progressBar);  //මෙයි කතා වෙන්නෙ MyApplication ක්ලාස් එකෙ පබ්ලික් කර ඇති
                        MyApplication.loadCategory("" + categoryId, binding.categoryTv);//මෙතර්ඩ්ස් කොල් කිරිමයි..

                        holder.titelTv.setText(bookTitle);
                        holder.descriptionTv.setText(description);
                        holder.dataTv.setText(date);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return pdfArrayList.size();
    }

    class HloderPdfFavorite extends RecyclerView.ViewHolder {

        PDFView pdfView;
        ProgressBar progressBar;
        TextView titelTv, descriptionTv, categoryTv, sizeTv, dataTv;
        ImageButton removeFavBtn;

        public HloderPdfFavorite(@NonNull View itemView) {
            super(itemView);

            pdfView = binding.pdfView;
            progressBar = binding.progressBar;
            titelTv = binding.titleTv;
            descriptionTv = binding.descriptionTv;
            categoryTv = binding.categoryTv;
            removeFavBtn = binding.removeFavBtn;
            sizeTv = binding.sizeTv;
            dataTv = binding.dateTv;
        }
    }
}
