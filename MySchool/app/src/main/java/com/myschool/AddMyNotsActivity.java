package com.myschool;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myschool.DBHelper.DBhelper;

import java.util.Calendar;
import java.util.Locale;

public class AddMyNotsActivity extends AppCompatActivity {
    EditText Ntitle, Nmasseage;
    TextView Nadddate;
    ImageButton BackPas;
    DBhelper dbHelper;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_my_nots);

        Ntitle = findViewById(R.id.NtitleEd);
        Nmasseage = findViewById(R.id.NmasseageEd);
        Nadddate = findViewById(R.id.notedateTV);

        BackPas = findViewById(R.id.backBtn);

        dbHelper = new DBhelper(this);


        long timestampe = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(timestampe);

        String data = DateFormat.format("dd/MM/yyyy", calendar).toString();
        Nadddate.setText(data);


        BackPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String title = Ntitle.getText().toString();
        String masseage = Nmasseage.getText().toString();
        String date = Nadddate.getText().toString();

        if (title.isEmpty()) {
            Toast.makeText(this, "Add Title", Toast.LENGTH_SHORT).show();
        } else if (masseage.isEmpty()) {
            Toast.makeText(this, "Add Masseage", Toast.LENGTH_SHORT).show();
        } else {
            boolean chake = dbHelper.addNote(id, title, masseage, date);
            if (chake == true) {
                Toast.makeText(getApplication(), "Save", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), "Note Save", Toast.LENGTH_SHORT).show();
            }
        }
    }
}