package com.tvacstudio.bottomsheet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mCustomBottomSheet;
    private BottomSheetBehavior mBottomSheetBehavior;

    private LinearLayout mHeaderLayout;
    private ImageView mHeaderImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomBottomSheet = findViewById(R.id.custom_bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mCustomBottomSheet);

        mHeaderLayout = findViewById(R.id.header_layout);
        mHeaderImage = findViewById(R.id.header_arrow);

        mHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        });

        mBottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                mHeaderImage.setRotation(slideOffset * 180);

            }
        });


    }
}
