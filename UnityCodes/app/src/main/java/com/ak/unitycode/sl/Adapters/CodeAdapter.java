package com.ak.unitycode.sl.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ak.unitycode.sl.Model.CodesModel;
import com.ak.unitycode.sl.MoreLodings.MoreLodings;
import com.ak.unitycode.sl.R;
import com.ak.unitycode.sl.ViewsActivity.CodeViewActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MediaContent;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CodeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_CONTENT = 0;
    private static final int VIEW_TYPE_AD = 1;
    private List<CodesModel> codesModelList;
    private Context context;
    private RewardedAd mRewardedAd;
    private Boolean testclick = false;

    public CodeAdapter(List<CodesModel> codesModelList, Context context) {
        this.codesModelList = codesModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_CONTENT) {
            view = LayoutInflater.from(context).inflate(R.layout.code_row, parent, false);
            return new HolderCodes(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.row_native_ad, parent, false);
            return new HolderNativeAd(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_CONTENT) {

            HolderCodes holderCodes = (HolderCodes) holder;

            CodesModel model = codesModelList.get(position);
            String codetitle = model.getCodeName();
            String useruid = model.getUid();
            String like = model.getLikes();
            String codeid = model.getCodeID();
            String zipurl = model.getCodeUrl();

            MoreLodings.loadeUserName(useruid, holderCodes.username);
            holderCodes.codeTitle.setText(codetitle);


            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            holderCodes.getlikebuttonstatus(codeid, firebaseAuth.getUid());

            holderCodes.likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    testclick = true;
                    DatabaseReference likereference = FirebaseDatabase.getInstance().getReference("CodesLikes");
                    likereference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (testclick == true) {
                                if (snapshot.child(codeid).hasChild(firebaseAuth.getUid())) {
                                    likereference.child(codeid).child(firebaseAuth.getUid()).removeValue();
                                    testclick = false;
                                } else {
                                    likereference.child(codeid).child(firebaseAuth.getUid()).setValue(true);
                                    testclick = false;
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            });

            holderCodes.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mRewardedAd != null) {
                        Activity activityContext = (Activity) context;
                        mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                            @Override
                            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                // Handle the reward.
                                //Log.d(TAG, "The user earned the reward.");
                                int rewardAmount = rewardItem.getAmount();
                                String rewardType = rewardItem.getType();

                                mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        super.onAdDismissedFullScreenContent();
                                        Intent intent = new Intent(context, CodeViewActivity.class);
                                        intent.putExtra("CODEID", codeid);
                                        intent.putExtra("ZIPURL", zipurl);
                                        intent.putExtra("CODETILE", codetitle);
                                        context.startActivity(intent);
                                        mRewardedAd = null;
                                    }
                                });
                            }
                        });
                    } else {
                        Intent intent = new Intent(context, CodeViewActivity.class);
                        intent.putExtra("CODEID", codeid);
                        intent.putExtra("ZIPURL", zipurl);
                        intent.putExtra("CODETILE", codetitle);
                        context.startActivity(intent);
                    }
                }
            });

            MobileAds.initialize(context, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            reWAds();

        } else if (getItemViewType(position) == VIEW_TYPE_AD) {


            AdLoader adLoader = new AdLoader.Builder(context, "ca-app-pub-9382446765591725/6909513346")
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                            HolderNativeAd holderNativeAd = (HolderNativeAd) holder;
                            displayNativeAd(holderNativeAd, nativeAd);

                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                            Toast.makeText(context, "" + adError, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAdClicked() {
                            // Log the click event or other custom behavior.
                        }
                    })
                    .withAdListener(new AdListener() {
                        @Override
                        public void onAdClicked() {
                            super.onAdClicked();
                        }

                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            super.onAdFailedToLoad(loadAdError);

                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }

                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                        }

                        @Override
                        public void onAdOpened() {
                            super.onAdOpened();
                        }
                    })
                    .withNativeAdOptions(new NativeAdOptions.Builder().build()).build();
            adLoader.loadAd(new AdRequest.Builder().build());
        }


    }

    private void displayNativeAd(HolderNativeAd holderNativeAd, NativeAd nativeAd) {
        String headline = nativeAd.getHeadline();
        String body = nativeAd.getBody();
        String callToAction = nativeAd.getCallToAction();
        NativeAd.Image icon = nativeAd.getIcon();
        String price = nativeAd.getPrice();
        String store = nativeAd.getStore();
        Double starRating = nativeAd.getStarRating();
        String advertiser = nativeAd.getAdvertiser();
        MediaContent mediaContent = nativeAd.getMediaContent();

        if (advertiser == null) {
            holderNativeAd.ad_advertiser.setVisibility(View.GONE);

        } else {
            holderNativeAd.ad_advertiser.setVisibility(View.VISIBLE);
            holderNativeAd.ad_advertiser.setText(advertiser);
        }
        if (headline == null) {
            holderNativeAd.ad_headline.setVisibility(View.GONE);
        } else {
            holderNativeAd.ad_headline.setVisibility(View.VISIBLE);
            holderNativeAd.ad_headline.setText(headline);
        }
        if (body == null) {
            holderNativeAd.ad_body.setVisibility(View.GONE);
        } else {
            holderNativeAd.ad_body.setVisibility(View.VISIBLE);
            holderNativeAd.ad_body.setText(body);
        }
        if (callToAction == null) {
            holderNativeAd.ad_call_to_action.setVisibility(View.GONE);
        } else {
            holderNativeAd.ad_call_to_action.setVisibility(View.VISIBLE);
            holderNativeAd.ad_call_to_action.setText(callToAction);
            holderNativeAd.nativeAdView.setCallToActionView(holderNativeAd.ad_call_to_action);
        }
        if (icon == null) {
            holderNativeAd.ad_app_icon.setVisibility(View.GONE);
        } else {
            holderNativeAd.ad_app_icon.setVisibility(View.VISIBLE);
            holderNativeAd.ad_app_icon.setImageDrawable(icon.getDrawable());
        }
        if (price == null) {
            holderNativeAd.ad_price.setVisibility(View.GONE);
        } else {
            holderNativeAd.ad_price.setVisibility(View.VISIBLE);
            holderNativeAd.ad_price.setText(price);
        }
        if (store == null) {
            holderNativeAd.ad_store.setVisibility(View.GONE);
        } else {
            holderNativeAd.ad_store.setVisibility(View.VISIBLE);
            holderNativeAd.ad_store.setText(store);
        }
        if (starRating == null) {
            holderNativeAd.ad_stars.setVisibility(View.GONE);
        } else {
            holderNativeAd.ad_stars.setVisibility(View.VISIBLE);
            holderNativeAd.ad_stars.setRating(starRating.floatValue());
        }
        if (advertiser == null) {
            holderNativeAd.ad_advertiser.setVisibility(View.GONE);
        } else {
            holderNativeAd.ad_advertiser.setVisibility(View.VISIBLE);
            holderNativeAd.ad_advertiser.setText(advertiser);
        }
        if (mediaContent == null) {
            holderNativeAd.media_view.setVisibility(View.GONE);
        } else {
            holderNativeAd.media_view.setVisibility(View.VISIBLE);
            holderNativeAd.media_view.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            holderNativeAd.media_view.setMediaContent(mediaContent);
        }
        holderNativeAd.nativeAdView.setNativeAd(nativeAd);
    }


    private void reWAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, "ca-app-pub-9382446765591725/9306282835",
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

    @Override
    public int getItemCount() {
        return codesModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 6 == 0) {
            return VIEW_TYPE_AD;
        } else {
            return VIEW_TYPE_CONTENT;
        }
    }

    class HolderCodes extends RecyclerView.ViewHolder {
        TextView codeTitle, username, likecounteTv;
        ImageView likeBtn;

        public HolderCodes(@NonNull View itemView) {
            super(itemView);
            codeTitle = itemView.findViewById(R.id.codeTitleTv);
            username = itemView.findViewById(R.id.userNameTv);
            likecounteTv = itemView.findViewById(R.id.likecountTv);
            likeBtn = itemView.findViewById(R.id.likeBtn);

        }

        public void getlikebuttonstatus(final String postkey, final String userid) {
            DatabaseReference likereference = FirebaseDatabase.getInstance().getReference("CodesLikes");
            likereference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(postkey).hasChild(userid)) {
                        int likecount = (int) snapshot.child(postkey).getChildrenCount();
                        likecounteTv.setText(likecount + " likes");
                        likeBtn.setImageResource(R.drawable.ic_action_like);
                    } else {
                        int likecount = (int) snapshot.child(postkey).getChildrenCount();
                        likecounteTv.setText(likecount + " likes");
                        likeBtn.setImageResource(R.drawable.ic_action_unlike);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    class HolderNativeAd extends RecyclerView.ViewHolder {
        NativeAdView nativeAdView;
        ImageView ad_app_icon;
        TextView ad_headline, ad_advertiser, ad_body, ad_price, ad_store;
        RatingBar ad_stars;
        MediaView media_view;
        Button ad_call_to_action;

        public HolderNativeAd(@NonNull View itemView) {
            super(itemView);
            nativeAdView = itemView.findViewById(R.id.nativeAdView);
            ad_app_icon = itemView.findViewById(R.id.ad_app_icon);
            ad_headline = itemView.findViewById(R.id.ad_headline);
            ad_advertiser = itemView.findViewById(R.id.ad_advertiser);
            ad_body = itemView.findViewById(R.id.ad_body);
            ad_price = itemView.findViewById(R.id.ad_price);
            ad_store = itemView.findViewById(R.id.ad_store);
            ad_stars = itemView.findViewById(R.id.ad_stars);
            media_view = itemView.findViewById(R.id.media_view);
            ad_call_to_action = itemView.findViewById(R.id.ad_call_to_action);
        }
    }

}
