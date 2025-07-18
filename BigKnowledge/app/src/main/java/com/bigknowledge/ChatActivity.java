package com.bigknowledge;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigknowledge.Adapters.AdapterChat;
import com.bigknowledge.Models.ModelChat;
import com.bigknowledge.Models.ModelUser;
import com.bigknowledge.Notifications.APIService;
import com.bigknowledge.Notifications.Client;
import com.bigknowledge.Notifications.Data;
import com.bigknowledge.Notifications.Response;
import com.bigknowledge.Notifications.Sender;
import com.bigknowledge.Notifications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView profileIv;
    TextView nameTv,userStatusTv;
    EditText messageEt;
    ImageButton sendBut;

    //FireBase
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ValueEventListener seenListener;

    List<ModelChat> chatList;
    AdapterChat adapterChat;

    String hisUid;
    String myUid;
    String hisImage;

    APIService apiService;
    boolean notify = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        recyclerView = findViewById(R.id.chat_recyclerView);
        profileIv = findViewById(R.id.proifleIv);
        nameTv = findViewById(R.id.nameTv);
        userStatusTv = findViewById(R.id.userStatusTv);
        messageEt = findViewById(R.id.messageEt);
        sendBut = findViewById(R.id.sendBut);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        apiService = Client.getRetrofit("https://fcm.googleapis.com/").create(APIService.class);

        Intent intent  = getIntent();
        hisUid = intent.getStringExtra("hisUid");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        Query userQuery = databaseReference.orderByChild("uid").equalTo(hisUid);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String name = ""+ ds.child("name").getValue();
                    hisImage = ""+ ds.child("image").getValue();

                    String typingStatus = "" + ds.child("typingTo").getValue();
                    if (typingStatus.equals(myUid)){
                        userStatusTv.setText("typing...");
                    }else
                        {
                        String onlineStatus = ""+ ds.child("onlineStatuse").getValue();
                        if (onlineStatus.equals("online")){
                            userStatusTv.setText(onlineStatus);
                        }else {
                            Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                            calendar.setTimeInMillis(Long.parseLong(onlineStatus));
                            String dateTime = DateFormat.format("hh:mm aa", calendar).toString();
                            userStatusTv.setText("Last Seen"+dateTime);//format("dd/MM/yyyy hh:mm aa", calendar)
                        }
                    }



                    nameTv.setText(name);
                    try {
                        Picasso.get().load(hisImage).placeholder(R.drawable.ic_default_img).into(profileIv);
                    }catch (Exception e){
                        Picasso.get().load(R.drawable.ic_default_image).into(profileIv);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = true;
                String message = messageEt.getText().toString().trim();
                if (TextUtils.isEmpty(message)){
                    Toast.makeText(ChatActivity.this, "Empty Chat", Toast.LENGTH_SHORT).show();
                }else {
                    sendMessage(message);
                }
                messageEt.setText("");//මැසෙජ් එක යවිමෙන් පසූ මැසෙජ් ටෙක්ස්ට් එකෙන් එය ණති වී යවී..
            }
        });

        messageEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() ==0){
                    checkTypingStatus("noOne");
                }
                else {
                    checkTypingStatus(hisUid);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        readMessage();

        seenMessage();

    }

    private void seenMessage() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
       seenListener = databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot ds : dataSnapshot.getChildren()){
                   ModelChat chat = ds.getValue(ModelChat.class);
                   if (chat.getReceiver().equals(myUid) && chat.getSender().equals(hisUid)){
                       HashMap<String, Object>hasSeenMap = new HashMap<>();
                       hasSeenMap.put("isSeen", true);
                       ds.getRef().updateChildren(hasSeenMap);
                   }
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }

    private void readMessage() {
        chatList = new ArrayList<>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ModelChat chat  = ds.getValue(ModelChat.class);
                    if (chat.getReceiver().equals(myUid) && chat.getSender().equals(hisUid) ||
                    chat.getReceiver().equals(hisUid) && chat.getSender().equals(myUid))chatList.add(chat);
                }
                adapterChat = new AdapterChat(ChatActivity.this, chatList, hisImage);
                adapterChat.notifyDataSetChanged();
                recyclerView.setAdapter(adapterChat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String message) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String timestamp = String.valueOf(System.currentTimeMillis());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",myUid);
        hashMap.put("receiver",hisUid);
        hashMap.put("message",message);
        hashMap.put("timestamp",timestamp);
        hashMap.put("isSeen",false);
        databaseReference.child("Chats").push().setValue(hashMap);



        String msg = message;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(myUid);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ModelUser user = dataSnapshot.getValue(ModelUser.class);
                if (notify){
                    senNotification(hisUid, user.getName(), message);
                }
                notify = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void senNotification(String hisUid, String name, String message) {
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = allTokens.orderByKey().equalTo(hisUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot ds : snapshot.getChildren()){
                   Token token = ds.getValue(Token.class);
                   Data data = new Data(myUid, name+":"+message, "New Meassage",hisUid, R.drawable.ic_default_img);

                   Sender sender = new Sender(data, token.getToken());
                   apiService.sendNotification(sender)
                           .enqueue(new Callback<Response>() {
                               @Override
                               public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                   Toast.makeText(ChatActivity.this, ""+response.message(), Toast.LENGTH_SHORT).show();
                               }

                               @Override
                               public void onFailure(Call<Response> call, Throwable t) {

                               }
                           });
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            myUid = user.getUid();
        }
        else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void  checkOnlineStatus(String status){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Users").child(myUid);
        HashMap<String, Object>hashMap = new HashMap<>();
        hashMap.put("onlineStatuse", status);

        dbref.updateChildren(hashMap);
    }
    private void  checkTypingStatus(String typing){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Users").child(myUid);
        HashMap<String, Object>hashMap = new HashMap<>();
        hashMap.put("typingTo", typing);

        dbref.updateChildren(hashMap);
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        checkOnlineStatus("online");
        super.onStart();
    }

    @Override
    protected void onPause() {
        String timesatamp = String.valueOf(System.currentTimeMillis());
        checkOnlineStatus(timesatamp);
        checkTypingStatus("noOne");
        databaseReference.removeEventListener(seenListener);
        super.onPause();
    }

    @Override
    protected void onResume() {
        checkOnlineStatus("online");
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_add_post).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logut){
            firebaseAuth.signOut();
            finish();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }
}