package com.ckfood.pvt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class My_Profile extends AppCompatActivity {
    TextView Profile_Name,Profile_About,Profile_Number,Profile_BDay,Profile_Location,Profile_Email,Edit_Aboutme,Abouth_Show_Txt;
    ImageView Image_Number,Image_BDay,Image_Location,Image_Email,Profail_image;

    FirebaseUser USER;
    FirebaseAuth Fireauth;
    StorageReference FSReferen;
    FirebaseDatabase FireDataBase;
    DatabaseReference FireBaseRef;

    String path="android.resource://com.ckfood.pvt/"+R.drawable.chekbx_bg;
    String UID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        Profile_Name = findViewById(R.id.pnametxt);
        Profile_About = findViewById(R.id.pabouttxt);
        Profile_Number = findViewById(R.id.pconttxt);
        Profile_BDay = findViewById(R.id.pbdaytxt);
        Profile_Location = findViewById(R.id.ploctxt);
        Profile_Email = findViewById(R.id.pmailtxt);
        Profail_image = findViewById(R.id.profilimg);
        Edit_Aboutme = findViewById(R.id.editaboutme);
        Abouth_Show_Txt = findViewById(R.id.meabutshow);
        /*Image_Number = findViewById(R.id.prof_contact);
        Image_BDay = findViewById(R.id.prof_bday);
        Image_Location = findViewById(R.id.prof_loca);
        Image_Email = findViewById(R.id.prof_mail);*/

        Fireauth = FirebaseAuth.getInstance();
        FireDataBase = FirebaseDatabase.getInstance();
        FSReferen = FirebaseStorage.getInstance().getReference();
        USER = Fireauth.getCurrentUser();


        FireBaseRef = FireDataBase.getReference("Users");
        Query query = FireBaseRef.orderByChild("User Email").equalTo(USER.getEmail());
        query.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //checkc until required data get
                for (DataSnapshot ds : snapshot.getChildren()){
                    //get data Firebase
                    String image = ""+ds.child("Image").getValue();
                    String name = ""+ds.child("User Name").getValue();
                    String about = ""+ds.child("About").getValue();
                    String number = ""+ds.child("Mobail Number").getValue();
                    String location = ""+ds.child("Address").getValue();
                    String email = ""+ds.child("User Email").getValue();
                    String bday = ""+ds.child("BirthDay").getValue();
                    String aboutme = ""+ds.child("About Me").getValue();

                    Profile_Name.setText(name);
                    Profile_About.setText(about);
                    Profile_Number.setText(number);
                    Profile_BDay.setText(bday);
                    Profile_Email.setText(email);
                    Profile_Location.setText(location);
                    Abouth_Show_Txt.setText(aboutme);

                    try {
                        Picasso.get().load(image).into(Profail_image);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.profil).into(Profail_image);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                return;
            }
        });

        Edit_Aboutme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditAboutMe();
            }
        });

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 30)
        {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 30)
        {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }



    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void showEditAboutMe() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("UpDate About Me");

        LinearLayout linearLayout = new LinearLayout(this);

        EditText Aboutme = new EditText(this);
        Aboutme.setHint("Add New About You");
        Aboutme.setBackground(Drawable.createFromPath(path));
        Aboutme.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        // set Ten minits run //
        Aboutme.setMinEms(10);

        linearLayout.addView(Aboutme);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String aboutme = Aboutme.getText().toString().trim();
                if(TextUtils.isEmpty(Aboutme.getText().toString())){
                    Aboutme.setError("Invalid Email");  // ඊමෙල් ෆිල්ඩ් එක සම්පුර්ණ කරදැයි බැලීම.
                    return;
                }else {
                    Toast.makeText(My_Profile.this, "Uplode New About You", Toast.LENGTH_SHORT).show();
                    UID = Fireauth.getCurrentUser().getUid();
                    FireBaseRef = FirebaseDatabase.getInstance().getReference("Users").child(UID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("About Me",aboutme.toString());
                    FireBaseRef.updateChildren(user);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    }
