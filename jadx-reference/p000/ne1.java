package p000;

import android.webkit.WebSettings;
import android.webkit.WebView;
import com.storm.safe.rock.iuzxujjtqev;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ne1 {

    /* renamed from: a0 */
    public final iuzxujjtqev f58510a0;

    /* renamed from: a1 */
    public WebView f58511a1;

    /* renamed from: a2 */
    public fh0 f58512a2;

    static {
        new je1(null);
    }

    public ne1(iuzxujjtqev iuzxujjtqevVar) {
        this.f58510a0 = iuzxujjtqevVar;
    }

    /* renamed from: a0 */
    public final void m214073a0(WebView webView) {
        this.f58511a1 = webView;
        WebSettings settings = webView.getSettings();
        t60.m214694b5(settings, "webView.settings");
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new le1(this));
        webView.setWebChromeClient(new me1(this));
        webView.addJavascriptInterface(new ke1(this), "Android");
    }
}
