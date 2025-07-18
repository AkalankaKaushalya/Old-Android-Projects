package com.myschool.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myschool.Models.TNotesModel;
import com.myschool.R;
import com.myschool.TNoteViewActivity;
import com.myschool.databinding.TheachrNotesRowBinding;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TNotesAdapter extends RecyclerView.Adapter<TNotesAdapter.HolderTNotes> {

    Context context;
    List<TNotesModel> tNotesModelList;
    TheachrNotesRowBinding binding;

    public TNotesAdapter(Context context, List<TNotesModel> tNotesModelList) {
        this.context = context;
        this.tNotesModelList = tNotesModelList;
    }

    @NonNull
    @Override
    public HolderTNotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = TheachrNotesRowBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderTNotes(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderTNotes holder, int position) {

        TNotesModel model = tNotesModelList.get(position);
        String title = model.getTitle();
        String subject = model.getSubject();
        String nid = model.getNID();
        long ldate = model.getDate();

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(ldate);

        String data = DateFormat.format("dd-MM-yyyy", calendar).toString();

        holder.Tilte.setText(title);
        holder.Massage.setText(subject);
        holder.Date.setText(data);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TNoteViewActivity.class);
                intent.putExtra("Date", nid);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tNotesModelList.size();
    }

    static class HolderTNotes extends RecyclerView.ViewHolder {
        TextView Tilte, Massage, Date;

        public HolderTNotes(@NonNull View itemView) {
            super(itemView);

            Tilte = itemView.findViewById(R.id.tntitleTv);
            Massage = itemView.findViewById(R.id.tnsubjectTv);
            Date = itemView.findViewById(R.id.tndateTv);
        }
    }
}
