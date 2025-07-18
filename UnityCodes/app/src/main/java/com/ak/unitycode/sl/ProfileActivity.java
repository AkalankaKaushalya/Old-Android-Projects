package com.ak.unitycode.sl;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private CircleImageView profileImag;
    private TextView nameTv, ageTv, userType, monyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        nameTv = findViewById(R.id.Admin_Name);
        ageTv = findViewById(R.id.Admin_Age);
        userType = findViewById(R.id.Admin_Skills);
        monyTv = findViewById(R.id.doller_earnings);
        profileImag = findViewById(R.id.profile_picture);

        loadUserInfo();

    }

    private void loadUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String profileImage = "" + snapshot.child("profileImage").getValue();
                        String Mony = "" + snapshot.child("Earnmony").getValue();
                        String Name = "" + snapshot.child("Name").getValue();
                        String Age = "" + snapshot.child("Age").getValue();
                        String UserType = "" + snapshot.child("UserType").getValue();

                        nameTv.setText(Name);
                        ageTv.setText(Age);
                        userType.setText(UserType);
                        monyTv.setText(Mony);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadUserInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUserInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfo();
    }
}