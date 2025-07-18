package com.ak.unitycode.sl.AddActivitys;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ak.unitycode.sl.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddLessonActivity extends AppCompatActivity {
    private EditText urlet, videodecrip, videoTitle;
    private ImageView thumbel;
    private Button btn;
    private FirebaseAuth firebaseAuth;
    private String imgurl;

    //String youTubeUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);

        urlet = findViewById(R.id.searchurlEt);
        videodecrip = findViewById(R.id.discriptionEt);
        thumbel = findViewById(R.id.img_thumnail);
        videoTitle = findViewById(R.id.LessontitleEt);
        btn = findViewById(R.id.button);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        urlet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String youTubeUrl = urlet.getText().toString();
                if (youTubeUrl.isEmpty()) {

                } else {
                    String video_id = "";

                    String expression = "^.*((youtu.be" + "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*"; // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
                    CharSequence input = youTubeUrl;
                    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(input);
                    if (matcher.matches()) {
                        String groupIndex1 = matcher.group(7);
                        if (groupIndex1 != null && groupIndex1.length() == 11)
                            video_id = groupIndex1;

                        String igm = video_id;
                        imgurl = "https://img.youtube.com/vi/" + igm + "/mqdefault.jpg";
                        //Uid.setText(imgurl);
                        try {
                            Picasso.get().load(imgurl).into(thumbel);
                            btn.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            Picasso.get().load(R.drawable.next).into(thumbel);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String youTubeUrl = urlet.getText().toString();
                String videodec = videodecrip.getText().toString();
                String videotitle = videoTitle.getText().toString();
                if (youTubeUrl.isEmpty()) {
                    Toast.makeText(AddLessonActivity.this, "Enter YouTube URL", Toast.LENGTH_SHORT).show();
                } else if (videodec.isEmpty()) {
                    Toast.makeText(AddLessonActivity.this, "Enter Short Discription", Toast.LENGTH_SHORT).show();
                } else if (videotitle.isEmpty()) {
                    Toast.makeText(AddLessonActivity.this, "Enter Short Title", Toast.LENGTH_SHORT).show();
                } else {
                    long timestamp = System.currentTimeMillis();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("Lid", "" + timestamp);
                    hashMap.put("imageurl", "" + imgurl);
                    hashMap.put("videourl", youTubeUrl);
                    hashMap.put("videotitle", videotitle);
                    hashMap.put("videodecrip", videodec);
                    hashMap.put("views", "" + 0);
                    hashMap.put("likes", "");
                    hashMap.put("uid", "" + firebaseAuth.getUid());
                    DatabaseReference rfe = FirebaseDatabase.getInstance().getReference("Lessons");
                    rfe.child("" + timestamp)
                            .setValue(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //progressDialog.dismiss();
                                    urlet.setText(null);
                                    videodecrip.setText(null);
                                    Picasso.get().load(R.drawable.next).into(thumbel);
                                    Toast.makeText(AddLessonActivity.this, "Add Lesson Successfuly...", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddLessonActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }


}