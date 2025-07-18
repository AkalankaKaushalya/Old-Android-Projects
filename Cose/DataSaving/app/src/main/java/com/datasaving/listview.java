package com.datasaving;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class                   listview extends AppCompatActivity {
    ArrayList<Model> arrayList;
    Model model;
    Context context;
    dbConnect dbConnect;j
    ListView ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ls = findViewById(R.id.list);

        dbConnect = new dbConnect(this);

        arrayList = new ArrayList<>();
        Cursor data = dbConnect.getData();
        int numRows = data.getCount();
        if (numRows==0){
            Toast.makeText(listview.this,"The database is empty :(.",Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                model = new Model(data.getString(0),data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5));
                arrayList.add(i,model);
                i++;
            }

            Adapter adapter = new Adapter(this, R.layout.item_row, arrayList);
            ls.setAdapter(adapter);
        }
    }
}