package com.skypper.k_mistry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Second_ListActivity extends AppCompatActivity {

    SecondAdapter secondAdapter;
    RecyclerView recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_list);

        recyclerView2 = findViewById(R.id.recycler_2_papers);
           recyclerView2.setLayoutManager(new LinearLayoutManager(this));

          FirebaseRecyclerOptions<SecondModel> options =
                new FirebaseRecyclerOptions.Builder<SecondModel>()
                         .setQuery(FirebaseDatabase.getInstance().getReference().child("Papers_List"), SecondModel.class)
                        .build();

            secondAdapter = new SecondAdapter(options);
           recyclerView2.setAdapter(secondAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        secondAdapter.startListening();
    }
}