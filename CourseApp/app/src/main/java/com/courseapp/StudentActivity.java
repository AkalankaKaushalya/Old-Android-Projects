package com.courseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.courseapp.Adapter.Adapter;
import com.courseapp.Helper.DBHelper;
import com.courseapp.Model.Model;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    ListView recyclerView;
    ArrayList<Model> dataholder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        recyclerView = findViewById(R.id.RecycleView);// RecyclerView එක අයිඩි කරගෙන පසුව එය මෙම ඇක්ටිවිටි එක තුල තිබෙන බව පෙන්ව්‍යි
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Cursor cursor = new DBHelper(this).getData(); //කසර් මගින් අපි DBHelper හී අපිට පෙන්වන්න යැයි කියු ඩෙට නොහොත් getData පෙන්වයි
        dataholder=new ArrayList<>(); // එය අපේ මොඩියුලය හරහා arrayList ekak ආකරයෙන් පෙන්වයි

        if (cursor.getCount()==0){
            Toast.makeText(StudentActivity.this, "No Enter Data..", Toast.LENGTH_SHORT).show();
            return;// කිසිම දත්ත යක නැතිනම් මෙහී පෙන්වන්නෙ කිසිවක් නති යන ආරතයි
        }

        while(cursor.moveToNext())
        {
            Model obj = new Model(cursor.getString(0),cursor.getString(1),cursor.getString(2));
            dataholder.add(obj);

        }
        Adapter adapter = new Adapter(dataholder);
        recyclerView.setAdapter(adapter);


    }



}