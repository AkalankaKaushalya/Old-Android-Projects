package com.myschool.Tabs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.myschool.Adapters.Adapter;
import com.myschool.AddMyNotsActivity;
import com.myschool.DBHelper.DBhelper;
import com.myschool.Models.Model;
import com.myschool.MynoteViewActivity;
import com.myschool.R;
import com.myschool.UpdeteActivity;

import java.util.ArrayList;


public class MyNotesFragment extends Fragment {

    DBhelper dBhelper;
    Button addNote;
    TextView countTv;

    //DBhelper dbHelper;
    ListView listView;


    ArrayList<Model> arrayList;
    Model model;
    Context context;

    public MyNotesFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dBhelper = new DBhelper(getContext());

        //addNote = getView().findViewById(R.id.myaddNoteBtn);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_notes, container, false);

        countTv = view.findViewById(R.id.countTv);
        int countToDo = dBhelper.getCount();
        countTv.setText(String.valueOf(countToDo));

        addNote = view.findViewById(R.id.myaddNoteBtn);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddMyNotsActivity.class));
            }
        });


        listView = (ListView) view.findViewById(R.id.mynoteLv);

        dBhelper = new DBhelper(getContext());
        context = getContext();

        arrayList = new ArrayList<>();
        Cursor data = dBhelper.getNotes();
        int numRows = data.getCount();
        if (numRows == 0) {
            Toast.makeText(getContext(), "The Database is empty  :(.", Toast.LENGTH_LONG).show();
        } else {
            int i = 0;
            while (data.moveToNext()) {
                model = new Model(data.getInt(0), data.getString(1), data.getString(2), data.getString(3));
                arrayList.add(i, model);
                System.out.println(data.getInt(0) + " " + data.getString(1) + " " + data.getString(2) + " " + data.getString(3));
                System.out.println(arrayList.get(i).getID());
                i++;
            }
            Adapter adapter = new Adapter(getContext(), R.layout.note_row, arrayList);
            // Adapter adapter = new
            //listView = (ListView) findViewById(R.id.listNote);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Model model = arrayList.get(position);
                    Intent intent = new Intent(getContext(), MynoteViewActivity.class);
                    intent.putExtra("Id", model.getID());
                    intent.putExtra("titl", model.getTitle());
                    intent.putExtra("mess", model.getMasseage());
                    startActivity(intent);
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Update Your Note");
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Model model = arrayList.get(position);
                            int i = model.getID();

                            Intent g = new Intent(getContext(), UpdeteActivity.class);

                            //This is Pass Int values in Next Intent
                            Bundle b = new Bundle();
                            b.putInt("Id", i);
                            g.putExtras(b);//..

                            g.putExtra("title", model.getTitle());
                            g.putExtra("masseage", model.getMasseage());
                            startActivity(g);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    // Show Recover Dialog //
                    builder.create().show();
                    return false;
                }
            });


        }

        return view;
    }


}