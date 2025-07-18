package com.ak.unitycode.sl.ViewsActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ak.unitycode.sl.MoreLodings.MoreLodings;
import com.ak.unitycode.sl.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CodeViewActivity extends AppCompatActivity {
    private String codeid, zipurl, codetite;
    private TextView codetitle, codedis, downcount, viewcount, size, uplodedate;
    private Button dowBtn;
    private ImageView priveImage;
    private ProgressBar progressBar;
    private RewardedAd mRewardedAd;
    private AdView mAdView;
    private ActivityResultLauncher<String> resultLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    MoreLodings.DownloadBook(CodeViewActivity.this, "" + codeid, "" + codeid, "" + zipurl);
                }

            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_view);
//-----------------------------------------------------------------------------------------------------//
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        reWAds();
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
//-----------------------------------------------------------------------------------------------------//

        Intent intent = getIntent();
        codeid = intent.getStringExtra("CODEID");
        zipurl = intent.getStringExtra("ZIPURL");
        codetite = intent.getStringExtra("CODETILE");

        codetitle = findViewById(R.id.codetitleTv);
        codedis = findViewById(R.id.descriptionTv);
        downcount = findViewById(R.id.downloadsTv);
        viewcount = findViewById(R.id.viewsTv);
        size = findViewById(R.id.sizeTv);
        uplodedate = findViewById(R.id.dateTv);
        dowBtn = findViewById(R.id.downloadCodeBtn);
        priveImage = findViewById(R.id.priveView);
        progressBar = findViewById(R.id.progressBar);

        MoreLodings.CodeViewContent(codeid);

        loadCodes();

        dowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRewardedAd != null) {
                    //Activity activityContext = CodeViewActivity.this;
                    mRewardedAd.show(CodeViewActivity.this, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            int rewardAmount = rewardItem.getAmount();
                            //String rewardType = rewardItem.getType();
                            Toast.makeText(CodeViewActivity.this, "" + rewardAmount, Toast.LENGTH_SHORT).show();
                            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    super.onAdDismissedFullScreenContent();
                                    if (ContextCompat.checkSelfPermission(CodeViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                        MoreLodings.DownloadBook(CodeViewActivity.this, "" + codeid, "" + codeid, "" + zipurl);
                                    } else {
                                        resultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                    }
                                    mRewardedAd = null;
                                }
                            });
                        }
                    });
                } else {
                    if (ContextCompat.checkSelfPermission(CodeViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        MoreLodings.DownloadBook(CodeViewActivity.this, "" + codeid, "" + codeid, "" + zipurl);
                    } else {
                        resultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                }


            }
        });


    }

    private void reWAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-9382446765591725/9306282835",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });
    }

    private void loadCodes() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Codes");
        ref.child(codeid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String codeTitle = "" + snapshot.child("CodeName").getValue();
                        String description = "" + snapshot.child("Codedescr").getValue();
                        String priveimg = "" + snapshot.child("Preview_img").getValue();
                        String zipurl = "" + snapshot.child("CodeUrl").getValue();
                        String views = "" + snapshot.child("viewsCount").getValue();
                        String douncount = "" + snapshot.child("downlodsCount").getValue();
                        long date = Long.parseLong("" + snapshot.child("timestamp").getValue());

                        codetitle.setText(codeTitle);
                        codedis.setText(description);
                        downcount.setText(douncount);
                        viewcount.setText(views.replace("null", "N/A"));
                        MoreLodings.loadZipSize(zipurl, size);
                        MoreLodings.formatTimestamp(date, uplodedate);

                        try {
                            progressBar.setVisibility(View.GONE);
                            Picasso.get().load(priveimg).into(priveImage);
                        } catch (Exception e) {
                            Picasso.get().load(R.drawable.next).into(priveImage);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}