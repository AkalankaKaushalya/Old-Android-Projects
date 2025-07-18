package com.skypper.k_mistry;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.nativead.NativeAd;

import java.util.ArrayList;


public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    Context context;

    public static int ITEM_VIEW = 0;
    public static int AD_VIEW = 1;



    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options, Context context) {
        super(options);
        this.context = context;

    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {

        if (position % 5 == 0 && position!=0){



            AdLoader.Builder builder = new AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110");
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {

                            holder.templateView.setNativeAd(nativeAd);
                        }
                    })
                    .build();
            final AdLoader adLoader = builder.build();
            adLoader.loadAd(new AdRequest.Builder().build());



            holder.templateView.setVisibility(View.VISIBLE);
        }





        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.course.setText(model.getCourse());

        Glide.with(holder.img.getContext())
                .load(model.getSurl())
                .placeholder(R.drawable.ic_undraw_empty)
                .circleCrop()
                .error(R.drawable.ic_undraw_empty)
                .into(holder.img);


        holder.list_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Recycler_Lessons_Activity.class);
                intent.putExtra("name", model.getName());

                intent.putExtra("surl", model.getSurl());
                intent.putExtra("email", model.getEmail());
                intent.putExtra("course", model.getCourse());
                intent.putExtra("details_1", model.getDetails_1());
                intent.putExtra("details_2", model.getDetails_2());
                intent.putExtra("details_3", model.getDetails_3());
                intent.putExtra("details_4", model.getDetails_4());
                intent.putExtra("details_5", model.getDetails_5());
                intent.putExtra("details_6", model.getDetails_6());
                intent.putExtra("details_7", model.getDetails_7());
                intent.putExtra("details_8", model.getDetails_8());
                intent.putExtra("details_9", model.getDetails_9());
                intent.putExtra("details__10", model.getDetails__10());

                intent.putExtra("details__20", model.getDetails__20());
                intent.putExtra("details__11", model.getDetails__11());
                intent.putExtra("details__12", model.getDetails__12());
                intent.putExtra("details__13", model.getDetails__13());
                intent.putExtra("details__14", model.getDetails__14());
                intent.putExtra("details__15", model.getDetails__15());
                intent.putExtra("details__16", model.getDetails__16());
                intent.putExtra("details__17", model.getDetails__17());
                intent.putExtra("details__18", model.getDetails__18());
                intent.putExtra("details__19", model.getDetails__19());


                Glide.with(holder.img.getContext())
                        .load(model.getSurl())
                        .placeholder(R.drawable.ic_undraw_empty)
                        .circleCrop()
                        .error(R.drawable.ic_undraw_empty)
                        .into(holder.img);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paper_listview, parent, false);
      return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, course, email;
        LinearLayout list_background;

        TemplateView templateView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.lesson_name);
            course = (TextView) itemView.findViewById(R.id.main_name);
            email = (TextView) itemView.findViewById(R.id.age);
            list_background = (LinearLayout) itemView.findViewById(R.id.list_background);
            templateView = (TemplateView) itemView.findViewById(R.id.my_template);
        }
    }

}
