package com.myschool.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myschool.Models.ChatModel;
import com.myschool.R;
import com.myschool.databinding.ChatRowBinding;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GchatAdapter extends RecyclerView.Adapter<GchatAdapter.HolderGChata> {
    Context context;
    List<ChatModel> chatModelList;
    //List<UsersModel> usersModelList;
    ChatRowBinding binding;
    FirebaseAuth firebaseAuth;

    public GchatAdapter(Context context, List<ChatModel> chatModelList) {
        this.context = context;
        this.chatModelList = chatModelList;
        // this.usersModelList = usersModelList;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public HolderGChata onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ChatRowBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderGChata(binding.getRoot());

    }

    @Override
    public void onBindViewHolder(@NonNull HolderGChata holder, int position) {
        ChatModel model = chatModelList.get(position);
        String masseg = model.getMassages();
        String name = model.getUname();
        String uid = model.getUid();
        long time = model.getTime();

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);

        String data = DateFormat.format("hh-mm", calendar).toString();


        holder.Time.setText(data);
        holder.Message.setText(masseg);
        holder.Username.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firebaseAuth.getCurrentUser() != null && uid.equals(firebaseAuth.getUid())) {
                    deleteComment(model, holder);
                }

            }
        });
    }

    private void deleteComment(ChatModel model, HolderGChata holder) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are Your Suver Delete");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Gchat");
                ref.child(model.getChatId())
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "Deleted..", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // Show Delete Dialog //
        builder.create().show();

    }

   /* private void loadeUser(String uid) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = "" + snapshot.child("name").getValue();
                //String description = "" + snapshot.child("Subject").getValue();

                binding.usernameTv.setText(name);
               // binding.sTNotedesTv.setText(description);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/


    @Override
    public int getItemCount() {
        return chatModelList.size();
    }

    static class HolderGChata extends RecyclerView.ViewHolder {
        TextView Message, Username, Time;

        public HolderGChata(@NonNull View itemView) {
            super(itemView);

            Message = itemView.findViewById(R.id.chatbodyTv);
            Username = itemView.findViewById(R.id.usernameTv);
            Time = itemView.findViewById(R.id.timeTv);
        }
    }
}
