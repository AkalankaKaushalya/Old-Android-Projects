package com.skypper.k_mistry.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.skypper.k_mistry.Admin_PanelActivity;
import com.skypper.k_mistry.All_LessonActivity;
import com.skypper.k_mistry.LoginActivity;
import com.skypper.k_mistry.R;

public class AboutActivity extends AppCompatActivity {

    ImageView admin_panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        admin_panel = findViewById(R.id.btnMark);
        admin_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AboutActivity.this, Admin_PanelActivity.class));
            }
        });
    }
}