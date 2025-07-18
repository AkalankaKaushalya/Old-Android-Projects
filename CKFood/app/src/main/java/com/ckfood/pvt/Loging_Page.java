package com.ckfood.pvt;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Loging_Page extends AppCompatActivity {
TextView  Regis_But,ForgetPass_But;
ImageView Next_but;
TextInputEditText LogEmail,LogPassword;
EditText Remail;
    //Fire base
    FirebaseAuth Fireauth;

    String path="android.resource://com.ckfood.pvt/"+R.drawable.chekbx_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loging_page);
       Regis_But = findViewById(R.id.registation_but);
       Next_but = findViewById(R.id.log_but);
       LogEmail = findViewById(R.id.logemail);
       LogPassword = findViewById(R.id.logpasswored);
       ForgetPass_But = findViewById(R.id.forgottenpass);
       Remail = findViewById(R.id.remail);

        Fireauth = FirebaseAuth.getInstance();


        Next_but.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String logemail = LogEmail.getText().toString();
              String logpass = LogPassword.getText().toString();
              if(!Patterns.EMAIL_ADDRESS.matcher(logemail).matches()){
                  LogEmail.setError("Invalid Email");  // ඊමෙල් ෆිල්ඩ් එක සම්පුර්ණ කරදැයි බැලීම.
                  return;
              }
              if (TextUtils.isEmpty(logpass)){
                  LogPassword.setError("Password is Required"); // මුරපයක් ලබා දී ඇත් දැයී පරීක්ෂා කර බැලීම.
                  return;                                       //මේ අකාරයට ඔනැම සම්පුර්ණ කල යුතු තීරූ පිලිබද පෙන්වා දීමට මෙය යොදාගත හැක
              }
              if (logpass.length()<6){
                  LogPassword.setError("Password Must Be 6 Characters"); // යම් මුරපදයක් අකුරූ 6 අනිවර්‍යෙන් ඇතුලත් විය යුතුයි යන නිවෙදනය පෙන්වයී
                         // මෙයද ඔනැම අකාරයකට අනිවර්‍ය අකුරු ප්‍රමානයක් තිබිය යුතු ස්තානවලට දැමිය හැක
              }
              else {
                  Fireauth.signInWithEmailAndPassword(logemail, logpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()) {
                              // Sign in success, update UI with the signed-in user's information
                              FirebaseUser user = Fireauth.getCurrentUser();
                              Toast.makeText(Loging_Page.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(Loging_Page.this, MainActivity.class));
                              finish();
                          } else {
                              // If sign in fails, display a message to the user.
                              Toast.makeText(Loging_Page.this, "This Email Already Have Account", Toast.LENGTH_SHORT).show();
                          }
                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {

                      }
                  });
              }
          }
      });
        Regis_But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Loging_Page.this, Register_Page.class);
                startActivity(i);
            }
        });
        ForgetPass_But.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              showRecoverPasswordDialog();
          }
      });


        if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT < 30)
        {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 21)
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

    private void showRecoverPasswordDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

        LinearLayout linearLayout = new LinearLayout(this);

        EditText Remail = new EditText(this);
        Remail.setHint("Email");
        Remail.setBackground(Drawable.createFromPath(path));
        Remail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        // set Ten minits run //
        Remail.setMinEms(10);

        linearLayout.addView(Remail);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = Remail.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Remail.setError("Invalid Email");  // ඊමෙල් ෆිල්ඩ් එක සම්පුර්ණ කරදැයි බැලීම.
                    return;
                }else {
                    beginRecovery(email);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void beginRecovery(String email) {
        Fireauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Loging_Page.this,"Email sent", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Loging_Page.this,"Failde....", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Loging_Page.this,""+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


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