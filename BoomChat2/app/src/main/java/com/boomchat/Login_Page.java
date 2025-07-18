package com.boomchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class Login_Page extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;
    EditText Login_Email,Login_Password;
    TextView Login_but,Forogot_Pass;
    ProgressDialog progressDialog;
    SignInButton Googel_Sign_Butto;
    private FirebaseAuth mAuth; // Insert Firebase Auth



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login__page);
        TextView reg = findViewById(R.id.btnReg);

        Login_Email = findViewById(R.id.Lemail);
        Login_Password = findViewById(R.id.Lpassword);
        Login_but = findViewById(R.id.log_btn);
        Forogot_Pass = findViewById(R.id.forgotpass);
        Googel_Sign_Butto = findViewById(R.id.googelsig);

        // Configure Googel Sing in //
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Progress Dialog
        progressDialog = new ProgressDialog(Login_Page.this);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        reg.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),Register_Page.class);
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_left);
        startActivity(i);
        finish();
    }
});
 // Forgot Password Code.....//
        Forogot_Pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        });

        // Googel SignIng But  //
        Googel_Sign_Butto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Configure Google Sign In

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


        Login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Login_Email.getText().toString();
                String password = Login_Password.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Login_Email.setError("Invalid Email");
                    Login_Email.setFocusable(true);
                }
                else {

                    loginUser(email,password);
                }
            }
        });

           // No ActionBar , No StutesBar //
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

           // Tap Target Item Add In Hier //
        new MaterialTapTargetPrompt.Builder(Login_Page.this)
                .setTarget(R.id.btnReg)
                .setPrimaryText("If You Have Boom Account")
                .setPrimaryTextColour(Color.BLACK)
                .setSecondaryText("You Not Have Account Plis Firs Register")
                .setSecondaryTextColour(Color.BLACK)
                .setSecondaryTextGravity(Gravity.VERTICAL_GRAVITY_MASK)
                .setBackgroundColour(0xC303FFC4)// Bacground Cicrcal Colour
                .setFocalColour(0xff0410FA)// Smaill Ball Colour
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                        {
                            // User has pressed the prompt target
                        }
                    }
                })
                .show();
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

        LinearLayout linearLayout = new LinearLayout(this);

        EditText Remail = new EditText(this);
        Remail.setHint("Email");
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
                beginRecovery(email);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // Show Recover Dialog //
        builder.create().show();
    }

    private void beginRecovery(String email) {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progres_dilog);
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                    Toast.makeText(Login_Page.this,"Email sent", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Login_Page.this,"Failde....", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Login_Page.this,""+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    private void loginUser(String email, String password) {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progres_dilog);
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(Login_Page.this, Dashboard_Activity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(Login_Page.this, "You Creat Account in This Mail..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
              //  Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(Login_Page.this,"Try You Coustem Email", Toast.LENGTH_LONG).show();
                // Google Sign In failed, update UI appropriately
                //Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                  if (task.getResult().getAdditionalUserInfo().isNewUser()){
                      // Get User User Uid & Email //
                      String email = user.getEmail();
                      String uid  = user.getUid();
                      // Using HashMap //
                      HashMap<Object, String> hashMap = new HashMap<>();
                      // put infom in Map  //
                      hashMap.put("email", email);
                      hashMap.put("uid", uid);
                      hashMap.put("name", "");
                      hashMap.put("phone", "");
                      hashMap.put("image", "");
                      hashMap.put("coverimg", "");
                      // FireBase Data istance //
                      FirebaseDatabase database = FirebaseDatabase.getInstance();
                      // path to store data name "User"
                      DatabaseReference reference = database.getReference("Users");
                      // put data with hash map in database //
                      reference.child(uid).setValue(hashMap);
                  }

                            Toast.makeText(Login_Page.this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
                            // To Go profile Page In  This Part //
                            startActivity(new Intent(Login_Page.this, Dashboard_Activity.class));
                            finish();
                           // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login_Page.this, "Login Failed", Toast.LENGTH_SHORT).show();
                          //  updateUI(null);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login_Page.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}