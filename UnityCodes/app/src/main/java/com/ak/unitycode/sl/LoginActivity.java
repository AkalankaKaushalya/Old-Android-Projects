package com.ak.unitycode.sl;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
//    static final int RC_SIGN_IN = 100;
//    private VideoView BGvideo;
////    private final String path = "android.resource://com.ak.unitycode.sl/" + R.raw.login_bg;
//    GoogleSignInClient mGoogleSignInClient;
//    SignInButton Googel_Sign_Butto;
//    FirebaseAuth firebaseAuth;

//    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
//
//        Window win = activity.getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//
//        BGvideo = findViewById(R.id.vview);
//        Googel_Sign_Butto = findViewById(R.id.googelsig);
//
//
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        if (Build.VERSION.SDK_INT < 32) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        }
//        if (Build.VERSION.SDK_INT >= 32) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//
//        Uri uri = Uri.parse(path);
//        BGvideo.setVideoURI(uri);
//        BGvideo.start();
//        BGvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.setLooping(true);
//                mp.setVolume(0, 0);
//            }
//        });

//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        // Googel SignIng But  //
//        Googel_Sign_Butto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Configure Google Sign In
////                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
////                startActivityForResult(signInIntent, RC_SIGN_IN);
//            }
//        });
    }
//    protected void onResume() {
//        BGvideo.resume();
//        super.onResume();
//    }
//
//    protected void onPause() {
//        BGvideo.suspend();
//        super.onPause();
//    }
//
//    protected void onDestroy() {
//        BGvideo.stopPlayback();
//        super.onDestroy();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                Toast.makeText(LoginActivity.this, ""+e, Toast.LENGTH_LONG).show();
//                // Google Sign In failed, update UI appropriately
//            }
//        }
//    }
//
//    private void firebaseAuthWithGoogle(String idToken) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            long timestamp = System.currentTimeMillis();
//                            if (task.getResult().getAdditionalUserInfo().isNewUser()) {
//                                // Get User User Uid & Email //
//                                String email = user.getEmail();
//                                String uid = user.getUid();
//
//                                // Using HashMap //
//                                HashMap<Object, String> hashMap = new HashMap<>();
//                                // put infom in Map  //
//                                hashMap.put("Email", email);
//                                hashMap.put("UserID", uid);
//                                hashMap.put("Name", "");
//                                hashMap.put("Phone", "");
//                                hashMap.put("Age", "");
//                                hashMap.put("Paypal", "");
//                                hashMap.put("UserType", "user");
//                                hashMap.put("Image", "");
//                                hashMap.put("Coverimg", "");
//                                hashMap.put("Earnmony", "" + 00.0);
//                                hashMap.put("timesTamp", String.valueOf(timestamp));
//                                // FireBase Data istance //
//                                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                // path to store data name "User"
//                                DatabaseReference reference = database.getReference("Users");
//                                // put data with hash map in database //
//                                reference.child(uid).setValue(hashMap);
//                            }
//                            Toast.makeText(LoginActivity.this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
//                            // To Go profile Page In  This Part //
//                            startActivity(new Intent(LoginActivity.this, DataActivity.class));
//                            finish();
//                            // updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                            //  updateUI(null);
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}