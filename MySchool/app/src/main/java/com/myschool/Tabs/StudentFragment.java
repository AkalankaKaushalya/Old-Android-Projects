package com.myschool.Tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myschool.Adapters.UserAdapter;
import com.myschool.Models.UsersModel;
import com.myschool.R;

import java.util.ArrayList;
import java.util.List;


public class StudentFragment extends Fragment {


    // ArrayList<UsersModel> usersModels;
    RecyclerView userRv;

    UserAdapter userAdapter;
    List<UsersModel> usersModelList;


    public StudentFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        //usersModels = new ArrayList<>();

        userRv = view.findViewById(R.id.studentRv);
        userRv.setHasFixedSize(true);
        userRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        usersModelList = new ArrayList<>();

        loadUsers();


        return view;
    }

    private void loadUsers() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersModelList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    UsersModel model = ds.getValue(UsersModel.class);
                    //usersModelList.add(model);
                    if (!model.getUid().equals(user.getUid())) {
                        usersModelList.add(model);
                    }
                    userAdapter = new UserAdapter(getActivity(), usersModelList);
                    userRv.setAdapter(userAdapter);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadUsers();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUsers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadUsers();
    }


}