package com.bigknowledge;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigknowledge.Adapters.AdapterPosts;
import com.bigknowledge.Models.ModelPost;
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
import java.util.List;

public class ThereProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    ImageView Avatar, CoverIV;
    TextView NameTv, EmailTv, PhoneTv;
    RecyclerView postsRecyclerView;

    List<ModelPost> postList;
    AdapterPosts adapterPosts;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_there_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Avatar = findViewById(R.id.avatarIv);
        CoverIV = findViewById(R.id.coverIv);
        NameTv = findViewById(R.id.nameTv);
        EmailTv = findViewById(R.id.emailTv);
        PhoneTv = findViewById(R.id.nuberTv);

        postsRecyclerView = findViewById(R.id.recyclerview_posts);
        firebaseAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = "" + ds.child("name").getValue();
                    String email = "" + ds.child("email").getValue();
                    String phone = "" + ds.child("phone").getValue();
                    String image = "" + ds.child("image").getValue();
                    String cover = "" + ds.child("cover").getValue();

                    NameTv.setText(name);
                    EmailTv.setText(email);
                    PhoneTv.setText(phone);
                    try {
                        Picasso.get().load(image).into(Avatar);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.ic_default_image).into(Avatar);
                    }
                    try {
                        Picasso.get().load(cover).into(CoverIV);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.ic_default_image).into(CoverIV);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        postList = new ArrayList<>();

        checkUserStatus();
        loadHistPosts();


    }

    private void loadHistPosts() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        postsRecyclerView.setLayoutManager(linearLayoutManager);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = ref.orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelPost myPosts = ds.getValue(ModelPost.class);

                    postList.add(myPosts);

                    adapterPosts = new AdapterPosts(ThereProfileActivity.this, postList);
                    postsRecyclerView.setAdapter(adapterPosts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ThereProfileActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void searchHisPosts( final String searchQuery){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        postsRecyclerView.setLayoutManager(linearLayoutManager);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        Query query = ref.orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelPost myPosts = ds.getValue(ModelPost.class);

                    if (myPosts.getuTitle().toLowerCase().contains(searchQuery.toLowerCase()) ||
                            myPosts.getpDescr().toLowerCase().contains(searchQuery.toLowerCase())){

                        postList.add(myPosts);
                    }

                    adapterPosts = new AdapterPosts(ThereProfileActivity.this, postList);
                    postsRecyclerView.setAdapter(adapterPosts);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ThereProfileActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){

        }
        else {
            startActivity(new Intent(this , MainActivity.class));
           finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_add_post).setVisible(false);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!TextUtils.isEmpty(s)){
                    searchHisPosts(s);
                }else {
                    loadHistPosts();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)){
                    searchHisPosts(s);
                }else {
                    loadHistPosts();
                }
                return false;
            }
        });
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
        if (id == R.id.action_add_post){
            startActivity(new Intent(this, AddPostActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}