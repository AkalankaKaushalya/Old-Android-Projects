package com.hiru.waterbill_saving;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hiru.waterbill_saving.DBConnect.DBConnect;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalculationActivity extends AppCompatActivity {

    FloatingActionButton ViewBill;

    DBConnect dbConnect;
    TextView ThisMounth, LastMounth, UseUnit, OneUnitP, Totle;
    EditText Thisuseg, Lastuseage, BillID;
    Button SaveBtn, CalculaterBtn;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        dbConnect = new DBConnect(this);

        ThisMounth = findViewById(R.id.nowMonthTv);
        LastMounth = findViewById(R.id.lastMonthTv);
        UseUnit = findViewById(R.id.useUnitTv);
        OneUnitP = findViewById(R.id.oneunitPrise);
        Totle = findViewById(R.id.totleTv);

        Thisuseg = findViewById(R.id.nowMonthUnitEt);
        Lastuseage = findViewById(R.id.lastMonthUnitEt);
        BillID = findViewById(R.id.billid);

        SaveBtn = findViewById(R.id.saveData);
        CalculaterBtn = findViewById(R.id.cal);

        ViewBill = findViewById(R.id.showList);



        ViewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CalculationActivity.this, ListActivity.class));
            }
        });

        ThisMounth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH,month);
                        myCalendar.set(Calendar.DAY_OF_MONTH,day);
                        updateLabel();
                    }
                };
                ThisMounth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(CalculationActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
            private void updateLabel(){
                String myFormat="MM.dd.yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                ThisMounth.setText(dateFormat.format(myCalendar.getTime()));
            }

        });
        LastMounth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH,month);
                        myCalendar.set(Calendar.DAY_OF_MONTH,day);
                        updateLabel();
                    }
                };
                LastMounth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(CalculationActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
            private void updateLabel(){
                String myFormat="MM.dd.yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                LastMounth.setText(dateFormat.format(myCalendar.getTime()));
            }

        });

        CalculaterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l = Lastuseage.getText().toString();
                String n = Thisuseg.getText().toString();
                String on = OneUnitP.getText().toString();


                int Lmu = Integer.parseInt(l);
                int Nmu = Integer.parseInt(n);

                int useunit = Nmu - Lmu;

                UseUnit.setText(String.valueOf(useunit));

                int oneUnit = Integer.parseInt(on);

                int totle = oneUnit * Nmu;

                Totle.setText(String.valueOf(totle));

            }
        });

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = BillID.getText().toString();
                String month = ThisMounth.getText().toString();
                String unit = Thisuseg.getText().toString();
                String use = UseUnit.getText().toString();
                String total = Totle.getText().toString();
                if (id.isEmpty()) {
                    Toast.makeText(CalculationActivity.this, "Bill ID is empty", Toast.LENGTH_SHORT).show();
                }else if (month.isEmpty()) {
                    Toast.makeText(CalculationActivity.this, "This Month is empty", Toast.LENGTH_SHORT).show();
                }else if (unit.isEmpty()) {
                    Toast.makeText(CalculationActivity.this, "This Month Unit is empty", Toast.LENGTH_SHORT).show();
                }else if (use.isEmpty()) {
                    Toast.makeText(CalculationActivity.this, "Use Unit is empty", Toast.LENGTH_SHORT).show();
                }else if (total.isEmpty()) {
                    Toast.makeText(CalculationActivity.this, "Total Price is empty", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = dbConnect.addData(id,month,unit,use,total);
                    if (check==true){
                        Toast.makeText(CalculationActivity.this, "Data is Uplode..", Toast.LENGTH_SHORT).show();
                        BillID.setText(null);
                        LastMounth.setText(null);
                        ThisMounth.setText(null);
                        Thisuseg.setText (null);
                        Lastuseage.setText (null);
                        UseUnit.setText(null);
                        Totle.setText(null);
                        return;
                    }
                    else{
                        Toast.makeText(CalculationActivity.this, "Erro Not Not Uploed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}