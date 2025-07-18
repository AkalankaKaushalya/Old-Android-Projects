package com.skypper.k_mistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Admin_PanelActivity extends AppCompatActivity {

    EditText   lesson_name,lesson, dc1,dc2,dc3,dc4,dc5,dc6,dc7,dc8,dc9,dc10,dc11,dc12,dc13,dc14,dc15,dc16,dc17,dc18,dc19,dc20;
    MaterialCardView senddata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        senddata = (MaterialCardView) findViewById(R.id.send_data);
        senddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
            }
        });

        lesson_name = (EditText) findViewById(R.id.lesson_name_add);
        lesson =  (EditText)findViewById(R.id.lesson_main_name_1);


        dc1 =  (EditText)findViewById(R.id.lesson_det_1_add);
        dc2 =  (EditText)findViewById(R.id.lesson_det_2_add);
        dc3 = (EditText) findViewById(R.id.lesson_det_3_add);
        dc4 =  (EditText)findViewById(R.id.lesson_det_4_add);
        dc5 = (EditText) findViewById(R.id.lesson_det_5_add);
        dc6 = (EditText) findViewById(R.id.lesson_det_6_add);
        dc7 = (EditText) findViewById(R.id.lesson_det_7_add);
        dc8 =  (EditText)findViewById(R.id.lesson_det_8_add);
        dc9 =  (EditText)findViewById(R.id.lesson_det_9_add);
        dc10 = (EditText) findViewById(R.id.lesson_det__10_add);
        dc11 = (EditText) findViewById(R.id.lesson_det__11_add);
        dc12 =  (EditText)findViewById(R.id.lesson_det__12_add);
        dc13 =  (EditText)findViewById(R.id.lesson_det__13_add);
        dc14 = (EditText) findViewById(R.id.lesson_det__14_add);
        dc15 = (EditText) findViewById(R.id.lesson_det__15_add);
        dc16 = (EditText) findViewById(R.id.lesson_det__16_add);
        dc17 = (EditText) findViewById(R.id.lesson_det__17_add);
        dc18 = (EditText) findViewById(R.id.lesson_det__18_add);
        dc19 = (EditText) findViewById(R.id.lesson_det__19_add);
        dc20 = (EditText) findViewById(R.id.lesson_det__20_add);


    }

    public void  InsertData()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("details_1",dc1.getText().toString());
        map.put("details_2",dc2.getText().toString());
        map.put("details_3",dc3.getText().toString());
        map.put("details_4",dc4.getText().toString());
        map.put("details_5",dc5.getText().toString());
        map.put("details_6",dc6.getText().toString());
        map.put("details_7",dc7.getText().toString());
        map.put("details_8",dc8.getText().toString());
        map.put("details_9",dc9.getText().toString());
        map.put("details__10",dc10.getText().toString());
        map.put("details__11",dc11.getText().toString());
        map.put("details__12",dc12.getText().toString());
        map.put("details__13",dc13.getText().toString());
        map.put("details__14",dc14.getText().toString());
        map.put("details__15",dc15.getText().toString());
        map.put("details__16",dc16.getText().toString());
        map.put("details__17",dc17.getText().toString());
        map.put("details__18",dc18.getText().toString());
        map.put("details__19",dc19.getText().toString());
        map.put("details__20",dc20.getText().toString());

        map.put("lesson_main_name",lesson.getText().toString());
        map.put("lesson_Name", lesson_name.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Admin_Lesson").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Admin_PanelActivity.this,"Data will be show in 24 Hours..Your Content is Cheching Now",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Admin_PanelActivity.this,"Data not sent.Please Try Again..",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}