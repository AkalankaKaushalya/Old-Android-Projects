package com.bigknowledge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bigknowledge.FragmentView.ChatListFragment;
import com.bigknowledge.FragmentView.HomeFragment;
import com.bigknowledge.FragmentView.ProfileFragment;
import com.bigknowledge.FragmentView.UserFragment;
import com.bigknowledge.Notifications.Token;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class DashbordActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    ActionBar actionBar;

    String mUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        actionBar = getSupportActionBar();
        //actionBar.setTitle("Profile");



        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

        actionBar.setTitle("Profile");
        ProfileFragment fragment2 = new ProfileFragment();
        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
        ft2.replace(R.id.content, fragment2, "");
        ft2.commit();

        checkUserStatus();


    }
    public void updateToken(String token){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token mToken = new Token(token);
        ref.child(mUID).setValue(mToken);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            actionBar.setTitle("Home");
                            HomeFragment fragment1 = new HomeFragment();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.content, fragment1, "");
                            ft1.commit();
                        return true;
                    }
                    switch (item.getItemId()){
                        case R.id.nav_profile:
                            actionBar.setTitle("Profile");
                            ProfileFragment fragment2 = new ProfileFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content, fragment2, "");
                            ft2.commit();
                            return true;
                    }
                    switch (item.getItemId()){
                        case R.id.nav_user:
                            actionBar.setTitle("Users");
                            UserFragment fragment3 = new UserFragment();
                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.content, fragment3, "");
                            ft3.commit();
                            return true;
                    }
                    switch (item.getItemId()){
                        case R.id.nav_chat:
                            actionBar.setTitle("Chats List");
                            ChatListFragment fragment4 = new ChatListFragment();
                            FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                            ft4.replace(R.id.content, fragment4, "");
                            ft4.commit();
                            return true;
                    }
                    return false;
                }
            };


    private void checkUserStatus(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            mUID = user.getUid();
            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Current_USERID", mUID);
            editor.apply();

            //update token
            updateToken(FirebaseInstanceId.getInstance().getToken());
        }
        else {
            startActivity(new Intent(DashbordActivity.this ,MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        checkUserStatus();
        super.onResume();
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }


}