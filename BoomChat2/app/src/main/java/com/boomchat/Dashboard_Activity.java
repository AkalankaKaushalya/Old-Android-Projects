package com.boomchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard_Activity extends AppCompatActivity {
 FirebaseAuth firebaseAuth;
    Connection_Detect connection_detect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        // hide action bar //
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firebaseAuth  = FirebaseAuth.getInstance();

        //bottom navigation //
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);
          // default , on start //


        connection_detect=new Connection_Detect(this);
    }

   // navigation call//
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
           new BottomNavigationView.OnNavigationItemSelectedListener() {
               @Override
               public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                   // hadel item Clicks //
                   switch (menuItem.getItemId()){
                       case R.id.nav_home:

                           //Home fragment Transaction
                           HomeFragment fragment1 = new HomeFragment();
                           FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                           ft1.replace(R.id.content, fragment1, "");
                           ft1.commit();
                           return true;
                       case R.id.nav_profile:
                           //profail fragment Transaction
                           ProfileFragment fragment2 = new ProfileFragment();
                           FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                           ft2.replace(R.id.content, fragment2, "");
                           ft2.commit();
                           return true;
                       case R.id.nav_users:
                           //Users fragment Transaction
                           UsersFragment fragment3 = new UsersFragment();
                           FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                           ft3.replace(R.id.content, fragment3, "");
                           ft3.commit();
                           return true;
                   }
                   return false;
               }
           };

    // NetWork Connection Check //
    private void chekConnection(){
        if (connection_detect.isConnected()){
            Toast.makeText(this,"Connect App",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Piles Connect Your Data",Toast.LENGTH_LONG).show();
            Intent i = new Intent(Dashboard_Activity.this, Splash_Screen.class);
            startActivity(i);
            finish();
        }
    }


    private  void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){

        }
        else {
            // ලොගවුට් උනට පස්සෙ යන්නෙ කෙලින්ම ස්ප්ලශ් ස්ක්‍රින් එක හරහා ලොගින් ඇක්ටිවිටි එකට //
            startActivity(new Intent(Dashboard_Activity.this, Splash_Screen.class));
            finish();
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
    protected void onStart(){
        checkUserStatus();
        chekConnection();
        super.onStart();
    }
    //  inflate Option Menu //
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
     return  super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id  = item.getItemId();
        if (id == R.id.logout_but){
            firebaseAuth.signOut();
            checkUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }
}