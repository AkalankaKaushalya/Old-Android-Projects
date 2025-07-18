package com.myschool.Dahsbords;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.myschool.AddSubjectsActivity;
import com.myschool.R;
import com.myschool.SpalshActivity;
import com.myschool.Tabs.GroupChatFragment;
import com.myschool.Tabs.MyNotesFragment;
import com.myschool.Tabs.StudentFragment;
import com.myschool.Tabs.TeacherNoteFragment;
import com.myschool.databinding.ActivityTeacherDashbordBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Teacher_DashbordActivity extends AppCompatActivity {
    ActivityTeacherDashbordBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherDashbordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        checkUser();


        binding.bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.groupchat));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.notes));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.reading));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.students));
        binding.bottomNavigation.show(2, true);
        binding.bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        replace(new GroupChatFragment());
                        binding.titleTv.setText("Group Chat");
                        binding.profileBtn.setVisibility(View.GONE);
                        break;

                    case 2:
                        replace(new TeacherNoteFragment());
                        binding.titleTv.setText("Techer Notes");
                        binding.profileBtn.setVisibility(View.VISIBLE);
                        break;

                    case 3:
                        replace(new MyNotesFragment());
                        binding.titleTv.setText("Your Notes");
                        binding.profileBtn.setVisibility(View.VISIBLE);
                        break;

                    case 4:
                        replace(new StudentFragment());
                        binding.titleTv.setText("All Student");
                        binding.profileBtn.setVisibility(View.VISIBLE);
                        break;
                }
                return null;
            }
        });

        binding.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Teacher_DashbordActivity.this, binding.moreBtn);
                popupMenu.getMenu().add(Menu.NONE, 0, 0, "Add Lessons");
                popupMenu.getMenu().add(Menu.NONE, 1, 1, "Logout");
                popupMenu.getMenu().add(Menu.NONE, 2, 2, "About Us");
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int which = item.getItemId();
                        if (which == 0) {
                            startActivity(new Intent(Teacher_DashbordActivity.this, AddSubjectsActivity.class));
                        } else if (which == 1) {
                            firebaseAuth.signOut();
                            startActivity(new Intent(Teacher_DashbordActivity.this, SpalshActivity.class));
                            finish();
                        }
                        return false;
                    }
                });
            }
        });

    }


    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            //binding.subTitleTv.setText("Not Logged In");
            startActivity(new Intent(Teacher_DashbordActivity.this, SpalshActivity.class));
            finish();


        } else {
            String email = firebaseUser.getEmail();
            // binding.subTitleTv.setText(email);
        }
    }

    protected void onStart() {
        super.onStart();
        replace(new TeacherNoteFragment());
        binding.titleTv.setText("Techer Notes");
        binding.profileBtn.setVisibility(View.VISIBLE);

    }
}