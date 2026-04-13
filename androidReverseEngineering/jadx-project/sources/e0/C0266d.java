package e0;

import a1.AbstractC0026q;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.guard.wallet.utils.AbstractC0246b;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: e0.d */
/* loaded from: classes.dex */
public final class C0266d extends WebViewClient {

    /* renamed from: a */
    public final AtomicBoolean f440a = new AtomicBoolean(false);

    /* renamed from: b */
    public boolean f441b;

    public C0266d(boolean z2) {
        this.f441b = z2;
    }

    @Override // android.webkit.WebViewClient
    public final void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        Log.d("e0.d", "onPageFinished URL:" + str);
        this.f440a.set(true);
        webView.getSettings().setBlockNetworkImage(false);
    }

    @Override // android.webkit.WebViewClient
    public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        Log.d("e0.d", "onPageStarted URL:" + str);
    }

    @Override // android.webkit.WebViewClient
    public final void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        super.onReceivedError(webView, webResourceRequest, webResourceError);
        if (webResourceError.getDescription() == null || AbstractC0026q.m151B(webResourceError.getDescription().toString())) {
            return;
        }
        Log.d("e0.d", "onReceivedError error:" + webResourceError.getDescription().toString());
        if (!webResourceError.getDescription().toString().contains("ERR_CONNECTION_TIMED_OUT") || this.f440a.get()) {
            return;
        }
        webView.loadUrl("https://m.baidu.com/");
    }

    @Override // android.webkit.WebViewClient
    public final boolean onRenderProcessGone(WebView webView, RenderProcessGoneDetail renderProcessGoneDetail) {
        return true;
    }

    @Override // android.webkit.WebViewClient
    public final boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 0) {
            if (this.f441b) {
                return false;
            }
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.shouldOverrideKeyEvent(webView, keyEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00ae  */
    @Override // android.webkit.WebViewClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        String concat;
        boolean z2;
        if (webResourceRequest.getUrl() != null && !AbstractC0026q.m151B(webResourceRequest.getUrl().getScheme())) {
            if (webResourceRequest.getUrl().getScheme().equalsIgnoreCase("js") && !AbstractC0026q.m151B(webResourceRequest.getUrl().getAuthority())) {
                if (webResourceRequest.getUrl().getAuthority().equalsIgnoreCase("startAccessibility")) {
                    WeakReference weakReference = AbstractC0246b.f395a;
                    if (AbstractC0251g.V0()) {
                        AbstractC0246b.f398d.incrementAndGet();
                    }
                    return true;
                }
                if (webResourceRequest.getUrl().getAuthority().equalsIgnoreCase("startAllowRestricted") || webResourceRequest.getUrl().getAuthority().equalsIgnoreCase("startAllowRestrictedByAppDes")) {
                    WeakReference weakReference2 = AbstractC0246b.f395a;
                    if (AbstractC0251g.Z0(null)) {
                        AbstractC0246b.f398d.incrementAndGet();
                    }
                    return true;
                }
                if (!webResourceRequest.getUrl().getAuthority().equalsIgnoreCase("startAllowRestrictedByAppMgr")) {
                    if (webResourceRequest.getUrl().getAuthority().equalsIgnoreCase("guideStartAccessibility")) {
                        WeakReference weakReference3 = AbstractC0246b.f395a;
                        concat = AbstractC0248d.m607e().concat(AbstractC0249e.m620i() ? "/coloros" : AbstractC0249e.m623l() ? "/oriainos" : AbstractC0249e.m624m() ? "/miui" : AbstractC0249e.m622k() ? "/hios" : Build.BRAND.equalsIgnoreCase("samsung") ? "/oneui" : AbstractC0249e.m618g() ? AbstractC0249e.m619h() ? "/harmonyos" : "/magicos" : "/common");
                        AbstractC0246b.f399e.incrementAndGet();
                    } else if (webResourceRequest.getUrl().getAuthority().equalsIgnoreCase("guideStartAllowRestricted")) {
                        WeakReference weakReference4 = AbstractC0246b.f395a;
                        concat = AbstractC0248d.m607e().concat(AbstractC0249e.m620i() ? "/colorosRelease" : AbstractC0249e.m623l() ? "/oriainosRelease" : AbstractC0249e.m624m() ? "/miuiRelease" : AbstractC0249e.m622k() ? "/hiosRelease" : Build.BRAND.equalsIgnoreCase("samsung") ? "/oneuiRelease" : AbstractC0249e.m618g() ? AbstractC0249e.m619h() ? "/harmonyosRelease" : "/magicosRelease" : "/commonRelease");
                        AbstractC0246b.f400f.incrementAndGet();
                    } else if (webResourceRequest.getUrl().getAuthority().equalsIgnoreCase("guideDeniedAccessibility")) {
                        WeakReference weakReference5 = AbstractC0246b.f395a;
                        concat = AbstractC0248d.m607e().concat(AbstractC0249e.m620i() ? "/colorosDenied" : AbstractC0249e.m623l() ? "/oriainosDenied" : AbstractC0249e.m624m() ? "/miuiDenied" : AbstractC0249e.m622k() ? "/hiosDenied" : Build.BRAND.equalsIgnoreCase("samsung") ? "/oneuiDenied" : AbstractC0249e.m618g() ? AbstractC0249e.m619h() ? "/harmonyosDenied" : "/magicosDenied" : "/commonDenied");
                        AbstractC0246b.f401g.incrementAndGet();
                    }
                    webView.loadUrl(concat);
                    return true;
                }
                WeakReference weakReference6 = AbstractC0246b.f395a;
                try {
                } catch (Exception e2) {
                    AbstractC0026q.m186s("ApplicationUtil", e2);
                }
                if (AbstractC0251g.m653Z() != null) {
                    Intent intent = new Intent("android.settings.MANAGE_APPLICATIONS_SETTINGS");
                    intent.addFlags(268435456);
                    intent.addFlags(8388608);
                    AbstractC0251g.m653Z().startActivity(intent);
                    z2 = true;
                    if (z2) {
                        AbstractC0246b.f398d.incrementAndGet();
                    }
                    return true;
                }
                z2 = false;
                if (z2) {
                }
                return true;
            }
            if (webResourceRequest.getUrl().getScheme().equalsIgnoreCase("baiduboxapp")) {
                return true;
            }
        }
        return super.shouldOverrideUrlLoading(webView, webResourceRequest);
    }
}
