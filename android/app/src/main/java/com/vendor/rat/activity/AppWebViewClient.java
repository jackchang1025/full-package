package com.vendor.rat.activity;

// ADAPT: vendor = e0.d (WebViewClient, 91 行)
// 一比一复刻: pageFinished 追踪、超时降级到 baidu、URL 拦截、返回键处理

import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.concurrent.atomic.AtomicBoolean;

public final class AppWebViewClient extends WebViewClient {

    private static final String TAG = "AppWebViewClient";

    // ADAPT: vendor field f303a → pageFinished
    public final AtomicBoolean pageFinished = new AtomicBoolean(false);
    // ADAPT: vendor field b → isGuide
    public boolean isGuide;

    public AppWebViewClient(boolean guide) {
        this.isGuide = guide;
    }

    // vendor: onPageFinished — 标记完成 + 放开图片加载
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (url == null || url.isEmpty()) {
            return;
        }
        Log.d(TAG, "onPageFinished URL:" + url);
        this.pageFinished.set(true);
        view.getSettings().setBlockNetworkImage(false);
    }

    // vendor: onPageStarted
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (url == null || url.isEmpty()) {
            return;
        }
        Log.d(TAG, "onPageStarted URL:" + url);
    }

    // vendor: onReceivedError — 超时降级到 baidu
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (error.getDescription() == null || error.getDescription().toString().isEmpty()) {
            return;
        }
        Log.d(TAG, "onReceivedError error:" + error.getDescription().toString());
        if (error.getDescription().toString().contains("ERR_CONNECTION_TIMED_OUT") && !this.pageFinished.get()) {
            view.loadUrl("https://m.baidu.com/");
        }
    }

    // vendor: onRenderProcessGone — 返回 true 防止 crash
    @Override
    public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
        return true;
    }

    // vendor: shouldOverrideKeyEvent — 引导模式下拦截返回键
    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (this.isGuide) {
                return false;
            }
            if (view != null && view.canGoBack()) {
                view.goBack();
                return true;
            }
        }
        return super.shouldOverrideKeyEvent(view, event);
    }

    // vendor: shouldOverrideUrlLoading
    // TODO: VENDOR_VERIFY — vendor 的 shouldOverrideUrlLoading 有 520 条指令，反编译不完整
    // 当前实现: 默认行为，后续需要根据真机测试补充 URL 拦截逻辑
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }
}
