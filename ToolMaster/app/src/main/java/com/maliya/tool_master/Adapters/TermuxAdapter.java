package com.maliya.tool_master.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.maliya.tool_master.Models.TermuxModel;
import com.maliya.tool_master.R;
import com.maliya.tool_master.ViewkaliActivity;
import com.maliya.tool_master.databinding.TermxItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TermuxAdapter extends RecyclerView.Adapter<TermuxAdapter.HolderTermux> {

    List<TermuxModel> termuxModels;
    Context context;

    TermxItemBinding binding;

    public TermuxAdapter(Context context, List<TermuxModel> termuxModels) {
        this.context = context;
        this.termuxModels = termuxModels;

    }

    @NonNull
    @Override
    public HolderTermux onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = TermxItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderTermux(binding.getRoot());

    }

    @Override
    public void onBindViewHolder(@NonNull HolderTermux holder, int position) {

        TermuxModel model = termuxModels.get(position);
        String rtname = model.getTitle();
        String tooltype = model.getTooltype();
        String toolimg = model.getUrl();
        String toolid = model.getToolid();


        holder.toolname.setText(rtname);
        holder.rtype.setText(tooltype);
        try {
            Picasso.get().load(toolimg).into(holder.toolimage);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.profile_img).into(holder.toolimage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewkaliActivity.class);
                intent.putExtra("toolid", toolid);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return termuxModels.size();
    }

    class HolderTermux extends RecyclerView.ViewHolder {

        TextView toolname, rtype;
        RoundedImageView toolimage;

        public HolderTermux(@NonNull View itemView) {
            super(itemView);

            toolname = binding.toolnameTv;
            rtype = binding.rtypeTv;
            toolimage = binding.toolimgIv;

        }

    }

}
