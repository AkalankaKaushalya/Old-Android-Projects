package com.sqlitepatrical2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sqlitepatrical2.Adapter.Adapter;
import com.sqlitepatrical2.Model.Model;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Model> dataholder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        recyclerView = findViewById(R.id.lview);// RecyclerView එක අයිඩි කරගෙන පසුව එය මෙම ඇක්ටිවිටි එක තුල තිබෙන බව පෙන්ව්‍යි
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Cursor cursor=new DBHelper(this).getData(); //කසර් මගින් අපි DBHelper හී අපිට පෙන්වන්න යැයි කියු ඩෙට නොහොත් getData පෙන්වයි
        dataholder=new ArrayList<>();// එය අපේ මොඩියුලය හරහා arrayList ekak ආකරයෙන් පෙන්වයි

        if (cursor.getCount()==0){
            Toast.makeText(ViewActivity.this, "No Enter Data..", Toast.LENGTH_SHORT).show();
            return;// කිසිම දත්ත යක නැතිනම් මෙහී පෙන්වන්නෙ කිසිවක් නති යන ආරතයි
        }

        while(cursor.moveToNext())
        {
            Model obj=new Model(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            dataholder.add(obj);
        }
        Adapter adapter = new Adapter(dataholder);
        recyclerView.setAdapter(adapter);
    }

}