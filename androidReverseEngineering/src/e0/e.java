package e0;

import a1.q;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import java.util.concurrent.atomic.AtomicBoolean;

public final class e extends WebView {
   public final AtomicBoolean a = new AtomicBoolean(false);

   public e(Context var1, boolean var2) {
      super(var1);
      WebView.setWebContentsDebuggingEnabled(false);
      this.setGuide(var2);
      this.setWebViewClient(new d(var2));
      this.setWebChromeClient(new WebChromeClient());
      this.setHapticFeedbackEnabled(false);
      this.requestFocusFromTouch();
      this.setRendererPriorityPolicy(1, true);
      CookieManager.getInstance().setAcceptCookie(true);
      this.getSettings().setJavaScriptEnabled(true);
      this.getSettings().setSaveFormData(true);
      this.getSettings().setCacheMode(1);
      this.getSettings().setLoadWithOverviewMode(true);
      this.getSettings().setUseWideViewPort(true);
      this.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
      this.getSettings().setLightTouchEnabled(true);
      this.getSettings().setAllowContentAccess(true);
      this.getSettings().setBlockNetworkImage(true);
      this.getSettings().setSafeBrowsingEnabled(true);
      this.getSettings().setDatabaseEnabled(true);
      this.getSettings().setDomStorageEnabled(true);
      this.getSettings().supportMultipleWindows();
      this.getSettings().setGeolocationEnabled(true);
      this.getSettings().setLoadsImagesAutomatically(true);
      this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
      this.getSettings().setPluginState(PluginState.ON);
      this.getSettings().setAllowFileAccess(true);
      this.getSettings().setAllowUniversalAccessFromFileURLs(true);
      this.getSettings().setAllowFileAccessFromFileURLs(true);
   }

   public final void destroy() {
      try {
         if (this.getParent() != null) {
            ((ViewGroup)this.getParent()).removeAllViews();
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
      } catch (Exception var2) {
         q.s("e0.e", var2);
      }
   }

   @SuppressLint({"WebViewApiAvailability"})
   public boolean getPageFinished() {
      return this.getWebViewClient() instanceof d ? ((d)this.getWebViewClient()).a.get() : false;
   }

   @SuppressLint({"WebViewApiAvailability"})
   public void setGuide(boolean var1) {
      this.a.set(var1);
      if (this.getWebViewClient() instanceof d) {
         ((d)this.getWebViewClient()).b = var1;
      }
   }
}
