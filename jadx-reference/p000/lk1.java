package p000;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class lk1 extends WebViewClient {
    @Override // android.webkit.WebViewClient
    public final void onPageFinished(WebView webView, String str) {
        t60.m214695b6(webView, "view");
        t60.m214695b6(str, "url");
        super.onPageFinished(webView, str);
    }

    @Override // android.webkit.WebViewClient
    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
        t60.m214695b6(webView, "view");
        t60.m214695b6(str, "url");
        return false;
    }
}
