package com.studentworknotes.vta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.studentworknotes.vta.databinding.ActivityWorkVeiwBinding;

public class WorkVeiwActivity extends AppCompatActivity {
    ActivityWorkVeiwBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkVeiwBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent i = getIntent();
        String sub = i.getStringExtra("subject");
        String msg = i.getStringExtra("message");
        //String user = i.getStringExtra("user");
        binding.SubjectShow.setText(sub);
        binding.MessageShow.setText(msg);
    }
}