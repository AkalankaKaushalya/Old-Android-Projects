package com.example.list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    Button adta;
    ListView listv;
    TextView count;
    private DBconnect dBconnect;
    private List<ToDoModelclz> toDoModelclzs;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        adta = findViewById(R.id.addbtn);
        listv = findViewById(R.id.list);
        count = findViewById(R.id.co);


        dBconnect = new DBconnect(context);
        toDoModelclzs = new ArrayList<>();
        toDoModelclzs = dBconnect.getAllToDos();

        ToDoAdapter adapter = new ToDoAdapter(context,R.layout.list_item,toDoModelclzs);
        listv.setAdapter(adapter);


        int countToDo = dBconnect.countToDo();
        count.setText("You have "+countToDo+" tasks");

        adta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),addtodo.class);
                startActivity(i);
                finish();
            }
        });

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoModelclz toDoModelclz = toDoModelclzs.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(toDoModelclz.getTitle());
                builder.setMessage(toDoModelclz.getDescription() );
                builder.setPositiveButton("Finished",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toDoModelclz.setFinished(System.currentTimeMillis());
                        dBconnect.updateSingleToDo(toDoModelclz);

                        startActivity(new Intent(context,MainActivity.class));
                        finish();

                    }
                });
                builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context,edittodo.class);
                        intent.putExtra("id",String.valueOf(toDoModelclz.getId()));
                        startActivity(intent);
                        finish();

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dBconnect.deletetodo(toDoModelclz.getId());
                        startActivity(new Intent(context,MainActivity.class));
                        finish();
                    }
                });
                builder.show();
            }
        });

    }
}