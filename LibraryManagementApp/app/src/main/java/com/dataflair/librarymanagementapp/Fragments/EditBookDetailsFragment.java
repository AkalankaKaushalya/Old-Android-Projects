package com.dataflair.librarymanagementapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dataflair.librarymanagementapp.Adapters.BooksCategoryAdapter;
import com.dataflair.librarymanagementapp.Adapters.HomeAdapter;
import com.dataflair.librarymanagementapp.Model.Model;
import com.dataflair.librarymanagementapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class EditBookDetailsFragment extends Fragment {

    RecyclerView recyclerView;
    BooksCategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_book_details, container, false);
        //Assigning the Recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.EditDetailsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //Firebase Recycler Options to get the data form firebase database using model class and reference
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("TotalBooksCategories"), Model.class)
                        .build();


        //Setting adapter to RecyclerView
        adapter = new BooksCategoryAdapter(options);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Starts listening for data from firebase when this fragment starts
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Stops listening for data from firebase
        adapter.stopListening();
    }
}