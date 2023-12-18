package io.github.krysiel86.utils;


import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import io.github.krysiel86.BuildConfig;

public class WebSettingsUtils {

    @SuppressLint("NewApi")
    public static void applyDefault(WebView webView) {
        final WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSaveFormData(true);
        webSettings.setUseWideViewPort(false);
        webSettings.setAllowFileAccess(true);
        webView.setVisibility(View.INVISIBLE);

        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

}