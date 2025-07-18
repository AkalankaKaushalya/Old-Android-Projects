package com.courseapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.courseapp.Model.Model;
import com.courseapp.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<Model> dataholder;
    public Adapter(ArrayList<Model> dataholder) { // දෙවනනුව මෙය සදා ගන්න මුලින්  ArrayList<Model> dataholder; මෙම කොටස යොද පසුව ඉතුරු කොටස් දමන්න
        this.dataholder = dataholder;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Vname.setText(dataholder.get(position).getUser());
        holder.sub.setText(dataholder.get(position).getSubject());
        holder.mess.setText(dataholder.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }


    // මුලින් කියා තිබේ සාඅදගන්නා ආකරය
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView Vname,sub,mess;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Vname=(TextView)itemView.findViewById(R.id.User);
            sub=(TextView)itemView.findViewById(R.id.Vsubject);
            mess=(TextView)itemView.findViewById(R.id.Vmessage);
        }
    }

}
