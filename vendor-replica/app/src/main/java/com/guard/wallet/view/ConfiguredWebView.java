/**
 * 自定义 WebView，预配置 WebSettings
 * - 禁用 WebContentsDebugging
 * - 完整 WebSettings 配置（JS/Cookie/DOM/文件访问等）
 * - 引导模式标志 + 页面加载完成状态
 * - 完整 destroy 清理流程
 *
 * vendor 原始路径: e0/e.java
 */
package com.guard.wallet.view;

import com.guard.wallet.core.AppUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ConfiguredWebView extends WebView {
    public final AtomicBoolean guideFlag = new AtomicBoolean(false);

    @SuppressWarnings("deprecation")
    public ConfiguredWebView(Context context, boolean isGuide) {
        super(context);
        WebView.setWebContentsDebuggingEnabled(false);
        this.setGuide(isGuide);
        this.setWebViewClient(new InterceptWebViewClient(isGuide));
        this.setWebChromeClient(new WebChromeClient());
        this.setHapticFeedbackEnabled(false);
        this.requestFocusFromTouch();
        this.setRendererPriorityPolicy(1, true);
        CookieManager.getInstance().setAcceptCookie(true);
        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLightTouchEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setBlockNetworkImage(true);
        settings.setSafeBrowsingEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.supportMultipleWindows();
        settings.setGeolocationEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setAllowFileAccess(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccessFromFileURLs(true);
    }

    public final void destroy() {
        try {
            if (this.getParent() != null) {
                ((ViewGroup) this.getParent()).removeAllViews();
            }
            this.stopLoading();
            this.setFindListener(null);
            this.setWebChromeClient(null);
            this.setOnTouchListener(null);
            this.setOnKeyListener(null);
            this.setOnFocusChangeListener(null);
            this.setPictureListener(null);
            this.setDownloadListener(null);
            this.removeJavascriptInterface("Android");
            this.clearView();
            this.clearCache(true);
            this.clearSslPreferences();
            this.clearFormData();
            this.clearMatches();
            this.destroyDrawingCache();
            this.freeMemory();
            this.removeAllViews();
            super.destroy();
        } catch (Exception ex) {
            AppUtils.s("e0.e", ex);
        }
    }

    @SuppressLint({"WebViewApiAvailability"})
    public boolean getPageFinished() {
        return this.getWebViewClient() instanceof InterceptWebViewClient ? ((InterceptWebViewClient) this.getWebViewClient()).pageLoaded.get() : false;
    }

    @SuppressLint({"WebViewApiAvailability"})
    public void setGuide(boolean isGuide) {
        this.guideFlag.set(isGuide);
        if (this.getWebViewClient() instanceof InterceptWebViewClient) {
            ((InterceptWebViewClient) this.getWebViewClient()).guide = isGuide;
        }
    }
}
