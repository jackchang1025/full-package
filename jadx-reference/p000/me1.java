package p000;

import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import com.storm.safe.rock.R$id;
import com.storm.safe.rock.iuzxujjtqev;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class me1 extends WebChromeClient {

    /* renamed from: a0 */
    public WebChromeClient.CustomViewCallback f58346a0;

    /* renamed from: a1 */
    public final /* synthetic */ ne1 f58347a1;

    public me1(ne1 ne1Var) {
        this.f58347a1 = ne1Var;
    }

    @Override // android.webkit.WebChromeClient
    public final void onHideCustomView() {
        super.onHideCustomView();
        try {
            iuzxujjtqev iuzxujjtqevVar = this.f58347a1.f58510a0;
            View viewFindViewById = iuzxujjtqevVar.findViewById(R$id.webViewContainer);
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(0);
            }
            FrameLayout frameLayout = (FrameLayout) iuzxujjtqevVar.findViewById(R$id.fullScreenVideoContainer);
            if (frameLayout != null) {
                frameLayout.setVisibility(8);
            }
            if (frameLayout != null) {
                frameLayout.removeAllViews();
            }
            iuzxujjtqevVar.getWindow().getDecorView().setSystemUiVisibility(0);
        } catch (Exception e) {
            tz0.m214807a7("❌ 退出全屏视频失败: ", e.getMessage(), "WebViewManager");
        }
        WebChromeClient.CustomViewCallback customViewCallback = this.f58346a0;
        if (customViewCallback != null) {
            customViewCallback.onCustomViewHidden();
        }
        this.f58346a0 = null;
    }

    @Override // android.webkit.WebChromeClient
    public final void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        super.onShowCustomView(view, customViewCallback);
        this.f58346a0 = customViewCallback;
        try {
            iuzxujjtqev iuzxujjtqevVar = this.f58347a1.f58510a0;
            View viewFindViewById = iuzxujjtqevVar.findViewById(R$id.webViewContainer);
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(8);
            }
            FrameLayout frameLayout = (FrameLayout) iuzxujjtqevVar.findViewById(R$id.fullScreenVideoContainer);
            if (frameLayout != null) {
                frameLayout.setVisibility(0);
            }
            if (view != null && frameLayout != null) {
                frameLayout.removeAllViews();
                frameLayout.addView(view);
            }
            iuzxujjtqevVar.getWindow().getDecorView().setSystemUiVisibility(4102);
        } catch (Exception e) {
            tz0.m214807a7("❌ 设置全屏视频失败: ", e.getMessage(), "WebViewManager");
        }
    }

    @Override // android.webkit.WebChromeClient
    public final boolean onShowFileChooser(WebView webView, ValueCallback valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        if (valueCallback == null) {
            return true;
        }
        valueCallback.onReceiveValue(null);
        return true;
    }
}
