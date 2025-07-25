package com.bigknowledge.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigknowledge.Models.ModelChat;
import com.bigknowledge.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.MyHolder>{

    FirebaseUser firebaseUser;

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPE_RIGHT = 1;
    Context context;
    List<ModelChat>chatList;
    String imageUri; // ජෙනරෙට් හරහා කොන්ස්ට්‍රක්ටර් කරලා පලහල කොටස ලබගන්නෙ....

    public AdapterChat(Context context, List<ModelChat> chatList, String imageUri) {
        this.context = context;
        this.chatList = chatList;
        this.imageUri = imageUri;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, viewGroup, false);
            return new MyHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, viewGroup, false);
            return new MyHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        String message = chatList.get(i).getMessage();
        String timeStamp = chatList.get(i).getTimestamp();

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(timeStamp));
        String dateTime = DateFormat.format("hh:mm aa", calendar).toString();  //format("dd/MM/yyyy hh:mm aa", calendar)

        myHolder.messageTv.setText(message);
        myHolder.timeTv.setText(dateTime);
        try {
            Picasso.get().load(imageUri).into(myHolder.profileIv);
        }catch (Exception e){
        }

        /*myHolder.messageLAyout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Your Sure too delete Message");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                             deletMessage(i);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        });*/
        myHolder.messageLAyout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Your Sure too delete Message");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletMessage(i);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        if (i==chatList.size()-1) {
            if (chatList.get(i).isSeen()) {
                myHolder.isSeenTv.setText("Seen");
            } else {
                myHolder.isSeenTv.setText("Delivered");
            }
        }else {
            myHolder.isSeenTv.setVisibility(View.GONE);
        }
    }

    private void deletMessage(int position) {
        String myUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String msgTimeStamp = chatList.get(position).getTimestamp();
        DatabaseReference dbRef  = FirebaseDatabase.getInstance().getReference("Chats");
        Query query = dbRef.orderByChild("timestamp").equalTo(msgTimeStamp);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    if (ds.child("sender").getValue().equals(myUID)){
                        //ds.getRef().removeValue();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("message", "This Message was Deleted...");
                        ds.getRef().updateChildren(hashMap);
                        Toast.makeText(context, "Message Was Deleted", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "You can delete only your Message..", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chatList.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }

    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView profileIv;
        TextView messageTv, timeTv, isSeenTv;
        LinearLayout messageLAyout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            profileIv = itemView.findViewById(R.id.proifleIv);
            messageTv = itemView.findViewById(R.id.messageTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            isSeenTv = itemView.findViewById(R.id.isSeentTv);
            messageLAyout = itemView.findViewById(R.id.messageLayout);

        }
    }
}
