package com.ckfood.pvt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Splash_Screen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    Connection_Detect connection_detect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash__screen);


        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 30)
        {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 30)
        {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        connection_detect=new Connection_Detect(this);
        if (connection_detect.isConnected()){
            Toast.makeText(this,"Connect",Toast.LENGTH_SHORT);
            //   Time Out Splesh Screen  //
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Splash_Screen.this, Welcome_Screen.class);
                    startActivity(i);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }else {
            Toast.makeText(this, "Connection Lost", Toast.LENGTH_SHORT).show();
        }
    }
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}