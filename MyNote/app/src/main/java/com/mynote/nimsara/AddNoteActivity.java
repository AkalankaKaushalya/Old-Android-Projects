package com.mynote.nimsara;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mynote.nimsara.DB.DBHelper;

import java.util.Calendar;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {
    EditText Ntitle, Nmasseage;
    TextView Nadddate;
    ImageButton BackPas;
    DBHelper dbHelper;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Ntitle = findViewById(R.id.NtitleEd);
        Nmasseage = findViewById(R.id.NmasseageEd);
        Nadddate = findViewById(R.id.notedateTV);

        BackPas = findViewById(R.id.backBtn);

        dbHelper = new DBHelper(this);


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

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        String title = Ntitle.getText().toString();
        String masseage = Nmasseage.getText().toString();

        boolean chake = dbHelper.addNote(id, title, masseage );
        if (chake == true) {
            Toast.makeText(AddNoteActivity.this, "Save", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddNoteActivity.this, "Note Save", Toast.LENGTH_SHORT).show();
        }
    }*/
}