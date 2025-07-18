package com.example.list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class addtodo extends AppCompatActivity {
    private EditText title,desc;
    private Button add,cl;
    private DBconnect dBconnect;
    private Context context;

    public void clear(){
        title.getText().clear();
        desc.getText().clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodo);

        title = findViewById(R.id.edittitlef);
        desc = findViewById(R.id.editdescf);
        add = findViewById(R.id.btnupdate);
        cl = findViewById(R.id.clear);
        context = this;

        dBconnect = new DBconnect(context);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titles = title.getText().toString();
                String descs = desc.getText().toString();
                long ctime =System.currentTimeMillis();
               // Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
               // calendar.setTimeInMillis(ctime);

               // String data = DateFormat.format("E,dd/MMMM/yyyy", calendar).toString();
                //Nadddate.setText(data);

                if (titles.isEmpty() || descs.isEmpty()){
                    Toasty.warning(context,"Title Or Descriotion fields empty",Toasty.LENGTH_LONG).show();
                }else{
                    ToDoModelclz toDoModelclz = new ToDoModelclz(titles,descs,ctime,0);
                    dBconnect.addToDo(toDoModelclz);
                    startActivity(new Intent(context,MainActivity.class));
                    finish();
                    Toasty.success(context,"Add New Task",Toasty.LENGTH_SHORT).show();
                }
            }
        });

        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });


    }
}