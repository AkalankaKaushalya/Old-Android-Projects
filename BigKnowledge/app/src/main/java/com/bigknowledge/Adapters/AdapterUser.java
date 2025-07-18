package com.bigknowledge.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigknowledge.ChatActivity;
import com.bigknowledge.Models.ModelUser;
import com.bigknowledge.R;
import com.bigknowledge.ThereProfileActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyHolder> {

    Context context;
    List<ModelUser> userList; //කොන්ස්ට්‍රක්ට් යොදා පහල ඇඩප්ටයුසර් කොන්ටෙක්ට් පහසුකම ලබගන්න

    public AdapterUser(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        String hitUID = userList.get(i).getUid();
        String userImage = userList.get(i).getImage();
        String userName = userList.get(i).getName();
        String userEmail = userList.get(i).getEmail();

        myHolder.mNameTv.setText(userName);
        myHolder.mEmailTv.setText(userEmail);
        try {
            Picasso.get().load(userImage).placeholder(R.drawable.ic_default_img).into(myHolder.mAvatarIv);
        }catch (Exception e){

        }
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Shhow Dilog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setItems(new String[]{"Profile", "Chat"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       if (which==0){
                           Intent intent = new Intent(context, ThereProfileActivity.class);
                           intent.putExtra("uid", hitUID);
                           context.startActivity(intent);
                       }
                       if (which==1){
                           //chat Clikd
                           //Toast.makeText(context, ""+userEmail, Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(context, ChatActivity.class);
                           intent.putExtra("hisUid", hitUID);
                           context.startActivity(intent);
                       }
                    }
                });
                builder.create().show();
            }
        });

       /* myHolder.mAvatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ThereProfileActivity.class);
                intent.putExtra("uid", hitUID);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView mAvatarIv;
        TextView mNameTv, mEmailTv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv = itemView.findViewById(R.id.nameTv);
            mEmailTv = itemView.findViewById(R.id.emailTv);

        }
    }
}
