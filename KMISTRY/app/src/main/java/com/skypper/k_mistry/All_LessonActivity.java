package com.skypper.k_mistry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.Shimmer;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class All_LessonActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
   LinearLayout shimmer;
   SwipeRefreshLayout swipeRefreshLayout;
   SearchView searchView;
    private TextView textView_search;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lesson);

        if (!isConnected(this)) {
            showInternetDailog();
        }

            //id
        recyclerView = findViewById(R.id.recyclerView);
        shimmer = findViewById(R.id.shimmer);
        textView_search = findViewById(R.id.search_text);
        searchView = findViewById(R.id.search_view);

        //Search_View
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



        //refresh Layout
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        recyclerView.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Lessons_List"), MainModel.class)
                        .build();

        mainAdapter = new MainAdapter(options,getApplicationContext());
        recyclerView.setAdapter(mainAdapter);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                recyclerView.setVisibility(View.VISIBLE);
                shimmer.setVisibility(View.INVISIBLE);


            }
        },5000);

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
                if (!isConnected(All_LessonActivity.this)) {
                    showInternetDailog();
                    shimmer.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);

                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    recyclerView.setVisibility(View.VISIBLE);
                    shimmer.setVisibility(View.INVISIBLE);
                    finish();
                }
            }
        });

        dialog.setContentView(view);
        dialog.show();

    }

    private boolean isConnected(All_LessonActivity all_lessonActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) all_lessonActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

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


}