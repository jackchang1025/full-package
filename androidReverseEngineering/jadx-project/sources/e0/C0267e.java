package e0;

import a1.AbstractC0026q;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: e0.e */
/* loaded from: classes.dex */
public final class C0267e extends WebView {

    /* renamed from: a */
    public final AtomicBoolean f442a;

    public C0267e(Context context, boolean z2) {
        super(context);
        this.f442a = new AtomicBoolean(false);
        WebView.setWebContentsDebuggingEnabled(false);
        setGuide(z2);
        setWebViewClient(new C0266d(z2));
        setWebChromeClient(new WebChromeClient());
        setHapticFeedbackEnabled(false);
        requestFocusFromTouch();
        setRendererPriorityPolicy(1, true);
        CookieManager.getInstance().setAcceptCookie(true);
        getSettings().setJavaScriptEnabled(true);
        getSettings().setSaveFormData(true);
        getSettings().setCacheMode(1);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setUseWideViewPort(true);
        getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        getSettings().setLightTouchEnabled(true);
        getSettings().setAllowContentAccess(true);
        getSettings().setBlockNetworkImage(true);
        getSettings().setSafeBrowsingEnabled(true);
        getSettings().setDatabaseEnabled(true);
        getSettings().setDomStorageEnabled(true);
        getSettings().supportMultipleWindows();
        getSettings().setGeolocationEnabled(true);
        getSettings().setLoadsImagesAutomatically(true);
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setPluginState(WebSettings.PluginState.ON);
        getSettings().setAllowFileAccess(true);
        getSettings().setAllowUniversalAccessFromFileURLs(true);
        getSettings().setAllowFileAccessFromFileURLs(true);
    }

    @Override // android.webkit.WebView
    public final void destroy() {
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
        } catch (Exception e2) {
            AbstractC0026q.m186s("e0.e", e2);
        }
    }

    @SuppressLint({"WebViewApiAvailability"})
    public boolean getPageFinished() {
        if (getWebViewClient() instanceof C0266d) {
            return ((C0266d) getWebViewClient()).f440a.get();
        }
        return false;
    }

    @SuppressLint({"WebViewApiAvailability"})
    public void setGuide(boolean z2) {
        this.f442a.set(z2);
        if (getWebViewClient() instanceof C0266d) {
            ((C0266d) getWebViewClient()).f441b = z2;
        }
    }
}
