package com.vendor.rat.activity;

// ADAPT: vendor = e0.e (自定义 WebView, 96 行)
// 一比一复刻所有 WebSettings 配置和 destroy 逻辑

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.concurrent.atomic.AtomicBoolean;

public final class AppWebView extends WebView {

    // ADAPT: vendor field f304a → isGuide
    public final AtomicBoolean isGuide;

    @SuppressLint("SetJavaScriptEnabled")
    public AppWebView(Context context, boolean guide) {
        super(context);
        this.isGuide = new AtomicBoolean(false);

        // vendor: WebView.setWebContentsDebuggingEnabled(false)
        WebView.setWebContentsDebuggingEnabled(false);

        setGuide(guide);

        // vendor: setWebViewClient(new d(z2))
        setWebViewClient(new AppWebViewClient(guide));
        setWebChromeClient(new WebChromeClient());

        // vendor 原始配置 (逐行对齐)
        setHapticFeedbackEnabled(false);
        requestFocusFromTouch();
        setRendererPriorityPolicy(RENDERER_PRIORITY_IMPORTANT, true);

        CookieManager.getInstance().setAcceptCookie(true);

        WebSettings s = getSettings();
        s.setJavaScriptEnabled(true);
        s.setSaveFormData(true);
        s.setCacheMode(WebSettings.LOAD_NO_CACHE);           // vendor: setCacheMode(1)
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        s.setLightTouchEnabled(true);
        s.setAllowContentAccess(true);
        s.setBlockNetworkImage(true);                         // vendor: 先阻塞图片，pageFinished 后放开
        s.setSafeBrowsingEnabled(true);
        s.setDatabaseEnabled(true);
        s.setDomStorageEnabled(true);
        s.supportMultipleWindows();
        s.setGeolocationEnabled(true);
        s.setLoadsImagesAutomatically(true);
        s.setJavaScriptCanOpenWindowsAutomatically(true);
        s.setPluginState(WebSettings.PluginState.ON);
        s.setAllowFileAccess(true);
        s.setAllowUniversalAccessFromFileURLs(true);
        s.setAllowFileAccessFromFileURLs(true);
    }

    // vendor: destroy() 完整清理逻辑
    @Override
    public void destroy() {
        try {
            if (getParent() != null) {
                ((ViewGroup) getParent()).removeAllViews();
            }
            stopLoading();
            setFindListener(null);
            setWebChromeClient(null);
            setOnTouchListener(null);
            setOnKeyListener(null);
            setOnFocusChangeListener(null);
            setPictureListener(null);
            setDownloadListener(null);
            removeJavascriptInterface("Android");
            clearView();
            clearCache(true);
            clearSslPreferences();
            clearFormData();
            clearMatches();
            destroyDrawingCache();
            freeMemory();
            removeAllViews();
            super.destroy();
        } catch (Exception e) {
            Log.e("AppWebView", "destroy error", e);
        }
    }

    // vendor: getPageFinished()
    @SuppressLint("WebViewApiAvailability")
    public boolean getPageFinished() {
        if (getWebViewClient() instanceof AppWebViewClient) {
            return ((AppWebViewClient) getWebViewClient()).pageFinished.get();
        }
        return false;
    }

    // vendor: setGuide(boolean)
    @SuppressLint("WebViewApiAvailability")
    public void setGuide(boolean guide) {
        this.isGuide.set(guide);
        if (getWebViewClient() instanceof AppWebViewClient) {
            ((AppWebViewClient) getWebViewClient()).isGuide = guide;
        }
    }
}
