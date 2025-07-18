package com.ckfood.pvt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ckfood.pvt.Models.BreadModels;
import com.ckfood.pvt.ViewHolders.BreadViewHolders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Set;


public class OurMenu extends Fragment {
    LinearLayout B_but,F_but,D_but,R_but,B_Liner,F_Liner,D_Liner,R_Liner;
    ImageView B_img,F_img,D_img,R_img;
    RecyclerView Brviwe,Frviwe,Drviwe,Rrviwe;

    FirebaseDatabase Firebasedatabase;
    DatabaseReference Databasereference;


    public OurMenu() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_our_menu, container, false);

        B_but = view.findViewById(R.id.bread_but);
        F_but = view.findViewById(R.id.fastfood_but);
        D_but = view.findViewById(R.id.drank_but);
        R_but = view.findViewById(R.id.raise_but);
        B_Liner = view.findViewById(R.id.breadpart);
        // F_Liner = view.findViewById(R.id.fastfoodpart);
        // D_Liner = view.findViewById(R.id.drinkpart);
        // R_Liner = view.findViewById(R.id.riasepart);
        B_img = view.findViewById(R.id.bimg);
        F_img = view.findViewById(R.id.fimg);
        D_img = view.findViewById(R.id.dimg);
        R_img = view.findViewById(R.id.rimg);

        Brviwe= view.findViewById(R.id.recybread);

        Brviwe.setLayoutManager(new LinearLayoutManager(getActivity()));
        Firebasedatabase = FirebaseDatabase.getInstance();
        Databasereference = Firebasedatabase.getReference("BreadMenu");


        B_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Hello ", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<BreadModels, BreadViewHolders> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BreadModels, BreadViewHolders>(
                BreadModels.class,
                R.layout.bread_row,
                BreadViewHolders.class,
                Databasereference
        ) {
            @Override
            protected void populateViewHolder(BreadViewHolders breadViewHolders, BreadModels breadModels, int i) {
                breadViewHolders.setDetails(getActivity(), breadModels.getBname(), breadModels.getBdis(), breadModels.getBprice(), breadModels.getBimg());
            }
        };
        Brviwe.setAdapter(firebaseRecyclerAdapter);
    }
}