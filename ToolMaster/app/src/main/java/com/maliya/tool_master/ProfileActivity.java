package com.maliya.tool_master;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.maliya.tool_master.databinding.ActivityProfileBinding;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    Window window;
    ActivityProfileBinding binding;
    String uid;
    Uri imageUri = null;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.stutas_bar));
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.stutas_bar));
        }

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        loaduser();

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, STORAGE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loaduser();
    }

    private void loaduser() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String name = "" + snapshot.child("name").getValue();
                        String email = "" + snapshot.child("email").getValue();
                        String profileImage = "" + snapshot.child("profileImage").getValue();

                        binding.UsernameTv.setText(name);
                        binding.UserEmailTv.setText(email);


                        try {
                            Picasso.get().load(profileImage).into(binding.profileImage);
                        } catch (Exception e) {
                            Picasso.get().load(R.drawable.profile_img).into(binding.profileImage);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STORAGE_REQUEST_CODE) {
            imageUri = data.getData();
            if (resultCode == Activity.RESULT_OK) {
                //ඇඩ්‍රොයිඩ් ගැලරි උමං සැකසුම මගින් තොරාගන්නා පික් එක ෆයර් බෙස් එකට අප්ලොඩ් වීමට උපයොගී වෙයි මෙහී මෙන් මෙතඩ් එක්ක ලෙස Uri imageUri මෙය භාවිත කරයී.
                binding.profileImage.setImageURI(imageUri);
                uploadProfilePhoto(imageUri);//uploadImageToFirebase(imageUri);//uploadImageToFirebase මෙය අප විසින් නිර්මානය කර ගත් මෙතඩ් එකක් වුවද මෙමගින් ෆයර් බෙස් වෙත සෘජුවම ඉමෙජ් එක යවයී
            } else {
                Toast.makeText(ProfileActivity.this, "Cacelled", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ProfileActivity.this, "Cacelled", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadProfilePhoto(Uri imageUri) {
        StorageReference fileRef = storageReference.child("users/" + firebaseAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uid = firebaseAuth.getCurrentUser().getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                        Map<String, Object> user = new HashMap<>();
                        user.put("profileImage", uri.toString());
                        Picasso.get().load(uri).into(binding.profileImage);//ණනිවැරදි ලිංක් එකක් පත් එක ලෙස යොදගනිමින් රූපකය ඇතුලත් කරයී එය ඇතුලත් උවාද ණැද්ද යන්න මෙයින් පෙන්නවයී
                        databaseReference.updateChildren(user);

                    }
                });
                Toast.makeText(ProfileActivity.this, "Image Uplod..", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Failed Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }
}