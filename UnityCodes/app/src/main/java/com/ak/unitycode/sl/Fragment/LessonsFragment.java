package com.ak.unitycode.sl.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ak.unitycode.sl.Adapters.LessonsAdapter;
import com.ak.unitycode.sl.AddActivitys.AddLessonActivity;
import com.ak.unitycode.sl.Model.LessonModel;
import com.ak.unitycode.sl.Model.UserModel;
import com.ak.unitycode.sl.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LessonsFragment extends Fragment {

    EditText serichEd;
    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private FloatingActionsMenu floatingActionsMenu;
    private FloatingActionButton floatingActionButton;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LessonsAdapter lessonsAdapter;
    private List<LessonModel> lessonModelList;

    public LessonsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons, container, false);

        //---------------------------------------------------------------------------------------------------------//
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        MobileAds.setRequestConfiguration(new
                RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ca-app-pub-9382446765591725~4023727067")).build());
        //----------------------------------------------------------------------------------------------------------//

        floatingActionsMenu = view.findViewById(R.id.fabmenuBtn);
        floatingActionButton = view.findViewById(R.id.addLessons);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        serichEd = view.findViewById(R.id.searchEt);


        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.lessonsRv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lessonModelList = new ArrayList<>();


        AdminAddButtonActivetor();
        LessonsLoder();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddLessonActivity.class));
            }
        });

        serichEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String sed = serichEd.getText().toString();
                if (!sed.isEmpty()) {
                    searchLesson(sed);
                } else {
                    LessonsLoder();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

    private void searchLesson(String searchQuery) {
        lessonModelList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Lessons");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lessonModelList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    LessonModel modelPost = ds.getValue(LessonModel.class);
                    UserModel userModel = ds.getValue(UserModel.class);

                    assert modelPost != null;
                    if (modelPost.getVideotitle().toLowerCase().contains(searchQuery.toLowerCase())) {
                        lessonModelList.add(modelPost);
                    }
                    lessonsAdapter = new LessonsAdapter(lessonModelList, getActivity());
                    recyclerView.setAdapter(lessonsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void LessonsLoder() {
        DatabaseReference lessondata = FirebaseDatabase.getInstance().getReference("Lessons");
        lessondata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    LessonModel model = ds.getValue(LessonModel.class);
                    lessonModelList.add(model);
                    lessonsAdapter = new LessonsAdapter(lessonModelList, getActivity());
                    recyclerView.setAdapter(lessonsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void AdminAddButtonActivetor() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(user.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String userType = "" + snapshot.child("UserType").getValue();
                        if (userType.equals("user")) {
                            floatingActionsMenu.setVisibility(View.GONE);
                        } else if (userType.equals("admin")) {
                            floatingActionsMenu.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}