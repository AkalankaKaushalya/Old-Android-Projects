package com.waterbillcalculator.devindi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.waterbillcalculator.devindi.Db.DBHelper;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView datelastEd, datenowEd;
    EditText bilNoEd, lastunitEd, nowuseUnitEd, nowunitEd, oneUnitPriseEd, nowunitepriseEd;
    Button calculeBtn, savebtn, updateBtn, deletBtn;
    FloatingActionButton showData;
    DatePickerDialog picker;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bilNoEd = findViewById(R.id.bileNoEd);

        datelastEd = findViewById(R.id.datelastEt);
        datenowEd = findViewById(R.id.datenowEt);

        lastunitEd = findViewById(R.id.lastUnitEd);
        nowunitEd = findViewById(R.id.nowUnitEd);

        nowuseUnitEd = findViewById(R.id.nowuseUnitsEt);

        oneUnitPriseEd = findViewById(R.id.oneUnitPriseEt);
        nowunitepriseEd = findViewById(R.id.totleEt);

        calculeBtn = findViewById(R.id.calculateBtn);
        savebtn = findViewById(R.id.saveYourBill);

        showData = findViewById(R.id.showhistrFbtn);

        dbHelper = new DBHelper(this);

        oneUnitPriseEd.setText("65.05");


        datelastEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datelastEd.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }

        });

        datenowEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                datenowEd.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BillViewActivity.class));
            }
        });

        calculeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bilno = bilNoEd.getText().toString();
                String ldate = datelastEd.getText().toString();
                String nowdate = datenowEd.getText().toString();
                String lu = lastunitEd.getText().toString();
                String nu = nowunitEd.getText().toString();
                String nuu = nowuseUnitEd.getText().toString();
                String oupris = oneUnitPriseEd.getText().toString();
                if (bilno.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter bill No", Toast.LENGTH_SHORT).show();
                } else if (ldate.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Add last date", Toast.LENGTH_SHORT).show();
                } else if (nowdate.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Add toda date", Toast.LENGTH_SHORT).show();
                } else if (lu.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Last unit", Toast.LENGTH_SHORT).show();
                } else if (nu.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Now unit", Toast.LENGTH_SHORT).show();
                } else {
                    double lastUnit = Double.parseDouble(lu);
                    double nowUnit = Double.parseDouble(nu);

                    double nowuseUnit = nowUnit - lastUnit;

                    nowuseUnitEd.setText(String.valueOf(nowuseUnit));

                    double oneunitPris = Double.parseDouble(oupris);

                    double totle = nowuseUnit * oneunitPris;

                    nowunitepriseEd.setText(String.valueOf(totle));


                }

            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bilno = bilNoEd.getText().toString();
                String ldate = datelastEd.getText().toString();
                String nowdate = datenowEd.getText().toString();
                String lu = lastunitEd.getText().toString();
                String nu = nowunitEd.getText().toString();
                String nuu = nowuseUnitEd.getText().toString();
                String oupris = oneUnitPriseEd.getText().toString();
                String totle = nowunitepriseEd.getText().toString();
                if (bilno.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter bill No", Toast.LENGTH_SHORT).show();
                } else if (ldate.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Add last date", Toast.LENGTH_SHORT).show();
                } else if (nowdate.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Add toda date", Toast.LENGTH_SHORT).show();
                } else if (lu.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Last unit", Toast.LENGTH_SHORT).show();
                } else if (nu.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Now unit", Toast.LENGTH_SHORT).show();
                } else {
                    boolean chake = dbHelper.saveData(bilno, ldate, nowdate, lu, nu, nuu, oupris, totle);
                    if (chake == true) {
                        Toast.makeText(getApplication(), "Save", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(), "Note Save", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}