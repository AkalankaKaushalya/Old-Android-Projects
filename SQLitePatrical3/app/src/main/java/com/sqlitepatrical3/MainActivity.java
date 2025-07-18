package com.sqlitepatrical3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText UserName, Age, NIC, Email;
    Button Insert, Update, Delete, View;
    DBHelper dbHelper;

    ListView listView;
    ArrayList<Model> arrayList;
    Model model;
    Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;


        UserName = findViewById(R.id.uname);
        Age = findViewById(R.id.age);
        NIC = findViewById(R.id.nic);
        Email = findViewById(R.id.email);

        Insert = findViewById(R.id.insertbtu);
        Update = findViewById(R.id.updatebtu);
        Delete = findViewById(R.id.deletebtu);
        View = findViewById(R.id.viewbtu);

        listView = findViewById(R.id.listView);





        // මෙය සියලු id improt කරගතයු වන්නෙ dbHelper
        dbHelper = new DBHelper(this);




        arrayList = new ArrayList<>();
        Cursor data = dbHelper.getData();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(MainActivity.this,"The Database is empty  :(.",Toast.LENGTH_LONG).show();
        }else{
            int i=0;
            while(data.moveToNext()){
                model = new Model(data.getString(0),data.getString(1),data.getString(2),data.getString(3));
                arrayList.add(i,model);
                System.out.println(data.getString(0)+" "+data.getString(1)+" "+data.getString(2)+" "+data.getString(3));
                System.out.println(arrayList.get(i).getUserName());
                i++;
            }
            Adapter adapter =  new Adapter(this,R.layout.row, arrayList);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
               Model model = arrayList.get(position);
                model.getUserName();
                Intent i = new Intent(MainActivity.this, ViewData.class);
                i.putExtra("uName",model.getUserName());
                i.putExtra("age",model.getAge());
                i.putExtra("nic",model.getNIC());
                i.putExtra("email",model.getEmail());
                startActivity(i);
               // Toast.makeText(MainActivity.this, ""+model.getUserName()+"\n"+model.getAge()+"\n", Toast.LENGTH_SHORT).show();
            }
        });



        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String uName = UserName.getText().toString();
                String age = Age.getText().toString();
                String Nic = NIC.getText().toString();
                String email = Email.getText().toString();

                boolean ichek = dbHelper.insert(uName,age,Nic,email); //ඔබට පුලුවන් ඔනනෙ නම් වැලිඩෙශන් යොදගන්න
                if (ichek==true){
                    Toast.makeText(MainActivity.this, "Insert Data", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(MainActivity.this, "No Insert Data...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String uName = UserName.getText().toString();
                String age = Age.getText().toString();
                String Nic = NIC.getText().toString();
                String email = Email.getText().toString();

                boolean uchek = dbHelper.update(uName,age,Nic,email); //ඔබට පුලුවන් ඔනනෙ නම් වැලිඩෙශන් යොදගන්න
                if(uchek==true) {
                    Toast.makeText(MainActivity.this, "Update Data", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "No Update Data...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String Nic = NIC.getText().toString();

                boolean dchek = dbHelper.delete(Nic); //ඔබට පුලුවන් ඔනනෙ නම් වැලිඩෙශන් යොදගන්න
                if(dchek==true){
                    Toast.makeText(MainActivity.this, "Delete Data", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "No Delete Data...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // view මෙතඩ් එක
        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Cursor res = dbHelper.getData();
                if (res.getCount()==0){ //කිසිම ඩෙටා එකක් නැතිනම් මෙවැනී ටොස්ට් එකක් පෙන්වයි
                    Toast.makeText(MainActivity.this, "No Enter Data..", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
                    buffer.append("Age :"+res.getString(1)+"\n");
                    buffer.append("NIC :"+res.getString(2)+"\n");
                    buffer.append("Email :"+res.getString(3)+"\n\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Detail");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

    }

}