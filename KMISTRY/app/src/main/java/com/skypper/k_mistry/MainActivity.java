package com.skypper.k_mistry;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.telephony.NetworkRegistrationInfo;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.Shimmer;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import com.skypper.k_mistry.settings.AboutActivity;

import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity  {

    private ImageView button;
    private SearchView searchView;
    private TextView textView_search,showall1;

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    RelativeLayout internet_layout,no_internet,recy_layout;
    LinearLayout shimmer_itemsss;
    SwipeRefreshLayout swipeRefreshLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FirebaseRemoteConfig remoteConfig,remoteConfig_update;

    DatabaseReference databaseReference;


    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isConnected(this)) {
            showInternetDailog();
        }



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



        recyclerView = findViewById(R.id.recyclerView);
        internet_layout = findViewById(R.id.contentLayout);
        no_internet = findViewById(R.id.emptyView);
        recyclerView.setVisibility(View.INVISIBLE);




        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lessons_List").limitToFirst(15), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options,getApplicationContext());
        recyclerView.setAdapter(mainAdapter);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                recyclerView.setVisibility(View.VISIBLE);
                shimmer_itemsss.setVisibility(View.INVISIBLE);


            }
        },5000);


        button = findViewById(R.id.notif);
        searchView = findViewById(R.id.search_view);
        textView_search = findViewById(R.id.search_text);



        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    textView_search.setVisibility(View.GONE);

            }
        });

        showall1 = findViewById(R.id.showAllRecommended);
        showall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, All_LessonActivity.class));
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                txtSearch(s);
                textView_search.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                txtSearch(s);
                textView_search.setVisibility(View.GONE);
                return false;
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        shimmer_itemsss = findViewById(R.id.shimmer_items);

        recy_layout = findViewById(R.id.recycler_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                          }
        });



                //Drawer eka thama
       drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        // Dark Mod Preferences

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


        //Drawer Design
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                int id = item.getItemId();
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {

                    case R.id.nav_home:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
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
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;

                    case R.id.nav_login:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
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


        ///  remote config sago
        int currentVersionCode;
        currentVersionCode = getCurrentVersionCode();

        Log.d("My App", String.valueOf(currentVersionCode));
        remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(5)
                .build();
        remoteConfig.setConfigSettingsAsync(configSettings);
        remoteConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()){
                    final String new_version_code = remoteConfig.getString("new_version_code");
                    if (Integer.parseInt(new_version_code) > 1000){
                        showMaintenanceMessage();
                    }
                }
            }
        });


        remoteConfig_update = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings_update = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(5)
                .build();
        remoteConfig_update.setConfigSettingsAsync(configSettings_update);
        remoteConfig_update.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()){
                    final String new_version_code = remoteConfig.getString("update_code");
                    if (Integer.parseInt(new_version_code) > getCurrentVersionCode()){
                        showUpdateMessage();
                    }
                }
            }
        });




    }

    private void showUpdateMessage() {

        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

       View view = LayoutInflater.from(this).inflate(R.layout.update_dialog, findViewById(R.id.no_internet_layout));

        view.findViewById(R.id.try_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp")));
                } catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id= 993523509333")));
                }
            }
        });
       dialog.setContentView(view);
       dialog.setCancelable(false);
       dialog.show();
    }

    private void showMaintenanceMessage() {

        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View view = LayoutInflater.from(this).inflate(R.layout.maintence_break, findViewById(R.id.no_internet_layout));

        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();


    }

    public void showInternetDailog() {


        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        View view = LayoutInflater.from(this).inflate(R.layout.no_internet_dialog, findViewById(R.id.no_internet_layout));
        view.findViewById(R.id.try_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected(MainActivity.this)) {
                    showInternetDailog();
                    shimmer_itemsss.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    shimmer_itemsss.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    finish();
                }
            }
        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();

    }

    private boolean isConnected(MainActivity mainActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConn !=null && wifiConn.isConnected() || (mobileConn !=null && mobileConn.isConnected()));


    }

    private void txtSearch(String str)
    {
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lessons_List").orderByChild("name").startAt(str).endAt(str+"~"), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options,getApplicationContext());
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }

    private int getCurrentVersionCode(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
        } catch (Exception e){
            Log.d("My App",e.getMessage());
        }
        return packageInfo.versionCode;
    }


}

