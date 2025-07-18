package com.mysqlitepatrical;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Name,Nic,Number,Email;
    Button Insart,Update,Delet,View;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = findViewById(R.id.uName);
        Nic = findViewById(R.id.uNic);
        Number = findViewById(R.id.uNumber);
        Email = findViewById(R.id.uEmail);

        Insart = findViewById(R.id.insert_but);
        Update = findViewById(R.id.update_but);
        Delet = findViewById(R.id.delet_but);
        View = findViewById(R.id.viwe_but);

        //පසුව අපී ලියපු ක්ලස් එක අපෙ ඇක්ටිවිටි ජවා එකට හදුන්වා දිය යුතුයි ඒ සදහා මුලින් එය ඉන්පොර්ට් කරගත යුතුයි

        DB = new DBHelper(this); // මෙයින් db helper අප මෙය තුල පෙන්විමට ගන්නවා

        // ඉන්සර්ට් බටන් එකට ලියපු කුවරි එක සෙට් කිරිම
        Insart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String name = Name.getText().toString();
                String nic = Nic.getText().toString();
                String number = Number.getText().toString();
                String email = Email.getText().toString();

                Boolean checkinsert = DB.insertdata(name, nic, number, email);
                if (checkinsert==true){
                    Name.setText(null);
                    Nic.setText(null);
                    Number.setText(null);
                    Email.setText(null);
                    Toast.makeText(MainActivity.this, "Insert Data...", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Not Insert Data..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // අප්ඩෙට් බටන් එකට ලියපු කුවරි එක සෙට් කිරිම
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String name = Name.getText().toString();
                String nic = Nic.getText().toString();
                String number = Number.getText().toString();
                String email = Email.getText().toString();

                Boolean checkupdate = DB.updatedata(name, nic, number, email);
                if (checkupdate==true){
                    Name.setText(null);
                    Nic.setText(null);
                    Number.setText(null);
                    Email.setText(null);
                    Toast.makeText(MainActivity.this, "Update Data...", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Not Update Data..", Toast.LENGTH_SHORT).show();
                }
            }
        });



        // Delet බටන් එකට ලියපු කුවරි එක සෙට් කිරිම
        Delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String nic = Nic.getText().toString();

                Boolean checkdelet = DB.deletdata(nic);
                if (checkdelet==true){
                    Name.setText(null);
                    Nic.setText(null);
                    Number.setText(null);
                    Email.setText(null);
                    Toast.makeText(MainActivity.this, "Delete Data...", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Not Update Data..", Toast.LENGTH_SHORT).show();
                }
            }
        });


        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Cursor res = DB.getdata();
                if (res.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Enter Data..", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
                    buffer.append("NIC :"+res.getString(1)+"\n");
                    buffer.append("Mobail :"+res.getString(2)+"\n");
                    buffer.append("Email :"+res.getString(3)+"\n\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("user Entris");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });


    }
}