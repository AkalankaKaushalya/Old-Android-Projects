package com.electricalmeterbile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.electricalmeterbile.DB.DBHelper;
import com.electricalmeterbile.databinding.DialogEditDeleteBinding;

import java.util.ArrayList;

public class BileViewActivity extends AppCompatActivity {
    ListView listView;
    DBHelper dbHelper;


    ArrayList<Model> arrayList;
    Model model;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bile_view);
        listView = findViewById(R.id.list);
        dbHelper = new DBHelper(this);


        context = this;

        arrayList = new ArrayList<>();
        Cursor data = dbHelper.getNotes();
        int numRows = data.getCount();
        if (numRows == 0) {
            Toast.makeText(BileViewActivity.this, "The Database is empty  :(.", Toast.LENGTH_LONG).show();
        } else {
            int i = 0;
            while (data.moveToNext()) {
                model = new Model(data.getString(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5), data.getString(6), data.getString(7));
                arrayList.add(i, model);
                i++;
            }
            Adapter adapter = new Adapter(this, R.layout.bil_row, arrayList);
            listView.setAdapter(adapter);
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                DialogEditDeleteBinding dialogEditDeleteBinding = DialogEditDeleteBinding.inflate(LayoutInflater.from(BileViewActivity.this));

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(BileViewActivity.this, R.style.CustomDialog);
                builder.setView(dialogEditDeleteBinding.getRoot());

                androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();

                dialogEditDeleteBinding.datadeletBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Model model = arrayList.get(position);
                        String i = model.getBilNo();

                        boolean dchek = dbHelper.datadelete(i); //ඔබට පුලුවන් ඔනනෙ නම් වැලිඩෙශන් යොදගන්න
                        if (dchek == true) {
                            Toast.makeText(BileViewActivity.this, "Delete Data", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BileViewActivity.this, "No Delete Data...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialogEditDeleteBinding.dataUpdateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Model model = arrayList.get(position);
                        String i = model.getBilNo();

                        Intent g = new Intent(BileViewActivity.this, UpdateActivity.class);

                        //This is Pass Int values in Next Intent
                        Bundle b = new Bundle();
                        /*b.putInt("Id", i);
                        g.putExtras(b);*/

                        g.putExtra("1", model.getBilNo());
                        g.putExtra("2", model.getLbdate());
                        g.putExtra("3", model.getNbdate());
                        g.putExtra("4", model.getLunit());
                        g.putExtra("5", model.getNunit());
                        g.putExtra("6", model.getNuseunit());
                        g.putExtra("7", model.getOneUnitP());
                        g.putExtra("8", model.getThisMonPris());
                        startActivity(g);


                    }
                });
                return true;
            }
        });

    }
}