package com.myschool.Tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myschool.Adapters.GchatAdapter;
import com.myschool.Adapters.UserAdapter;
import com.myschool.Models.ChatModel;
import com.myschool.Models.UsersModel;
import com.myschool.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GroupChatFragment extends Fragment {

    ImageButton Sender;
    EditText editText;
    FirebaseAuth firebaseAuth;

    RecyclerView userRv;

    GchatAdapter gchatAdapter;
    List<ChatModel> chatModelList;

    UserAdapter userAdapter;
    List<UsersModel> usersModelList;

    String name = "";
    String image = "";


    public GroupChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_chat, container, false);

        Sender = view.findViewById(R.id.sendBtn);
        editText = view.findViewById(R.id.masseEt);

        firebaseAuth = FirebaseAuth.getInstance();

        userRv = view.findViewById(R.id.chatRcv);
        userRv.setHasFixedSize(true);
        userRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        chatModelList = new ArrayList<>();

        loadChat();

        lodeNotes();


        Sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mass = editText.getText().toString();

                if (mass.isEmpty()) {
                    Toast.makeText(getContext(), "Enter Massage", Toast.LENGTH_SHORT).show();
                } else {
                    long timestamp = System.currentTimeMillis();

                    String uid = firebaseAuth.getUid();

                    String chatId = uid + timestamp;

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("uid", uid);
                    hashMap.put("chatId", chatId);
                    hashMap.put("Uname", name);
                    hashMap.put("Massages", mass);
                    hashMap.put("Time", timestamp);
                    hashMap.put("ProfileImg", image);

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Gchat");
                    ref.child(chatId)
                            .setValue(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(getContext(), "Send..", Toast.LENGTH_SHORT).show();
                                    editText.setText(null);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });


        return view;
    }

    private void lodeNotes() {

        String uid = firebaseAuth.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name = "" + snapshot.child("name").getValue();
                image = "" + snapshot.child("profileImage").getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadChat() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Gchat");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatModelList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ChatModel model = ds.getValue(ChatModel.class);

                    chatModelList.add(model);

                    gchatAdapter = new GchatAdapter(getActivity(), chatModelList);
                    userRv.setAdapter(gchatAdapter);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}