package com.maliya.tool_master.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maliya.tool_master.Models.KaliModel;
import com.maliya.tool_master.ViewTermaxActivity;
import com.maliya.tool_master.databinding.KaliItemBinding;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class KaliAdapter extends RecyclerView.Adapter<KaliAdapter.HolderKali> {
    KaliItemBinding binding;
    List<KaliModel> kaliModels;
    Context context;

    public KaliAdapter(List<KaliModel> kaliModels, Context context) {
        this.kaliModels = kaliModels;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderKali onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = KaliItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderKali(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderKali holder, int position) {
        KaliModel model = kaliModels.get(position);
        String rtname = model.getTitle();
        Long date = model.getTimestamp();
        String tooltype = model.getTooltype();
        String toolid = model.getToolid();


        holder.toolname.setText(rtname);
        holder.rtype.setText(tooltype);

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(date);

        String data = DateFormat.format("dd-MM-yyyy", calendar).toString();
        holder.dateTv.setText(data);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewTermaxActivity.class);
                intent.putExtra("toolid", toolid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kaliModels.size();
    }

    class HolderKali extends RecyclerView.ViewHolder {

        TextView toolname, rtype, dateTv;

        public HolderKali(@NonNull View itemView) {
            super(itemView);

            toolname = binding.toolnameTv;
            rtype = binding.rtypeTv;
            dateTv = binding.uplodeDateTv;

        }

    }
}
