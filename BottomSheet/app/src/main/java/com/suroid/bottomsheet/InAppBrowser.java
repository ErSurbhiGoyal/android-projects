package com.suroid.bottomsheet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class InAppBrowser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_browser);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("https://github.com/ErSurbhiGoyal");
    }

}
