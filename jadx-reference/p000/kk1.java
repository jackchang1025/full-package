package p000;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class kk1 extends WebChromeClient {
    @Override // android.webkit.WebChromeClient
    public final boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        t60.m214695b6(webView, "view");
        t60.m214695b6(str, "url");
        t60.m214695b6(str2, "message");
        t60.m214695b6(jsResult, "result");
        jsResult.confirm();
        return true;
    }
}
