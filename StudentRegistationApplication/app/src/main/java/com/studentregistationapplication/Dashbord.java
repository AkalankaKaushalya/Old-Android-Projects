package com.studentregistationapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Dashbord extends AppCompatActivity {
    ArrayList<Model> arrayList;
    Model model;
    DBHelper dbHelper;
    ListView list;
    ImageButton morbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);

        list = findViewById(R.id.listview);
        morbtn = findViewById(R.id.moreBtn);

dbHelper = new DBHelper(this);
        arrayList = new ArrayList<>();
        Cursor data = dbHelper.getNote();
        int numRows = data.getCount();
        if (numRows == 0) {
            Toast.makeText(Dashbord.this, "The Database is empty..", Toast.LENGTH_LONG).show();
        } else {
            int d = 0;
            while (data.moveToNext()) {
                model = new Model(data.getString(0), data.getString(1));
                arrayList.add(d, model);
                System.out.println(data.getString(0) + " " + data.getString(1) );
                System.out.println(arrayList.get(d).getTitle());
                d++;
            }
            Adapter adapter = new Adapter(this, R.layout.row, arrayList);
          list.setAdapter(adapter);
        }

        morbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashbord.this, Registation.class));
            }
        });
    }
}