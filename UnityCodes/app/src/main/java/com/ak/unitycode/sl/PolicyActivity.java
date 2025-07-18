package com.ak.unitycode.sl;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class PolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        WebView mywebview = (WebView) findViewById(R.id.privacyWab);
        mywebview.loadUrl("file:///android_asset/unity_code_policy.html");
    }
}