package com.myschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myschool.DBHelper.DBhelper;

public class UpdeteActivity extends AppCompatActivity {

    EditText Title, Message;
    TextView IdTv;
    ImageButton backBtn;
    DBhelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updete);

        dbHelper = new DBhelper(this);

        Title = findViewById(R.id.NtitleEd);
        Message = findViewById(R.id.NmasseageEd);
        IdTv = findViewById(R.id.noteidTv);
        backBtn = findViewById(R.id.backBtn);

        Intent i = getIntent();
        String tit = i.getStringExtra("title");
        String mass = i.getStringExtra("masseage");
        // This is Cach in Int Valus in Intent
        Bundle b = this.getIntent().getExtras();
        int view = b.getInt("Id");

        Title.setText(tit);
        Message.setText(mass);
        IdTv.setText(String.valueOf(view));

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle b = this.getIntent().getExtras();
        int view = b.getInt("Id");

        String title = Title.getText().toString();
        String masseage = Message.getText().toString();

        boolean uchek = dbHelper.noteupdate(view, title, masseage); //ඔබට පුලුවන් ඔනනෙ නම් වැලිඩෙශන් යොදගන්න
        if (uchek == true) {
            Toast.makeText(UpdeteActivity.this, "Update Data", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(UpdeteActivity.this, "No Update Data...", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(UpdeteActivity.this, MyNotesFragment.class));
        }
        //finish();
    }
}