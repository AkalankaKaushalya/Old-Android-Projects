package com.skypper.k_mistry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.Lottie;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.skypper.k_mistry.settings.AboutActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Recycler_Lessons_Activity extends AppCompatActivity {


    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,lesson_name,lesson_partname,tv11,tv12,tv13,tv14,tv15,tv16,tv17,tv18,tv19,tv20;
    RelativeLayout lottie;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView button;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_lessons);

        button = findViewById(R.id.notif);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);


        ///Google Ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {

            }

            @Override
            public void onAdOpened() {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdClosed() {

            }
        });

//Dark MOD
        SharedPreferences sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();
        final boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);

        // When user reopens the app
        // after applying dark/light mode
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        //NAVIGATION DRAWER

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {

                    case R.id.nav_home:
                        startActivity(new Intent(Recycler_Lessons_Activity.this, MainActivity.class));
                        break;
                    case R.id.nav_theme:


                        if (isDarkModeOn) {

                            // if dark mode is on it
                            // will turn it off
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_NO);
                            // it will set isDarkModeOn
                            // boolean to false
                            editor.putBoolean(
                                    "isDarkModeOn", false);
                            editor.apply();
                        }
                        else {

                            // if dark mode is off
                            // it will turn it on
                            AppCompatDelegate
                                    .setDefaultNightMode(
                                            AppCompatDelegate
                                                    .MODE_NIGHT_YES);

                            // it will set isDarkModeOn
                            // boolean to true
                            editor.putBoolean(
                                    "isDarkModeOn", true);
                            editor.apply();

                        }
                        break;


                    case R.id.about:
                        startActivity(new Intent(Recycler_Lessons_Activity.this, AboutActivity.class));
                        break;

                    case R.id.nav_login:
                        startActivity(new Intent(Recycler_Lessons_Activity.this, LoginActivity.class));
                        break;

                    case R.id.nav_share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String Body = "Download This App";
                        String Sub = "https://play.google.com";
                        intent.putExtra(Intent.EXTRA_TEXT,Body);
                        intent.putExtra(Intent.EXTRA_TEXT,Sub);
                        startActivity(Intent.createChooser(intent,"Share Using"));
                        break;

                    case R.id.nav_rate:
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp")));
                        } catch (ActivityNotFoundException e){
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id= 993523509333")));
                        }
                        break;


                    default:
                        return true;

                }
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });


        lesson_partname = findViewById(R.id.Lesson_Title);
        lesson_name = findViewById(R.id.Main_title);
        lesson_name.setText(getIntent().getStringExtra("name"));
        lottie = findViewById(R.id.lottie_layer_bg);

            //description wala ID tika
        tv1 = findViewById(R.id.Lesson_Description_1);
        tv2 = findViewById(R.id.Lesson_Description_2);
        tv3 = findViewById(R.id.Lesson_Description_3);
        tv4 = findViewById(R.id.Lesson_Description_4);
        tv5 = findViewById(R.id.Lesson_Description_5);
        tv6 = findViewById(R.id.Lesson_Description_6);
        tv7 = findViewById(R.id.Lesson_Description_7);
        tv8 = findViewById(R.id.Lesson_Description_8);
        tv9 = findViewById(R.id.Lesson_Description_9);
        tv10 = findViewById(R.id.Lesson_Description_10);


        tv11 = findViewById(R.id.Lesson_Description_11);
        tv12 = findViewById(R.id.Lesson_Description_12);
        tv13 = findViewById(R.id.Lesson_Description_13);
        tv14 = findViewById(R.id.Lesson_Description_14);
        tv15 = findViewById(R.id.Lesson_Description_15);
        tv16 = findViewById(R.id.Lesson_Description_16);
        tv17 = findViewById(R.id.Lesson_Description_17);
        tv18 = findViewById(R.id.Lesson_Description_18);
        tv19 = findViewById(R.id.Lesson_Description_19);
        tv20 = findViewById(R.id.Lesson_Description_20);

        //invisible tika
      tv1.setVisibility(View.GONE);
        tv2.setVisibility(View.GONE);
        tv3.setVisibility(View.GONE);
        tv4.setVisibility(View.GONE);
        tv5.setVisibility(View.GONE);
        tv6.setVisibility(View.GONE);
        tv7.setVisibility(View.GONE);
        tv8.setVisibility(View.GONE);
        tv9.setVisibility(View.GONE);
        tv10.setVisibility(View.GONE);

        tv11.setVisibility(View.GONE);
        tv12.setVisibility(View.GONE);
        tv13.setVisibility(View.GONE);
        tv14.setVisibility(View.GONE);
        tv15.setVisibility(View.GONE);
        tv16.setVisibility(View.GONE);
        tv17.setVisibility(View.GONE);
        tv18.setVisibility(View.GONE);
        tv19.setVisibility(View.GONE);
        tv20.setVisibility(View.GONE);

        lesson_partname.setVisibility(View.INVISIBLE);

                //swipe refresh Layout eka
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

     new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {

                lottie.setVisibility(View.GONE);

                lesson_partname.setVisibility(View.VISIBLE);

                //description_showing

                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv3.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.VISIBLE);
                tv5.setVisibility(View.VISIBLE);
                tv6.setVisibility(View.VISIBLE);
                tv7.setVisibility(View.VISIBLE);
                tv8.setVisibility(View.VISIBLE);
                tv9.setVisibility(View.VISIBLE);
                tv10.setVisibility(View.VISIBLE);

              tv11.setVisibility(View.VISIBLE);
              tv12.setVisibility(View.VISIBLE);
              tv13.setVisibility(View.VISIBLE);
              tv14.setVisibility(View.VISIBLE);
              tv15.setVisibility(View.VISIBLE);
              tv16.setVisibility(View.VISIBLE);
              tv17.setVisibility(View.VISIBLE);
              tv18.setVisibility(View.VISIBLE);
              tv19.setVisibility(View.VISIBLE);
              tv20.setVisibility(View.VISIBLE);
          }
      },3500);


        lesson_partname.setText(getIntent().getStringExtra("course"));
        tv1.setText(getIntent().getStringExtra("details_1"));
        tv2.setText(getIntent().getStringExtra("details_2"));
        tv3.setText(getIntent().getStringExtra("details_3"));
        tv4.setText(getIntent().getStringExtra("details_4"));
        tv5.setText(getIntent().getStringExtra("details_5"));
        tv6.setText(getIntent().getStringExtra("details_6"));
        tv7.setText(getIntent().getStringExtra("details_7"));
        tv8.setText(getIntent().getStringExtra("details_8"));
        tv9.setText(getIntent().getStringExtra("details_9"));
        tv10.setText(getIntent().getStringExtra("details__10"));

        tv11.setText(getIntent().getStringExtra("details__11"));
        tv12.setText(getIntent().getStringExtra("details__12"));
        tv13.setText(getIntent().getStringExtra("details__13"));
        tv14.setText(getIntent().getStringExtra("details__14"));
        tv15.setText(getIntent().getStringExtra("details__15"));
        tv16.setText(getIntent().getStringExtra("details__16"));
        tv17.setText(getIntent().getStringExtra("details__17"));
        tv18.setText(getIntent().getStringExtra("details__18"));
        tv19.setText(getIntent().getStringExtra("details__19"));
        tv20.setText(getIntent().getStringExtra("details__20"));



    }





}