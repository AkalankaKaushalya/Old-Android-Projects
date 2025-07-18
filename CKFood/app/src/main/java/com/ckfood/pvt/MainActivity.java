package com.ckfood.pvt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    ImageView Profail_Imge;

    FirebaseUser USER;
    FirebaseAuth Fireauth;
    StorageReference FSReferen;
    FirebaseDatabase FireDataBase;
    DatabaseReference FireBaseRef;




    String UID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        Profail_Imge =findViewById(R.id.profil);
        Toolbar tb = findViewById(R.id.toob);
        setSupportActionBar(tb);

        Fireauth = FirebaseAuth.getInstance();
        FireDataBase = FirebaseDatabase.getInstance();
        FSReferen = FirebaseStorage.getInstance().getReference();
        FireBaseRef= FirebaseDatabase.getInstance().getReference();
        USER = Fireauth.getCurrentUser();
        FireBaseRef = FireDataBase.getReference("Users");


        //StorageReference profileRef = FSReferen.child("users/"+Fireauth.getCurrentUser().getUid()+"/profile.jpg");
       /*Query query = FireBaseRef.orderByChild("User Email").equalTo(USER.getEmail());
        query.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //checkc until required data get
                for (DataSnapshot ds : snapshot.getChildren()){
                    //get data Firebase
                    String image = ""+ds.child("Image").getValue();
                    try {
                        Picasso.get().load(image).into(Profail_Imge);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.profil).into(Profail_Imge);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });*/

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.recipebook));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.menu));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.offer));
        bottomNavigation.show(2,true);
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        replace(new Recipes());
                        break;

                    case 2:
                        replace(new OurMenu());
                        break;

                    case 3:
                        replace(new Offer());
                        break;
                }
                return null;
            }
        });

        Profail_Imge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });




    }
    protected void onActivityResult(int requestCode, int resultCode,@androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();        //ඇඩ්‍රොයිඩ් ගැලරි උමං සැකසුම මගින් තොරාගන්නා පික් එක ෆයර් බෙස් එකට අප්ලොඩ් වීමට උපයොගී වෙයි මෙහී මෙන් මෙතඩ් එක්ක ලෙස Uri imageUri මෙය භාවිත කරයී.
                Profail_Imge.setImageURI(imageUri);
                uploadProfileCoverPhoto(imageUri);
                //uploadImageToFirebase(imageUri);//uploadImageToFirebase මෙය අප විසින් නිර්මානය කර ගත් මෙතඩ් එකක් වුවද මෙමගින් ෆයර් බෙස් වෙත සෘජුවම ඉමෙජ් එක යවයී
            }
        }
    }

    private void uploadProfileCoverPhoto(Uri imageUri) {
        StorageReference fileRef = FSReferen.child("users/"+Fireauth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UID = Fireauth.getCurrentUser().getUid();
                        FireBaseRef = FirebaseDatabase.getInstance().getReference("Users").child(UID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("Image",uri.toString());
                        Picasso.get().load(uri).into(Profail_Imge);//ණනිවැරදි ලිංක් එකක් පත් එක ලෙස යොදගනිමින් රූපකය ඇතුලත් කරයී එය ඇතුලත් උවාද ණැද්ද යන්න මෙයින් පෙන්නවයී
                        FireBaseRef.updateChildren(user);
                    }
                });
                Toast.makeText(MainActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed Uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,fragment);
        transaction.commit();
    }

    public void checkUserStatus(){
        FirebaseUser user = Fireauth.getCurrentUser();
        if(user != null){
        }
        else {
            // ලොගවුට් උනට පස්සෙ යන්නෙ කෙලින්ම ස්ප්ලශ් ස්ක්‍රින් එක හරහා ලොගින් ඇක්ටිවිටි එකට //
            startActivity(new Intent(MainActivity.this, Splash_Screen.class));
            finish();
        }
    }
    protected void onStart(){
        super.onStart();
        replace(new OurMenu());
        checkUserStatus();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id  = item.getItemId();
        if (id == R.id.logout_but){
            Fireauth.signOut();
            startActivity(new Intent(MainActivity.this, Splash_Screen.class));
        }
        if (id == R.id.profil);{
            startActivity(new Intent(MainActivity.this, My_Profile.class));
        }
        return true;
    }




}

