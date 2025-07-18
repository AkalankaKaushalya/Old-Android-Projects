package com.maliya.tool_master_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maliya.tool_master_admin.Notifi.FcmNotificationsSender;
import com.maliya.tool_master_admin.databinding.ActivityAddKalitoolBinding;
import com.maliya.tool_master_admin.databinding.ActivityAddTermaxToolBinding;

import java.util.HashMap;

public class AddKalitoolActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
ActivityAddKalitoolBinding binding;
    String toolname ="", description ="", command="", rtyoe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddKalitoolBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Spinner spin = (Spinner) findViewById(R.id.ttype);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);


        binding.uplodebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolname = binding.toolnameEt.getText().toString();
                description = binding.tooldescripEt.getText().toString();
                command = binding.toolcommnedEt.getText().toString();

                if (TextUtils.isEmpty(toolname)) {
                    Toast.makeText(AddKalitoolActivity.this, "Enter Title...", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(description)) {
                    Toast.makeText(AddKalitoolActivity.this, "Enter Description...", Toast.LENGTH_SHORT).show();
                } else if (rtyoe.equals("Select Type")){
                    Toast.makeText(AddKalitoolActivity.this, "Select Root Type...", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImageToStorage();
                }
            }
        });
    }

    private void uploadImageToStorage() {
        long timestampe = System.currentTimeMillis();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", "");
        hashMap.put("toolid", "" + timestampe);
        hashMap.put("title", "" + toolname);
        hashMap.put("description", "" + description);
        hashMap.put("commend", "" + command);
        hashMap.put("tooltype","" + rtyoe);
        hashMap.put("url", "" + "");
        hashMap.put("timestamp", timestampe);
        hashMap.put("viewCount", 0);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Kali");
        ref.child("" + timestampe)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //progressDialog.dismiss();
                        FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",
                                binding.toolnameEt.getText().toString(),
                                binding.tooldescripEt.getText().toString(), getApplicationContext(), AddKalitoolActivity.this);
                        notificationsSender.SendNotifications();
                        Toast.makeText(AddKalitoolActivity.this, "Successfully Uploaded...", Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //progressDialog.dismiss();
                        Toast.makeText(AddKalitoolActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        rtyoe = text;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}