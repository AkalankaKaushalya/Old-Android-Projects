package com.studentworknotes.vta;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.studentworknotes.vta.Adapters.AdapterStudent;
import com.studentworknotes.vta.DataBase.DBHelper;
import com.studentworknotes.vta.DataBase.SubDBHelper;
import com.studentworknotes.vta.Models.ModelStudent;
import com.studentworknotes.vta.databinding.ActivityTeacherBinding;

import java.util.ArrayList;

public class TeacherActivity extends AppCompatActivity {
    ActivityTeacherBinding binding;

    SubDBHelper subDBHelper;
    DBHelper dbHelper;
    String Stuitle, Smassage;


    ArrayList<ModelStudent> StarrayList;
    ModelStudent stmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        subDBHelper = new SubDBHelper(this);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.studentViewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.AddSujetLL.setVisibility(View.GONE);
                binding.studentViewTv.setBackground(ContextCompat.getDrawable(TeacherActivity.this, R.drawable.techer_nav_bg));
                binding.ViewStudentLL.setVisibility(View.VISIBLE);
                binding.addSubjetTv.setBackground(ContextCompat.getDrawable(TeacherActivity.this, R.drawable.techer_nav_bg2));
            }
        });

        binding.addSubjetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.AddSujetLL.setVisibility(View.VISIBLE);
                binding.studentViewTv.setBackground(ContextCompat.getDrawable(TeacherActivity.this, R.drawable.techer_nav_bg2));
                binding.ViewStudentLL.setVisibility(View.GONE);
                binding.addSubjetTv.setBackground(ContextCompat.getDrawable(TeacherActivity.this, R.drawable.techer_nav_bg));
            }
        });


        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stuitle = binding.SubjectTitelEt.getText().toString();
                Smassage = binding.SubjectDiscriptionEt.getText().toString();
                if (Stuitle.isEmpty()) {
                    Toast.makeText(TeacherActivity.this, "Enter Title", Toast.LENGTH_SHORT).show();
                } else if (Smassage.isEmpty()) {
                    Toast.makeText(TeacherActivity.this, "Enter Discription", Toast.LENGTH_SHORT).show();
                } else {
                    boolean chake = subDBHelper.addMessage(Stuitle, Smassage);
                    if (chake == true) {
                        Toast.makeText(TeacherActivity.this, "Send Your Subject", Toast.LENGTH_SHORT).show();
                        binding.SubjectTitelEt.setText(null);
                        binding.SubjectDiscriptionEt.setText(null);
                    } else {
                        Toast.makeText(TeacherActivity.this, "Not Send...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        dbHelper = new DBHelper(this);

        StarrayList = new ArrayList<>();
        Cursor data = dbHelper.getUsers();
        int numRows = data.getCount();
        if (numRows == 0) {
            Toast.makeText(TeacherActivity.this, "Not Login Student is empty..", Toast.LENGTH_LONG).show();
        } else {
            int d = 0;
            while (data.moveToNext()) {
                stmodel = new ModelStudent(data.getString(0), data.getString(1), data.getString(2));
                StarrayList.add(d, stmodel);
                System.out.println(data.getString(0) + " " + data.getString(1) + " " + data.getString(2));
                System.out.println(StarrayList.get(d).getUserName());
                d++;
            }
            AdapterStudent adapter = new AdapterStudent(this, R.layout.item_student, StarrayList);
            binding.StListView.setAdapter(adapter);
        }
    }
}