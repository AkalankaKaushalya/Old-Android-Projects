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

import com.ak.unitycode.sl.Adapters.CodeAdapter;
import com.ak.unitycode.sl.AddActivitys.AddCodeActivity;
import com.ak.unitycode.sl.Model.CodesModel;
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


public class CodesFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private RecyclerView recyclerView;
    private FloatingActionsMenu floatingActionsMenu;
    private FloatingActionButton floatingActionButton;
    private EditText serich;

    private List<CodesModel> codesModelList;
    private CodeAdapter codeAdapter;


    public CodesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_codes, container, false);

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
        floatingActionButton = view.findViewById(R.id.addCode);
        serich = view.findViewById(R.id.searchEt);


        recyclerView = view.findViewById(R.id.codeRv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        codesModelList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();

        AdminAddButtonActivetor();
        CodesLoder();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddCodeActivity.class));
            }
        });

        serich.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String sed = serich.getText().toString();
                if (!sed.isEmpty()) {
                    searchCode(sed);
                } else {
                    CodesLoder();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

    private void searchCode(String searchQuery) {
        codesModelList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Codes");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                codesModelList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CodesModel codesModel = ds.getValue(CodesModel.class);

                    assert codesModel != null;
                    if (codesModel.getCodeName().toLowerCase().contains(searchQuery.toLowerCase())) {
                        codesModelList.add(codesModel);
                    }
                    codeAdapter = new CodeAdapter(codesModelList, getActivity());
                    recyclerView.setAdapter(codeAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CodesLoder() {
        DatabaseReference codedata = FirebaseDatabase.getInstance().getReference("Codes");
        codedata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    CodesModel codemodel = ds.getValue(CodesModel.class);
                    codesModelList.add(codemodel);
                    codeAdapter = new CodeAdapter(codesModelList, getActivity());
                    recyclerView.setAdapter(codeAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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