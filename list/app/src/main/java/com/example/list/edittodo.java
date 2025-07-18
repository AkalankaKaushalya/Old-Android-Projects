package com.example.list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class edittodo extends AppCompatActivity {
    private EditText etitle,edesc;
    private Button edit,cle;
    private DBconnect dBconnect;
    private Context context;
    //private long updatedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittodo);

        context = this;
        dBconnect = new DBconnect(context);

        etitle= findViewById(R.id.edittitlef);
        edesc = findViewById(R.id.editdescf);
        edit = findViewById(R.id.btnupdate);
        cle = findViewById(R.id.clear);


        final String id = getIntent().getStringExtra("id");
        ToDoModelclz toDoModelclz = dBconnect.getSingleToDo(Integer.parseInt(id));

        etitle.setText(toDoModelclz.getTitle());
        edesc.setText(toDoModelclz.getDescription());


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tit = etitle.getText().toString();
                String dec = edesc.getText().toString();

                long ctime =System.currentTimeMillis();

                Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                calendar.setTimeInMillis(ctime);

                String data = DateFormat.format("dd/MMMM/yyyy", calendar).toString();


                ToDoModelclz toDoModelclz = new ToDoModelclz(Integer.parseInt(id),tit,dec,ctime,0);
                int state =dBconnect.updateSingleToDo(toDoModelclz);
                if(state == 0){
                    Toast.makeText(context, "Not Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toasty.success(context,"Update Successfull !",Toasty.LENGTH_SHORT).show();
                    System.out.println(state);
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                }

            }
        });
    }
}