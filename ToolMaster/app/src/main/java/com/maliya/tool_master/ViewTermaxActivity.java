package com.maliya.tool_master;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maliya.tool_master.databinding.ActivityViewTermaxBinding;

import java.util.Calendar;
import java.util.Locale;

public class ViewTermaxActivity extends AppCompatActivity {
    ActivityViewTermaxBinding binding;
    String ToolID;
    FirebaseAuth firebaseAuth;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewTermaxBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= 21) {
            window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.stutas_bar));
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.stutas_bar));
        }

        firebaseAuth = FirebaseAuth.getInstance();


        Intent intent = getIntent();
        ToolID = intent.getStringExtra("toolid");

        loadetool();


        binding.profileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewTermaxActivity.this, ProfileActivity.class));
            }
        });

        binding.MoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ViewTermaxActivity.this, binding.MoreBtn);
                popupMenu.getMenu().add(Menu.NONE, 0, 0, "About Us");
                popupMenu.getMenu().add(Menu.NONE, 1, 1, "Logout");
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int which = item.getItemId();
                        if (which == 0) {
                            startActivity(new Intent(ViewTermaxActivity.this, AboutUsActivity.class));
                        } else if (which == 1) {
                            firebaseAuth.signOut();
                            startActivity(new Intent(ViewTermaxActivity.this, SplashActivity.class));
                            finish();
                        }
                        return false;
                    }
                });
            }
        });

        binding.copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) ViewTermaxActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("rewod", binding.commendTv.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(ViewTermaxActivity.this, "Copy..", Toast.LENGTH_SHORT).show();
            }
        });

        binding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Tool-Master" + binding.commendTv.getText().toString());
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });
    }

    private void loadetool() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Kali");
        ref.child(ToolID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String Toolname = "" + snapshot.child("title").getValue();
                        String Commend = "" + snapshot.child("commend").getValue();
                        String Description = "" + snapshot.child("description").getValue();
                        String time = "" + snapshot.child("timestamp").getValue();


                        binding.toolnameTv.setText(Toolname);
                        binding.commendTv.setText(Commend);
                        binding.decriptionTv.setText(Description);

                        long tm = Long.parseLong(time);

                        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
                        calendar.setTimeInMillis(tm);

                        String data = DateFormat.format("dd-MM-yyyy", calendar).toString();
                        binding.updateTv.setText(data);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}