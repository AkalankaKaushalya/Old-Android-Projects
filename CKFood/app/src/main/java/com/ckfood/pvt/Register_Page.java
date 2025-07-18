package com.ckfood.pvt;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Register_Page extends AppCompatActivity {
    ImageView DownArrow,Submit;
    LinearLayout Chl1,Chl2;
    TextInputEditText P_name,P_day,P_no,P_about,P_addres,P_email,P_password;
    CheckBox Vag,Met,Brad,Drink,Ifood;

    //Fire base
    FirebaseAuth Fireauth;
    FirebaseDatabase FireDataBase;
    DatabaseReference FireBaseRef;

    String UID;
    String not="Not selected";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        DownArrow = findViewById(R.id.dounarrow);
        Chl1 = findViewById(R.id.chl1);
        Chl2 = findViewById(R.id.chl2);
        P_name  =findViewById(R.id.username);
        P_day   =findViewById(R.id.userbday);
        P_no    =findViewById(R.id.usermobail);
        P_about =findViewById(R.id.userabout);
        P_email =findViewById(R.id.useremail);
        P_password = findViewById(R.id.userpassword);
        P_addres =findViewById(R.id.useraddreas);
        Vag     =findViewById(R.id.chekveg);
        Met     =findViewById(R.id.chekmeet);
        Brad    =findViewById(R.id.chekbread);
        Drink   =findViewById(R.id.chekdrink);
        Ifood   =findViewById(R.id.chekinstantfood);
        Submit  =findViewById(R.id.submit);

        Fireauth = FirebaseAuth.getInstance();
        FireDataBase = FirebaseDatabase.getInstance();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname   = P_name.getText().toString().trim();
                String ubday   = P_day.getText().toString().trim();
                String umno    = P_no.getText().toString().trim();
                String uabout  = P_about.getText().toString().trim();
                String uadd    = P_addres.getText().toString().trim();
                String umail   =P_email.getText().toString().trim();
                String upass   =P_password.getText().toString().trim();
                String vag     = Vag.getText().toString().trim();
                String met     = Met.getText().toString().trim();
                String brad    = Brad.getText().toString().trim();
                String drink   = Drink.getText().toString().trim();
                String ifood   = Ifood.getText().toString().trim();
                if (TextUtils.isEmpty(P_name.getText().toString())){
                    P_name.setError("Please Add UserName");
                    return;
                }if (TextUtils.isEmpty(P_day.getText().toString())){
                    P_day.setError("Please Add BirthDay");
                    return;
                }if (P_no.toString().matches("[0-9] {10}")) {
                    P_no.setError("Only Numbrs");
                    return;
                }if (P_no.getText().toString().length()<10) {
                    P_no.setError("Wrong number");
                    return;
                }if (TextUtils.isEmpty(P_about.getText().toString())){
                    P_about.setError("Please Add About");
                }if (TextUtils.isEmpty(P_addres.getText().toString())){
                    P_addres.setError("Please Add About");
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(umail).matches()){
                    P_email.setError("Invalid Email");  // ඊමෙල් ෆිල්ඩ් එක සම්පුර්ණ කරදැයි බැලීම.
                    return;
                }
                if (TextUtils.isEmpty(upass)){
                    P_password.setError("Password is Required"); // මුරපයක් ලබා දී ඇත් දැයී පරීක්ෂා කර බැලීම.
                    return;                                       //මේ අකාරයට ඔනැම සම්පුර්ණ කල යුතු තීරූ පිලිබද පෙන්වා දීමට මෙය යොදාගත හැක
                }
                if (upass.length()<6){
                    P_password.setError("Password Must Be 6 Characters"); // යම් මුරපදයක් අකුරූ 6 අනිවර්‍යෙන් ඇතුලත් විය යුතුයි යන නිවෙදනය පෙන්වයී
                    return;                                         // මෙයද ඔනැම අකාරයකට අනිවර්‍ය අකුරු ප්‍රමානයක් තිබිය යුතු ස්තානවලට දැමිය හැක
                }
                else {
                   Fireauth.createUserWithEmailAndPassword(umail,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                UID = Fireauth.getCurrentUser().getUid();
                                FireBaseRef = FirebaseDatabase.getInstance().getReference("Users").child(UID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("User Name",P_name.getText().toString());
                                user.put("User Email",P_email.getText().toString());
                                user.put("BirthDay",P_day.getText().toString());
                                user.put("Mobail Number",P_no.getText().toString());
                                user.put("About",P_about.getText().toString());
                                user.put("Address",P_addres.getText().toString());
                                user.put("Image", "");
                                user.put("About Me","");
                                if (!Vag.isChecked()){
                                    user.put("Vegitarian",not);
                                }else if (Vag.isChecked()){
                                    user.put("Vegitarian",Vag.getText().toString());
                                }
                                if (!Met.isChecked()){
                                    user.put("Meet Food",not);
                                }else if (Met.isChecked()){
                                    user.put("Meet Food",Met.getText().toString());
                                }
                                if (!Brad.isChecked()){
                                    user.put("Brad",not);
                                }else if (Brad.isChecked()){
                                    user.put("Brad",Brad.getText().toString());
                                }
                                if (!Drink.isChecked()){
                                    user.put("Drinks",not);
                                }else if (Drink.isChecked()){
                                    user.put("Drinks",Drink.getText().toString());
                                }
                                if (!Ifood.isChecked()){
                                    user.put("IFood",not);
                                }else if (Ifood.isChecked()){
                                    user.put("IFood",Ifood.getText().toString());
                                }
                                FireBaseRef.setValue(user);
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                Toast.makeText(Register_Page.this, "Welcome  "+umail, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                Toast.makeText(Register_Page.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                       }
                   });
                }
            }
        });


        //Date Set Date Type....
        P_day.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            // When user changes text of the EditText

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    P_day.setText(current);
                    P_day.setSelection(sel < current.length() ? sel : current.length());
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });


        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 30)
        {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 30)
        {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

//--------------------------------------------------------------------------------------------------------------------------------------------//
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}