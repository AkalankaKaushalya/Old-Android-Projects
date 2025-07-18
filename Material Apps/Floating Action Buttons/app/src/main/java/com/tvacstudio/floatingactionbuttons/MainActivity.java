package com.tvacstudio.floatingactionbuttons;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mMainAddFab, mAddUserFab, mAddContactFab;
    private TextView mAddUserText, mAddContactText;

    private Animation mFabOpenAnim, mFabCloseAnim;

    private boolean isOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainAddFab = findViewById(R.id.main_add_fab);
        mAddUserFab = findViewById(R.id.add_user_fab);
        mAddContactFab = findViewById(R.id.add_contact_fab);

        mAddUserText = findViewById(R.id.add_user_text);
        mAddContactText = findViewById(R.id.add_contact_text);

        mFabOpenAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fab_open);
        mFabCloseAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fab_close);

        isOpen = false;

        mMainAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen){

                    mAddUserFab.setAnimation(mFabCloseAnim);
                    mAddContactFab.setAnimation(mFabCloseAnim);

                    mAddUserText.setVisibility(View.INVISIBLE);
                    mAddContactText.setVisibility(View.INVISIBLE);

                    isOpen = false;
                } else {

                    mAddUserFab.setAnimation(mFabOpenAnim);
                    mAddContactFab.setAnimation(mFabOpenAnim);

                    mAddUserText.setVisibility(View.VISIBLE);
                    mAddContactText.setVisibility(View.VISIBLE);

                    isOpen = true;
                }

            }
        });


    }
}
