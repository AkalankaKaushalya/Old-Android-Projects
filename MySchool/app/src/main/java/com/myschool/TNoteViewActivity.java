package com.myschool;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myschool.databinding.ActivityTNoteViewBinding;

public class TNoteViewActivity extends AppCompatActivity {
    ActivityTNoteViewBinding binding;
    String Dlode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTNoteViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        Dlode = intent.getStringExtra("Date");


        lodeNotes();


    }

    private void lodeNotes() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lesons");
        ref.child(Dlode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String title = "" + snapshot.child("Title").getValue();
                String description = "" + snapshot.child("Subject").getValue();

                binding.sTNoteTv.setText(title);
                binding.sTNotedesTv.setText(description);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}