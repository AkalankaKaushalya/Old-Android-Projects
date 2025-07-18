package com.maliya.tool_master.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maliya.tool_master.Adapters.KaliAdapter;
import com.maliya.tool_master.Models.KaliModel;
import com.maliya.tool_master.R;

import java.util.ArrayList;
import java.util.List;


public class KaliFragment extends Fragment {

    RecyclerView terRv;

    KaliAdapter kaliAdapter;
    List<KaliModel> kaliModels;
    StaggeredGridLayoutManager gridLayoutManager;

    ProgressBar progressBar;

    public KaliFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kali, container, false);
        terRv = view.findViewById(R.id.kaliRv);
        progressBar = view.findViewById(R.id.progressbar);
        terRv.setHasFixedSize(true);

        terRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        // gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        /// terRv.setLayoutManager(gridLayoutManager);

        kaliModels = new ArrayList<>();

        loadTools();

        return view;
    }

    private void loadTools() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Kali");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //kaliModels.clear();
                progressBar.setVisibility(View.GONE);
                for (DataSnapshot ds : snapshot.getChildren()) {

                    KaliModel model = ds.getValue(KaliModel.class);

                    kaliModels.add(model);

                    //kaliAdapter = new KaliAdapter(getActivity(), kaliModels);
                    //terRv.setAdapter(kaliAdapter);
                    kaliAdapter = new KaliAdapter(kaliModels, getActivity());
                    terRv.setAdapter(kaliAdapter);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadTools();
    }

    @Override
    public void onPause() {
        super.onPause();
        loadTools();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadTools();
    }

    @Override
    public void onStop() {
        super.onStop();
        loadTools();
    }
}