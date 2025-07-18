package com.ak.unitycode.sl.Login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.ak.unitycode.sl.AdminActivity;
import com.ak.unitycode.sl.DataActivity;
import com.ak.unitycode.sl.R;
import com.ak.unitycode.sl.databinding.DialogForgetPasswordBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class LoginRegisterActivity extends AppCompatActivity {
    private TextView loginname, forgotPassTv, singupTv, singinTv;
    private EditText emailEt, passwordEt, confpasswordEt;
    private Button loginBtn, registerBtn;
    private CardView confpasswordCv;
    private FirebaseAuth firebaseAuth;
    private String email = "", password = "", confopassword = "";

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        if (Build.VERSION.SDK_INT < 32) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        if (Build.VERSION.SDK_INT >= 32) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        firebaseAuth = FirebaseAuth.getInstance();

        loginname = findViewById(R.id.loginname);
        forgotPassTv = findViewById(R.id.forgotPassTv);
        singupTv = findViewById(R.id.singupTv);
        singinTv = findViewById(R.id.singinTv);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        confpasswordEt = findViewById(R.id.confpasswordEt);
        confpasswordCv = findViewById(R.id.confpasswordCv);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);


        singupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginname.setText("Create New Account");
                singupTv.setVisibility(View.GONE);
                singinTv.setVisibility(View.VISIBLE);
                confpasswordCv.setVisibility(View.VISIBLE);
                forgotPassTv.setVisibility(View.GONE);
                registerBtn.setVisibility(View.VISIBLE);
                loginBtn.setVisibility(View.GONE);

            }
        });

        singinTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginname.setText("Login");
                singupTv.setVisibility(View.VISIBLE);
                singinTv.setVisibility(View.GONE);
                confpasswordCv.setVisibility(View.GONE);
                forgotPassTv.setVisibility(View.VISIBLE);
                registerBtn.setVisibility(View.GONE);
                loginBtn.setVisibility(View.VISIBLE);

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterWork();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginWork();
            }
        });

        forgotPassTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frogotPass();
            }
        });


    }

    private void frogotPass() {
        DialogForgetPasswordBinding dialogForgetPasswordBinding = DialogForgetPasswordBinding.inflate(LayoutInflater.from(LoginRegisterActivity.this));

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(LoginRegisterActivity.this, R.style.CustomDialog);
        builder.setView(dialogForgetPasswordBinding.getRoot());

        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dialogForgetPasswordBinding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        dialogForgetPasswordBinding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        dialogForgetPasswordBinding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recoverMail = Objects.requireNonNull(dialogForgetPasswordBinding.recoverMailEt.getText()).toString();


                if (recoverMail.isEmpty()) {
                    Toast.makeText(LoginRegisterActivity.this, "Empty Email", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(recoverMail)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(LoginRegisterActivity.this, "Email sent", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginRegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }

    private void LoginWork() {
        email = emailEt.getText().toString();
        password = passwordEt.getText().toString().trim();
        confopassword = confpasswordEt.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(this, "Email is Empty", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email Not Valided", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginRegisterActivity.this, "Welcome Back \n" + email, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginRegisterActivity.this, AdminActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginRegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void RegisterWork() {
        email = emailEt.getText().toString();
        password = passwordEt.getText().toString().trim();
        confopassword = confpasswordEt.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(this, "Email is Empty", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(confopassword)) {
            Toast.makeText(this, "Password not Confirm", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email Not Valided", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            long timestamp = System.currentTimeMillis();

                            assert user != null;
                            String email = user.getEmail();
                            String uid = user.getUid();

                            HashMap<Object, String> hashMap = new HashMap<>();
                            hashMap.put("Email", email);
                            hashMap.put("UserID", uid);
                            hashMap.put("Password", password);
                            hashMap.put("Name", "");
                            hashMap.put("Phone", "");
                            hashMap.put("Age", "");
                            hashMap.put("Paypal", "");
                            hashMap.put("UserType", "user");
                            hashMap.put("Image", "");
                            hashMap.put("Coverimg", "");
                            hashMap.put("Earnmony", "" + 0.00);
                            hashMap.put("timesTamp", String.valueOf(timestamp));
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                            reference.child(uid)
                                    .setValue(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(LoginRegisterActivity.this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginRegisterActivity.this, DataActivity.class));
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(LoginRegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //progressDialog.dismiss();
                            Toast.makeText(LoginRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}