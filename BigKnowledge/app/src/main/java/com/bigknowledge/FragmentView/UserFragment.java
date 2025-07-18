package com.bigknowledge.FragmentView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigknowledge.Adapters.AdapterUser;
import com.bigknowledge.AddPostActivity;
import com.bigknowledge.MainActivity;
import com.bigknowledge.Models.ModelUser;
import com.bigknowledge.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterUser adapterUser;
    List<ModelUser> userList;

    FirebaseAuth firebaseAuth;


    public UserFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.users_recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        userList = new ArrayList<>();
        getAllUsers();



        return view;

    }

    private void getAllUsers() {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ModelUser modelUser = ds.getValue(ModelUser.class);

                    if (!modelUser.getUid().equals(fUser.getUid())){
                        userList.add(modelUser);
                    }
                    adapterUser = new AdapterUser(getContext(), userList);
                    recyclerView.setAdapter(adapterUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void searchUsers(String query) {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ModelUser modelUser = ds.getValue(ModelUser.class);

                    if (!modelUser.getUid().equals(fUser.getUid())){
                        if (modelUser.getName().toLowerCase().contains(query.toLowerCase()) ||
                        modelUser.getEmail().toLowerCase().contains(query.toLowerCase())){
                            userList.add(modelUser);
                        }
                    }
                    adapterUser = new AdapterUser(getContext(), userList);
                    adapterUser.notifyDataSetChanged();
                    recyclerView.setAdapter(adapterUser);
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

        }
        else {
            startActivity(new Intent(getActivity() , MainActivity.class));
            getActivity().finish();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflating menu
        inflater.inflate(R.menu.menu_main, menu);

        // hide addpost icon from this fragment
        menu.findItem(R.id.action_add_post).setVisible(false);

        //Search View
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        //Search Listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.isEmpty(s.trim())){

                    searchUsers(s);
                }
                else {
                    getAllUsers();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s.trim())){

                    searchUsers(s);
                }
                else {
                    getAllUsers();
                }
                return false;
            }
        });
       super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logut){
            firebaseAuth.signOut();
            getActivity().finish();
            checkUserStatus();
        }
        if (id == R.id.action_add_post){
            startActivity(new Intent(getActivity(), AddPostActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        checkUserStatus();
        super.onStart();
    }
}