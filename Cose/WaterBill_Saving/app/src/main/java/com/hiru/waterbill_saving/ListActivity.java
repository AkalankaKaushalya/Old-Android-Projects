package com.hiru.waterbill_saving;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hiru.waterbill_saving.Adapter.Adapter;
import com.hiru.waterbill_saving.DBConnect.DBConnect;
import com.hiru.waterbill_saving.Model.Model;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    DBConnect dbConnect;
    ListView listView;
    FloatingActionButton call;
    AlertDialog.Builder builder;
    //androidx.appcompat.app.AlertDialog.Builder builder;//AlertDialog.Builder builder;
    ArrayList<Model> arrayList;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dbConnect = new DBConnect(this);
        listView = findViewById(R.id.BillList);
        call = findViewById(R.id.addBill);

        builder = new AlertDialog.Builder(ListActivity.this);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        arrayList = new ArrayList<>();
        Cursor data1 = dbConnect.getData();
        int numRows1 = data1.getCount();
        if (numRows1 == 0) {
            Toast.makeText(ListActivity.this, "The Database is empty :(.", Toast.LENGTH_LONG).show();
        } else {
            int i = 0;
            while ((data1.moveToNext())) {
                model = new Model(data1.getString(0), data1.getString(1), data1.getString(2), data1.getString(3), data1.getString(4));
                arrayList.add(i, model);
                i++;
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model model = arrayList.get(position);

                builder.setTitle(model.getBid());
                builder.setMessage("Choose Your option ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ListActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Model model = arrayList.get(position);
//
//                builder.setTitle(model.getBid());
//                builder.setMessage("Choose Your option ?");
//                builder.setCancelable(false);
//                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        boolean dcheck = dbConnect.delete(model.getBid());
//                        if (dcheck == true) {
//                            Toast.makeText(ListActivity.this, "Delete Data", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(ListActivity.this, "No Delete Data", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//
//                return true;
//            }
//        });


    }
}

//          /* Adapter adapter = new Adapter(this,R.layout.list_item,arrayList);
//            listView.setAdapter(adapter);
//        }
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Model model = arrayList.get(position);
//
//                builder.setTitle(model.getBid());
//
//                builder.setMessage("Choose Your option ?");
//                builder.setCancelable(false);
//                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        boolean dcheck = dbConnect.delete(model.getBid());
//                        if (dcheck == true) {
//                            Toast.makeText(ListActivity.this, "Delete Data", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(ListActivity.this, "No Delete Data", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
//    }
//}
//*/