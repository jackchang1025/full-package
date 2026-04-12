package p000;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class le1 extends WebViewClient {

    /* renamed from: a0 */
    public final /* synthetic */ ne1 f57900a0;

    public le1(ne1 ne1Var) {
        this.f57900a0 = ne1Var;
    }

    @Override // android.webkit.WebViewClient
    public final void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        ne1 ne1Var = this.f57900a0;
        if (str != null) {
            ne1Var.getClass();
        }
        fh0 fh0Var = ne1Var.f58512a2;
    }
}
