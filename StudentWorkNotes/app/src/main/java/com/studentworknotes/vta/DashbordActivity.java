package com.studentworknotes.vta;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.studentworknotes.vta.Adapters.AdapterWork;
import com.studentworknotes.vta.DataBase.SubDBHelper;
import com.studentworknotes.vta.Models.ModelWork;
import com.studentworknotes.vta.databinding.ActivityDashbordBinding;
import com.studentworknotes.vta.databinding.DialogTeacherLoginBinding;

import java.util.ArrayList;

public class DashbordActivity extends AppCompatActivity {
    ActivityDashbordBinding binding;

    ArrayList<ModelWork> arrayList;
    ModelWork model;
    SubDBHelper subDBHelper;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashbordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i = getIntent();
        String umail = i.getStringExtra("UserEmail");
        binding.titleTv.setText(umail);

        subDBHelper = new SubDBHelper(this);

        arrayList = new ArrayList<>();
        Cursor data = subDBHelper.getData();
        int numRows = data.getCount();
        if (numRows == 0) {
            Toast.makeText(DashbordActivity.this, "The Database is empty..", Toast.LENGTH_LONG).show();
        } else {
            int d = 0;
            while (data.moveToNext()) {
                model = new ModelWork(data.getString(1), data.getString(2));
                arrayList.add(d, model);
                System.out.println(data.getString(0) + " " + data.getString(1) + " " + data.getString(2));
                System.out.println(arrayList.get(d).getTitle());
                d++;
            }
            AdapterWork adapter = new AdapterWork(this, R.layout.row, arrayList);
            binding.ListView.setAdapter(adapter);
        }

        binding.ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                ModelWork model = arrayList.get(position);
                model.getTitle();
                Intent i = new Intent(DashbordActivity.this, WorkVeiwActivity.class);
                //i.putExtra("user",model.getUser());
                i.putExtra("message", model.getMaseeag());
                i.putExtra("subject", model.getTitle());
                startActivity(i);
                // Toast.makeText(MainActivity.this, ""+model.getUserName()+"\n"+model.getAge()+"\n", Toast.LENGTH_SHORT).show();
            }
        });

        binding.MoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DashbordActivity.this, binding.MoreBtn);
                popupMenu.getMenu().add(Menu.NONE, 0, 0, "Teachers Page");
                popupMenu.getMenu().add(Menu.NONE, 1, 1, "About Us");
                popupMenu.getMenu().add(Menu.NONE, 2, 2, "Logout");
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int which = item.getItemId();
                        if (which == 0) {
                            showTecheraDiloge();
                        } else if (which == 1) {
                            startActivity(new Intent(DashbordActivity.this, AboutActivity.class));
                        } else if (which == 2) {
                            startActivity(new Intent(DashbordActivity.this, SpalshActivity.class));
                            finish();
                        }
                        return false;
                    }
                });
            }
        });


    }

    private void showTecheraDiloge() {
        DialogTeacherLoginBinding teacherLoginBinding = DialogTeacherLoginBinding.inflate(LayoutInflater.from(this));

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this, R.style.CustomDialog);
        builder.setView(teacherLoginBinding.getRoot());

        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

        teacherLoginBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        teacherLoginBinding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        teacherLoginBinding.loginTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = teacherLoginBinding.teacherrMailEt.getText().toString();
                password = teacherLoginBinding.teacherPasslEt.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(DashbordActivity.this, "Enter Email..", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(DashbordActivity.this, "Enter Password..", Toast.LENGTH_SHORT).show();
                } else {
                    if (email.equals("hello") || password.equals("12345")) {
                        startActivity(new Intent(DashbordActivity.this, TeacherActivity.class));
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(DashbordActivity.this, "Your Not Teacher", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}