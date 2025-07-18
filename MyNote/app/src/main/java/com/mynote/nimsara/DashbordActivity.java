package com.mynote.nimsara;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mynote.nimsara.Adapter.Adapter;
import com.mynote.nimsara.DB.DBHelper;
import com.mynote.nimsara.Model.Model;
import com.mynote.nimsara.databinding.DialogEditDeleteBinding;

import java.util.ArrayList;

public class DashbordActivity extends AppCompatActivity {
    FloatingActionButton addnote;
    DBHelper dbHelper;
    ListView listView;

    ArrayList<Model> arrayList;
    Model model;
    Context context;

    //Adapter adapter;

    EditText serched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        addnote = findViewById(R.id.AddNoteFbtn);

        serched = findViewById(R.id.searchEd);


        listView = (ListView) findViewById(R.id.listNote);

        dbHelper = new DBHelper(this);
        context = this;

        arrayList = new ArrayList<>();
        Cursor data = dbHelper.getNotes();
        int numRows = data.getCount();
        if (numRows == 0) {
            Toast.makeText(DashbordActivity.this, "The Database is empty  :(.", Toast.LENGTH_LONG).show();
        } else {
            int i = 0;
            while (data.moveToNext()) {
                model = new Model(data.getInt(0), data.getString(1), data.getString(2), data.getString(3));
                arrayList.add(i, model);
                System.out.println(data.getInt(0) + " " + data.getString(1) + " " + data.getString(2) + " " + data.getString(3));
                System.out.println(arrayList.get(i).getID());
                i++;
            }
            Adapter adapter = new Adapter(this, R.layout.note_row, arrayList);
            // Adapter adapter = new
            //listView = (ListView) findViewById(R.id.listNote);
            listView.setAdapter(adapter);


        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                // NoteRowBinding noteRowBinding = NoteRowBinding.inflate(LayoutInflater.from(DashbordActivity.this));
                //setContentView(noteRowBinding.getRoot());
                //  noteRowBinding.getRoot();

                //   Random random = new Random();
                //   int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                //  noteRowBinding.nidLL.setBackgroundColor(color);

                Model model = arrayList.get(position);
                Intent intent = new Intent(DashbordActivity.this, ViewActivity.class);
                intent.putExtra("Id", model.getID());
                intent.putExtra("titl", model.getTitle());
                intent.putExtra("mess", model.getMasseage());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                DialogEditDeleteBinding dialogEditDeleteBinding = DialogEditDeleteBinding.inflate(LayoutInflater.from(DashbordActivity.this));

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DashbordActivity.this, R.style.CustomDialog);
                builder.setView(dialogEditDeleteBinding.getRoot());

                androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();

                dialogEditDeleteBinding.notedeletBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Model model = arrayList.get(position);
                        int i = model.getID();

                        boolean dchek = dbHelper.notesdelete(i); //ඔබට පුලුවන් ඔනනෙ නම් වැලිඩෙශන් යොදගන්න
                        if (dchek == true) {
                            Toast.makeText(DashbordActivity.this, "Delete Data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DashbordActivity.this, "No Delete Data...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialogEditDeleteBinding.noteUpdateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Model model = arrayList.get(position);
                        int i = model.getID();

                        Intent g = new Intent(DashbordActivity.this, UpdeteActivity.class);

                        //This is Pass Int values in Next Intent
                        Bundle b = new Bundle();
                        b.putInt("Id", i);
                        g.putExtras(b);//..

                        g.putExtra("title", model.getTitle());
                        g.putExtra("masseage", model.getMasseage());
                        startActivity(g);


                    }
                });
                return true;
            }
        });


        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DashbordActivity.this, AddNoteActivity.class));
                finish();
            }
        });


    }


}