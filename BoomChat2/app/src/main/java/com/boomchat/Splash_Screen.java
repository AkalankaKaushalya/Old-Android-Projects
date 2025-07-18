package com.boomchat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Splash_Screen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    Connection_Detect connection_detect;

    private View decorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
       getSupportActionBar().hide();
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Connection To Data In App //
        connection_detect=new Connection_Detect(this);
        if (connection_detect.isConnected()){
            Toast.makeText(this,"Connect App",Toast.LENGTH_LONG);
            //   Time Out Splesh Screen  //
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Splash_Screen.this, Login_Page.class);
                    startActivity(i);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }else {
            Toast.makeText(this,"Piles Connect Your Data",Toast.LENGTH_LONG).show();
            showDialog();

        }

    }
    private void showDialog() {
        AlertDialog.Builder Builder = new AlertDialog.Builder(Splash_Screen.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(Splash_Screen.this).inflate(R.layout.not_connection, (LinearLayout) findViewById(R.id.lost_connetion));
        Builder.setView(view);
       

        final AlertDialog alertDialog = Builder.create();
        view.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        }
        alertDialog.show();

    }
}