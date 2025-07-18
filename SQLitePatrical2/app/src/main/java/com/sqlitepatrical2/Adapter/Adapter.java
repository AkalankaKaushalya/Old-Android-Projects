package com.sqlitepatrical2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sqlitepatrical2.Model.Model;
import com.sqlitepatrical2.R;

import java.util.ArrayList;

// මුලින්ම අපි ViewHolder එක්ක සදා ගත යුතු වේ Adapter calls ඒක තුලම
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

        holder.Vname.setText(dataholder.get(position).getUserName());
        holder.Vage.setText(dataholder.get(position).getAge());
        holder.Vnic.setText(dataholder.get(position).getNIC());
        holder.Vemail.setText(dataholder.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }


// මුලින් කියා තිබේ සාඅදගන්නා ආකරය
    class ViewHolder extends RecyclerView.ViewHolder {
    TextView Vname, Vnic, Vage, Vemail;

    public ViewHolder(@NonNull View itemView) {
          super(itemView);
              Vname=(TextView)itemView.findViewById(R.id.Vname);
              Vage=(TextView)itemView.findViewById(R.id.Vage);
              Vnic=(TextView)itemView.findViewById(R.id.Vnic);
              Vemail=(TextView)itemView.findViewById(R.id.Vemail);

      }
  }
}
