package com.ckfood.pvt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class Welcome_Screen extends AppCompatActivity {
    VideoView videoView;
    TextView Loging_but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        videoView = findViewById(R.id.vview);
        Loging_but = findViewById(R.id.loging_but);

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

        String path="android.resource://com.ckfood.pvt/"+R.raw.login_bg;
        Uri uri =Uri.parse(path);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.setVolume(0,0);
            }
        });
        Loging_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Welcome_Screen.this,Loging_Page.class));
                finish();
            }
        });
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
    protected void onResume() {
        videoView.resume();
        super.onResume();
    }
    protected void onPause() {
        videoView.suspend();
        super.onPause();
    }
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }
}