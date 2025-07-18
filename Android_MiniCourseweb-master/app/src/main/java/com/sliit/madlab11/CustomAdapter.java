package com.sliit.madlab11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    ArrayList<String> users, subjects, messages;
    Context mContext;

    public CustomAdapter(ArrayList<String> users, ArrayList<String> subjects, ArrayList<String> messages, Context mContext) {
        this.users = users;
        this.subjects = subjects;
        this.messages = messages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {

        holder.userName.setText(String.valueOf(users.get(position)));
        holder.subjectName.setText(String.valueOf(subjects.get(position)));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Message.class);
                intent.putExtra("user",String.valueOf(users.get(position)));
                intent.putExtra("subject",String.valueOf(subjects.get(position)));
                intent.putExtra("message",String.valueOf(messages.get(position)));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        TextView subjectName, userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.parent_layout);
            subjectName = itemView.findViewById(R.id.txt_sub);
            userName = itemView.findViewById(R.id.txt_user);
        }
    }
}
