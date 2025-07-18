package com.maliya.tool_master;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.maliya.tool_master.Fragment.KaliFragment;
import com.maliya.tool_master.Fragment.TermuxFragment;
import com.maliya.tool_master.databinding.ActivityDashbordBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class DashbordActivity extends AppCompatActivity {
    ActivityDashbordBinding binding;
    Window window;

    FirebaseAuth firebaseAuth;
    FirebaseRemoteConfig firebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashbordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseMessaging.getInstance().subscribeToTopic("all");

        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.stutas_bar));
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.stutas_bar));
        }

        binding.bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.linux));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.contact));
        binding.bottomNavigation.show(2, true);
        binding.bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        replace(new KaliFragment());
                        binding.titleTv.setText("Kali Linux");
                        break;

                    case 2:
                        replace(new TermuxFragment());
                        binding.titleTv.setText("Termux");
                        break;

                }
                return null;
            }
        });


        binding.profileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashbordActivity.this, ProfileActivity.class));
            }
        });

        binding.MoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(DashbordActivity.this, binding.MoreBtn);
                popupMenu.getMenu().add(Menu.NONE, 0, 0, "About Us");
                popupMenu.getMenu().add(Menu.NONE, 1, 1, "Logout");
                popupMenu.getMenu().add(Menu.NONE, 2, 2, "ShareApp");
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int which = item.getItemId();
                        if (which == 0) {
                            startActivity(new Intent(DashbordActivity.this, AboutUsActivity.class));
                        } else if (which == 1) {
                            firebaseAuth.signOut();
                            startActivity(new Intent(DashbordActivity.this, SplashActivity.class));
                            finish();
                        } else if (which == 2) {
                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://www.youtube.com/channel/UC-9OyRtkgtHvVUvLKc36y1g");
                            startActivity(Intent.createChooser(sharingIntent, "Share using"));
                        }
                        return false;
                    }
                });
            }
        });

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
                        AlertDialog dialog = new AlertDialog.Builder(DashbordActivity.this)
                                .setTitle("New Update Available")
                                .setMessage("Update Now")
                                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=vmFR8JP40vc")));
                                        } catch (Exception e) {
                                            Toast.makeText(DashbordActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).show();
                        dialog.setCancelable(false);
                    }
                }
            }
        });

    }

    private void chakuser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

        } else {
            // ලොගවුට් උනට පස්සෙ යන්නෙ කෙලින්ම ස්ප්ලශ් ස්ක්‍රින් එක හරහා ලොගින් ඇක්ටිවිටි එකට //
            startActivity(new Intent(DashbordActivity.this, SplashActivity.class));
            finish();
        }
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    protected void onStart() {
        super.onStart();
        chakuser();
        replace(new TermuxFragment());
        binding.bottomNavigation.show(2, true);
        binding.titleTv.setText("Termux");

    }

    @Override
    protected void onPause() {
        super.onPause();
        replace(new TermuxFragment());
        binding.bottomNavigation.show(2, true);
        binding.titleTv.setText("Termux");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        replace(new TermuxFragment());
        binding.bottomNavigation.show(2, true);
        binding.titleTv.setText("Termux");
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