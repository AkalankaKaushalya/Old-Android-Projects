package com.bigknowledge.FragmentView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigknowledge.Adapters.AdapterPosts;
import com.bigknowledge.AddPostActivity;
import com.bigknowledge.MainActivity;
import com.bigknowledge.Models.ModelPost;
import com.bigknowledge.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {

    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    ImageView Avatar, CoverIV;
    TextView NameTv, EmailTv, PhoneTv;
    FloatingActionButton Fab;
    RecyclerView postsRecyclerView;

    ProgressDialog progressDialog;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    /*private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;*/

    String storagePath = "Users_Profile_Cover_Imgs/";
    //String UID;

    /*String cameraPermissions[];
    String storagePermissions[];*/

    List<ModelPost> postList;
    AdapterPosts adapterPosts;
    String uid;


   /* String profileOrCoverPhoto;*/

    public ProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        /*cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};*/

        Avatar = view.findViewById(R.id.avatarIv);
        CoverIV = view.findViewById(R.id.coverIv);
        NameTv = view.findViewById(R.id.nameTv);
        EmailTv = view.findViewById(R.id.emailTv);
        PhoneTv = view.findViewById(R.id.nuberTv);
        Fab = view.findViewById(R.id.fab);
        postsRecyclerView = view.findViewById(R.id.recyclerview_posts1);



        progressDialog = new ProgressDialog(getActivity());

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
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

        Fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shwoEditeProfileDialog();
            }
        });

        Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,STORAGE_REQUEST_CODE);
            }
        });
        CoverIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, CAMERA_REQUEST_CODE);
            }
        });

        postList = new ArrayList<>();

        checkUserStatus();
        loadMyPosts();
        return view;
    }

    private void loadMyPosts() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        postsRecyclerView.setLayoutManager(layoutManager);

         DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
         Query query = ref.orderByChild("uid").equalTo(uid);
         query.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 postList.clear();
                 for (DataSnapshot ds: dataSnapshot.getChildren()){
                     ModelPost myPosts = ds.getValue(ModelPost.class);

                     postList.add(myPosts);

                     adapterPosts = new AdapterPosts(getActivity(), postList);
                     postsRecyclerView.setAdapter(adapterPosts);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
             }
         });
    }

    private void searchMyPosts( final String searchQuery) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        postsRecyclerView.setLayoutManager(layoutManager);
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

                    adapterPosts = new AdapterPosts(getActivity(), postList);
                    postsRecyclerView.setAdapter(adapterPosts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == STORAGE_REQUEST_CODE) {
            Uri imageUri = data.getData();
            if (resultCode == Activity.RESULT_OK) {
               //ඇඩ්‍රොයිඩ් ගැලරි උමං සැකසුම මගින් තොරාගන්නා පික් එක ෆයර් බෙස් එකට අප්ලොඩ් වීමට උපයොගී වෙයි මෙහී මෙන් මෙතඩ් එක්ක ලෙස Uri imageUri මෙය භාවිත කරයී.
                Avatar.setImageURI(imageUri);
                uploadProfilePhoto(imageUri);//uploadImageToFirebase(imageUri);//uploadImageToFirebase මෙය අප විසින් නිර්මානය කර ගත් මෙතඩ් එකක් වුවද මෙමගින් ෆයර් බෙස් වෙත සෘජුවම ඉමෙජ් එක යවයී
            }


        }
        if(requestCode == CAMERA_REQUEST_CODE) {
            Uri imageUri2 = data.getData();
            if (resultCode == Activity.RESULT_OK) {
                //ඇඩ්‍රොයිඩ් ගැලරි උමං සැකසුම මගින් තොරාගන්නා පික් එක ෆයර් බෙස් එකට අප්ලොඩ් වීමට උපයොගී වෙයි මෙහී මෙන් මෙතඩ් එක්ක ලෙස Uri imageUri මෙය භාවිත කරයී.
                CoverIV.setImageURI(imageUri2);
                uploadCoverPhoto(imageUri2);//uploadImageToFirebase(imageUri);//uploadImageToFirebase මෙය අප විසින් නිර්මානය කර ගත් මෙතඩ් එකක් වුවද මෙමගින් ෆයර් බෙස් වෙත සෘජුවම ඉමෙජ් එක යවයී
            }
        }

    }

    private void uploadCoverPhoto(Uri imageUri2) {
        StorageReference fileRef = storageReference.child("users/"+firebaseAuth.getCurrentUser().getUid()+"/cover.jpg");
        fileRef.putFile(imageUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uid = firebaseAuth.getCurrentUser().getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                        Map<String,Object> user = new HashMap<>();
                        user.put("cover",uri.toString());
                        Picasso.get().load(uri).into(Avatar);//ණනිවැරදි ලිංක් එකක් පත් එක ලෙස යොදගනිමින් රූපකය ඇතුලත් කරයී එය ඇතුලත් උවාද ණැද්ද යන්න මෙයින් පෙන්නවයී
                        databaseReference.updateChildren(user);
                    }
                });
                Toast.makeText(getActivity(), "Image Uplod..", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed Uploaded", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void uploadProfilePhoto(Uri imageUri) {
       // StorageReference fileRef = storageReference.child(firebaseAuth.getCurrentUser().getUid()+storagePath+"/profile.jpg");
        StorageReference fileRef = storageReference.child("users/"+firebaseAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uid = firebaseAuth.getCurrentUser().getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                        Map<String,Object> user = new HashMap<>();
                        user.put("image",uri.toString());
                        Picasso.get().load(uri).into(Avatar);//ණනිවැරදි ලිංක් එකක් පත් එක ලෙස යොදගනිමින් රූපකය ඇතුලත් කරයී එය ඇතුලත් උවාද ණැද්ද යන්න මෙයින් පෙන්නවයී
                        databaseReference.updateChildren(user);

                    }
                });
                Toast.makeText(getActivity(), "Image Uplod..", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed Uploaded", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void shwoEditeProfileDialog() {
        String options[] = {"Edit Name", "Edit Phone"}; //"Edit profile Picture","Edit Cover Phone","Edit Name","Edit Phone"
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Action");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    progressDialog.setMessage("Updating Name");
                    showNamePhoneUpdateDilog("name");
                } else if (which == 1) {
                    progressDialog.setMessage("Updating Phone");
                    showNamePhoneUpdateDilog("phone");
                }

            }
        });
        builder.create().show();
    }

    private void showNamePhoneUpdateDilog(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update" + key);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10, 10, 10, 10);

        EditText editText = new EditText(getActivity());
        editText.setHint("Enter " + key);
        linearLayout.addView(editText);
        builder.setView(linearLayout);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    progressDialog.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);
                    databaseReference.child(user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Updated...", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    //if user edit his name, also change it from hist post
                    if (key.equals("name")){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                        Query query = ref.orderByChild("uid").equalTo(uid);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds: snapshot.getChildren()){
                                    String child = ds.getKey();
                                    snapshot.getRef().child(child).child("uName").setValue(value);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                } else {
                    Toast.makeText(getActivity(), "Please Enter"+key, Toast.LENGTH_SHORT).show();
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

    private void checkUserStatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            uid = user.getUid();
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
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
               if (!TextUtils.isEmpty(s)){
                   searchMyPosts(s);
               }else {
                   loadMyPosts();
               }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)){
                    searchMyPosts(s);
                }else {
                    loadMyPosts();
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




