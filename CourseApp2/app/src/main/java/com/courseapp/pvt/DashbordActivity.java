package com.courseapp.pvt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.courseapp.pvt.Adapter.SubjectAdapter;
import com.courseapp.pvt.DataBase.DBHelper;
import com.courseapp.pvt.DataBase.SubDBHelper;
import com.courseapp.pvt.Model.SubjectModel;

import java.util.ArrayList;

public class DashbordActivity extends AppCompatActivity {
    TextView NameView;
    ListView ListView;

    ArrayList<SubjectModel> arrayList;
    SubjectModel model;
    SubDBHelper subDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        NameView= findViewById(R.id.text1);
        ListView = findViewById(R.id.List_view);
        subDBHelper = new SubDBHelper(this);

        Intent i = getIntent();
        String name = i.getStringExtra("UserName");
        NameView.setText("Welcome "+name);

        String un = name;

        arrayList = new ArrayList<>();
        Cursor data = subDBHelper.getData();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(DashbordActivity.this,"The Database is empty..",Toast.LENGTH_LONG).show();
        }else {
            int d= 0;
            while (data.moveToNext()) {
                model = new SubjectModel(data.getString(0), data.getString(1), data.getString(2));
                arrayList.add(d, model);
                System.out.println(data.getString(0) + " " + data.getString(1) + " " + data.getString(2));
                System.out.println(arrayList.get(d).getSubject());
                d++;
            }
            SubjectAdapter adapter = new SubjectAdapter(this, R.layout.row, arrayList);
            ListView.setAdapter(adapter);
        }

        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                SubjectModel model = arrayList.get(position);
                model.getSubject();
                Intent i = new Intent(DashbordActivity.this, SubjectViewActivity.class);
                i.putExtra("user",model.getUser());
                i.putExtra("message",model.getMessage());
                i.putExtra("subject",model.getSubject());
                startActivity(i);
                // Toast.makeText(MainActivity.this, ""+model.getUserName()+"\n"+model.getAge()+"\n", Toast.LENGTH_SHORT).show();
            }
        });




    }






    //  inflate Option Menu //
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_manu, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id  = item.getItemId();
        if (id == R.id.add_but){
            Intent i = new Intent(DashbordActivity.this, AddSubjectActivity.class);
            String name = NameView.getText().toString();
            i.putExtra("UserName", name );
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}