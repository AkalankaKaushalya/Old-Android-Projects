package com.maliya.tool_master;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.maliya.tool_master.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends AppCompatActivity {
    Window window;
    ActivityAboutUsBinding binding;

    FirebaseRemoteConfig firebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.stutas_bar));
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.stutas_bar));
        }

        int curentViersncode;

        curentViersncode = getCurentVirsion();
        binding.versionTv.setText(String.valueOf(curentViersncode + ".0"));


        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(1)
                .build();
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    final String new_version_code = firebaseRemoteConfig.getString("newcode");
                    if (Integer.parseInt(new_version_code) > getCurentVirsion()) {
                        AlertDialog dialog = new AlertDialog.Builder(AboutUsActivity.this)
                                .setTitle("New Update Available")
                                .setMessage("Update Now")
                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=vmFR8JP40vc")));
                                        } catch (Exception e) {
                                            Toast.makeText(AboutUsActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).show();
                        dialog.setCancelable(false);
                    }
                }
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        binding.waIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(String.valueOf("https://wa.me/+94757660648"));
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(uri);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        binding.instIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(String.valueOf("https://www.instagram.com/kaushalya7.akalanka/"));
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(uri);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        binding.fbIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(String.valueOf("https://www.facebook.com/groups/2867182636897006/"));
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(uri);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }

    private int getCurentVirsion() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (Exception e) {

        }
        return packageInfo.versionCode;
    }
}