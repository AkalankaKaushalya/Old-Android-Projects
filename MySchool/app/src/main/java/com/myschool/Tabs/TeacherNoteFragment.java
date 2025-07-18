package com.myschool.Tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myschool.Adapters.TNotesAdapter;
import com.myschool.Models.TNotesModel;
import com.myschool.R;

import java.util.ArrayList;
import java.util.List;


public class TeacherNoteFragment extends Fragment {

    RecyclerView TNoteRv;
    TNotesModel tNotesModel;

    TNotesAdapter tNotesAdapter;
    List<TNotesModel> tNotesModelList;

    public TeacherNoteFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_teacher_note, container, false);

        TNoteRv = view.findViewById(R.id.techerNotsRv);
        TNoteRv.setHasFixedSize(true);
        TNoteRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        tNotesModelList = new ArrayList<>();


        lodeNotes();

        return view;
    }

    private void lodeNotes() {
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lesons");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tNotesModelList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    TNotesModel model = ds.getValue(TNotesModel.class);

                    tNotesModelList.add(model);


                    tNotesAdapter = new TNotesAdapter(getActivity(), tNotesModelList);
                    TNoteRv.setAdapter(tNotesAdapter);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}