package com.ak.unitycode.sl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.ak.unitycode.sl.Fragment.CodesFragment;
import com.ak.unitycode.sl.Fragment.LessonsFragment;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.waspar.falert.Falert;
import com.waspar.falert.FalertButtonType;
import com.waspar.falert.SingleButtonListener;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AdminActivity extends AppCompatActivity {
    private MeowBottomNavigation bottomNavigation;
    private Window window;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageButton moreBtn;
    private CircleImageView profile;
    private FirebaseRemoteConfig firebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();


        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        moreBtn = findViewById(R.id.moreBtn);
        profile = findViewById(R.id.profil);

        chek();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.trns));
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.green));
        }

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_action_lessons));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_action_codes));
//        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_action_font));
//        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_action_apks));
        bottomNavigation.show(2, true);
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        replace(new LessonsFragment());
                        break;

                    case 2:
                        replace(new CodesFragment());
                        break;

//                    case 3:
//                        replace(new FontFragment());
//                        break;
//
//                    case 4:
//                        replace(new ApksFragment());
//                        break;
                }
                return null;
            }
        });



        moreBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(AdminActivity.this, moreBtn, Gravity.CENTER_HORIZONTAL);
                popupMenu.getMenu().add(R.menu.menu_main, 0, 0, "privacy policy");
                popupMenu.getMenu().add(Menu.NONE, 1, 0, "About Us");
                popupMenu.getMenu().add(Menu.NONE, 2, 0, "LogOut");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        if (id == 0) {
                            startActivity(new Intent(AdminActivity.this, PolicyActivity.class));
                        } else if (id == 1) {
                            startActivity(new Intent(AdminActivity.this, AboutActivity.class));
                        } else if (id == 2) {
                            firebaseAuth.signOut();
                            startActivity(new Intent(AdminActivity.this, SplashActivity.class));
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, ProfileActivity.class));
            }
        });

        int currentVersionCode;
        currentVersionCode = getCurrentVersionCode();


    }

    private void chekForUpdate() {
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings_update = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(5)
                .build();
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings_update);
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()) {
                    final String new_version_code = firebaseRemoteConfig.getString("update_code");
                    if (Integer.parseInt(new_version_code) > getCurrentVersionCode()) {
                        //Toast.makeText(AdminActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                        UpdatedilogMasseage();
                    }
                }
            }
        });
    }


    private int getCurrentVersionCode() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (Exception e) {

        }
        return packageInfo.versionCode;
    }


    private void UpdatedilogMasseage() {
        LayoutInflater inflaterr = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflaterr.inflate(R.layout.custom_view, null, false);

        @SuppressLint("UseCompatLoadingForDrawables") Falert falert = new Falert(this)
                .setButtonType(FalertButtonType.ONE_BUTTON)
                .customView(customView)
                .setAutoDismiss(true)
                .setSingleButtonBackground(getResources().getColor(R.color.white))
                .setSingleButtonTextColor(getResources().getColor(R.color.green))
                .setPositiveText("UPDATE")
                .setHeaderIcon(getResources().getDrawable(R.drawable.update_img))
                .setAlertRadius(40)
                .setstrokeButtonsSize(3)
                .setButtonRadius(80)
                .setButtonTextSize(13)
                .setHeaderIconEnable(true)
                .setButtonEnable(true)
                .setCancelableTouchOutside(true)
                //.setTypeFace(Typeface.createFromAsset(getAssets(), "bsans.ttf"))
                .setSingleButtonListener(new SingleButtonListener() {
                    @Override
                    public void onClick() {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=knownme.hashcode.android")));
                    }
                });
        falert.show(getSupportFragmentManager(), "");
        falert.setCancelableTouchOutside(false);
        falert.setCancelable(false);
    }

    private void chek() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = (String) snapshot.child("Name").getValue();
                        try {
                            if (name.equals("")) {
                                startActivity(new Intent(AdminActivity.this, DataActivity.class));
                                Toast.makeText(AdminActivity.this, "Enter Your Data", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (Exception ignored) {

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }  // නම ඇතුලත් කර ඇතිද බලන කොඩ් එක


    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    protected void onStart() {
        super.onStart();
        chekForUpdate();
        replace(new CodesFragment());
    }


    @Override
    protected void onResume() {
        super.onResume();
        replace(new CodesFragment());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        replace(new CodesFragment());
    }
}