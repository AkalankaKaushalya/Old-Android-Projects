package com.myschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.myschool.LogingRegisterActivitys.LoginActivity;
import com.myschool.LogingRegisterActivitys.RegisterActivity;
import com.myschool.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      /*  String path="android.resource://com.myschool/" + R.raw.main_bg;
        Uri uri = Uri.parse(path);
        binding.Vview.setVideoURI(uri);
        binding.Vview.start();

        binding.Vview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.setVolume(0,0);
            }
        });*/

        binding.MregisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        binding.MloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }
        });

    }

    /*protected void onResume() {
        binding.Vview.resume();
        super.onResume();
    }
    protected void onPause() {
        binding.Vview.suspend();
        super.onPause();
    }
    protected void onDestroy() {
        binding.Vview.stopPlayback();
        super.onDestroy();
    }*/
}