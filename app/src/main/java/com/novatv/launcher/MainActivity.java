package com.novatv.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.JavascriptInterface;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        WebView webView = new WebView(this);
        webView.setBackgroundColor(0xFF07111F);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);

        webView.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void openApp(String name) {
                runOnUiThread(() -> {
                    String packageName = "";

                    switch (name) {
    case "YouTube":
        packageName = "com.google.android.youtube.tv";
        break;

    case "Netflix":
        packageName = "com.netflix.ninja";
        break;

    case "Music":
        packageName = "com.astro.sooka";
        break;

    case "Browser":
        packageName = "com.iqiyi.i18n.tv";
        break;

    case "Settings":
        startActivity(new Intent(
            android.provider.Settings.ACTION_SETTINGS
        ));
        return;
}

                    if (!packageName.isEmpty()) {
                        Intent intent = getPackageManager()
                            .getLaunchIntentForPackage(packageName);

                        if (intent != null) {
                            startActivity(intent);
                        }
                    }
                });
            }
        }, "Android");

        setContentView(webView);
        webView.loadUrl("file:///android_asset/index.html");
    }
}
