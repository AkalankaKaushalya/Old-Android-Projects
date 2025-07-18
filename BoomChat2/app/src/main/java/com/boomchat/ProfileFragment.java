package com.boomchat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.security.cert.PolicyNode;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.EUICC_SERVICE;
import static android.content.Context.PRINT_SERVICE;
import static com.google.firebase.storage.FirebaseStorage.getInstance;


public class ProfileFragment extends Fragment {

    //firebase //
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    // ප්‍රොග්‍රස් ඩයලොග් ඉටෙන්ට්....//
    ProgressDialog progressDialog;

    //Storage
    StorageReference storageReference;
    //parth Where image ] කොහිද ඉමෙජ් එක සෙව් වෙන්නෙ කියලා කියන්නෙ මෙකෙන්
    String storagePath = "Users_Profile_Cover_imgs/";

    //Image from xml//
    ImageView Uimg,logout,cover_Img;
    TextView Uname,Uemali,Uphone;
    FloatingActionButton edit_fab;

// Perition constants //
    private static final  int CAMERA_REQUEST_CODE = 100;
    private static final  int STORAGE_REQUEST_CODE = 200;
    private static final  int IMAGE_PICK_GALLERY_CODE = 300;
    private static final  int IMAGE_PICK_CAMERA_CODE = 400;
    //arrays of permission t be requested //
    String cameraPermissions[];
    String storagePermissions[];
        // uri of picked image //
    Uri image_uri;
    // for checking profaile or cover photo
    String profileOrCoverPhoto;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //init firbase / /
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        storageReference = getInstance().getReference();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        //init Arrays of permition
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        Uimg = view.findViewById(R.id.uprofile);
        Uname = view.findViewById(R.id.uname);
        Uphone = view.findViewById(R.id.uphon);
        Uemali = view.findViewById(R.id.uemail);
        logout = view.findViewById(R.id.logout_but1);
        cover_Img = view.findViewById(R.id.cover_img);
        edit_fab = view.findViewById(R.id.edit_propaty);

        //Update Query //
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //checkc until required data get

                for (DataSnapshot ds : snapshot.getChildren()){
                    //get data Firebase
                    String name = ""+ds.child("name").getValue();
                    String email = ""+ds.child("email").getValue();
                    String phone = ""+ds.child("phone").getValue();
                    String image = ""+ds.child("image").getValue();
                    String cover = ""+ds.child("coverimg").getValue();

                    // set Data //
                    Uname.setText(name);
                    Uemali.setText(email);
                    Uphone.setText(phone);
                  try {
                      Picasso.get().load(image).into(Uimg);
                  }
                  catch (Exception e){
                      Picasso.get().load(R.mipmap.ic_add_image).into(Uimg);
                  }
                    try {
                        Picasso.get().load(cover).into(cover_Img);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.gradient_cover).into(cover_Img);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ලොගවුට් උනට පස්සෙ යන්නෙ කෙලින්ම ස්ප්ලශ් ස්ක්‍රින් එක හරහා ලොගින් ඇක්ටිවිටි එකට //
             //   Intent intent = new Intent(ProfileFragment.super.getContext());
                Intent myintent = new Intent(getActivity(), Splash_Screen.class);
                startActivity(myintent);
                firebaseAuth.signOut();
            }
        });

        // Fab button click //
        edit_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDilog();
            }
        });

        return view;
    }
    // ස්ටොරෙජ් පර්මිශන් ඔන් ද ඔෆ්ද බල්ලෙ මෙතනින්..... //
    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestStoragePermission(){
        requestPermissions(storagePermissions,STORAGE_REQUEST_CODE);
    }

    // කැමරාව සදහා පර්මිශන් ඔන් ද ඔෆ්ද බල්ලෙ මෙතනින්..... //
    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }
    private void requestCameraPermission(){
       requestPermissions(storagePermissions,STORAGE_REQUEST_CODE);
    }


    // මෙතනින් තමයී අපිට පර්මිශන් දෙන්න ඔනෙද එපාද අහන්නෙ....//
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                // Enebel and Desaibel Camera Prmition
                if (grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted){
                        // Permission Enabled
                        pickFromCamera();
                    }
                    else {
                        //pemissions denied ] පර්මිශන් නොදුන් විට මෙය පෙන්නවයි
                        Toast.makeText(getActivity(), "Please Enable Camera & Storage Permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                // Enebel and Desaibel Galary Prmition ]ස්ටොරෙජ් එකට පර්මිශන් ලබිලද බලනවා...//
                if (grantResults.length > 1){
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted){
                        // Permission Enabled
                        pickFromGallery();
                    }
                    else {
                        //pemissions denied
                        Toast.makeText(getActivity(), "Please Enable  Storage Permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Temp Descripton");
        // Put Image
        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Intent to start camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
    }
    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_CODE);
    }



    //ෆැබ් එක ක්ලික් කලාම ප්‍රොෆයිල් එක එඩිට් කරන්න එන ඩයලොග් එක //
    private void showEditProfileDilog(){
        // එහී ඇති ඔප්ශන්ස්..............//
    String options[] = {"Edit Profilr Image","Edit Profilr Cover Image","Edit Profilr Name","Edit Profilr Nomber"};
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle("Choose Action");
    builder.setItems(options, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == 0){
                progressDialog.setMessage("Updating Image");
                profileOrCoverPhoto = "image";
                showImagePicDialg();
            }
            else if (which ==1){
                progressDialog.setMessage("Updating Image");
                profileOrCoverPhoto = "cover";
                showImagePicDialg();
            }
            else if (which ==2){
                showNamePhoneUpdateDialog("name");
                //progressDialog.show();
                //progressDialog.setContentView(R.layout.progres_dilog);
            }
            else if (which ==3){
                showNamePhoneUpdateDialog("phone");
            }
        }
    });
    builder.create().show();
}


    //ඉමෙජ් එකක් දෙන්න ක්ලික් කලාම මොන ඔප්ශන් එකද තොරන්නෙ බල්ලෙ මෙකෙන්....//
    private void showImagePicDialg() {
        // Options
        String options[] = {"Camera","Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    //Camra Click
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {                             //කමරාව ඔන් වෙන්න ඔනෙ තැන
                        pickFromCamera();
                    }
                }
                else if (which ==1){
                    //Gallery Click
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {                            // ස්ටොරෙජ් ඔන් වෙන්න ඔනේ තැන
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

    // නම සහා දුරකතන අංකය අප්ඩෙට් වෙන්නෙ මෙතනින්.....//
    private void showNamePhoneUpdateDialog(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update" + key);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);

        EditText editText = new EditText(getActivity());
        editText.setHint("Enter "+ key);
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        //add button in dilog
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)){
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Updated.....", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "Please Enter", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_OK){
          // uploadProfileCoverPhoto(image_uri);

            if (requestCode == IMAGE_PICK_GALLERY_CODE){
                image_uri = data.getData();
                uploadProfileCoverPhoto(image_uri);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE){
                uploadProfileCoverPhoto(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadProfileCoverPhoto(Uri uri) {
        //ප්‍රොග්‍රස් වෙනවා පෙන්නවා ඉමෙජ් එකක් අප්ල්ඩ් වෙනකොට
        progressDialog.show();
        progressDialog.setContentView(R.layout.progres_dilog);

        String filePathAndName = storagePath +""+ profileOrCoverPhoto +"_"+ user.getUid(); // ෆ්‍යර් බෙස් එකෙ කොහොමද අපේ කවර් ෆොටො සෙව් වෙන්නෙ කියන පාත් එක මෙක..//
        StorageReference storageReference2nd = storageReference.child(filePathAndName);
        storageReference2nd.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();
                        //ඉමෙජ් එක අප්ලොඩ් උනාද නද්ද බලන්නෙ මෙතනින්....//
                        if (uriTask.isSuccessful()){
                            HashMap<String, Object> results = new HashMap<>();
                            results.put(profileOrCoverPhoto, downloadUri.toString());
                            databaseReference.child(user.getUid()).updateChildren(results)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                          progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Image Updated....", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                          progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Erro Updating....", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Some Error Occured", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}