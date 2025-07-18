package com.studentworknotes.vta;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.studentworknotes.vta.databinding.ActivityAboutBinding;

public class AboutActivity extends AppCompatActivity {
    ActivityAboutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.TranslatEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.adoutEN.setVisibility(View.VISIBLE);
                binding.aboutSN.setVisibility(View.GONE);
            }
        });

        binding.TranslatSinhala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.aboutSN.setVisibility(View.VISIBLE);
                binding.adoutEN.setVisibility(View.GONE);
            }
        });
    }
}