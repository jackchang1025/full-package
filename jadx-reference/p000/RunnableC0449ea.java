package p000;

import android.widget.RelativeLayout;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.yw5xud.C0372a9;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ea */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC0449ea implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f55945a0;

    /* renamed from: a1 */
    public final /* synthetic */ boolean f55946a1;

    /* renamed from: a2 */
    public final /* synthetic */ Object f55947a2;

    public /* synthetic */ RunnableC0449ea(Object obj, boolean z, int i) {
        this.f55945a0 = i;
        this.f55947a2 = obj;
        this.f55946a1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() throws IOException {
        switch (this.f55945a0) {
            case 0:
                boolean z = this.f55946a1;
                C0454ef c0454ef = (C0454ef) this.f55947a2;
                try {
                    if (!z) {
                        c0454ef.m212673a6(c0454ef.f55981a3);
                        c0454ef.f55981a3 = null;
                        c0454ef.f55984a6 = false;
                        return;
                    } else {
                        if (!c0454ef.f55983a5) {
                            t60.m214726f4("BlackScreenOverlay", "⚠️ overlay 未显示，跳过 touchView 创建，防止孤儿视图");
                            return;
                        }
                        if (c0454ef.f55981a3 == null) {
                            c0454ef.m212671a4();
                        }
                        RelativeLayout relativeLayout = c0454ef.f55981a3;
                        if (relativeLayout != null) {
                            relativeLayout.setVisibility(0);
                            c0454ef.f55984a6 = true;
                            return;
                        } else {
                            t60.m214704c5("BlackScreenOverlay", "❌ touchView 创建失败，触摸拦截未生效");
                            c0454ef.f55984a6 = false;
                            return;
                        }
                    }
                } catch (Exception e) {
                    t60.m214705c6("BlackScreenOverlay", "❌ 设置触摸拦截失败", e);
                    return;
                }
            case 1:
                C0372a9 c0372a9 = (C0372a9) this.f55947a2;
                boolean z2 = this.f55946a1;
                t60.m214695b6(c0372a9, "this$0");
                try {
                    String str = "{\"package\":\"" + c0372a9.f53209a1.getPackageName() + "\",\"overseas\":" + z2 + "}";
                    URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912/setAppPackage").openConnection();
                    t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Content-Type", "application/json");
                    httpURLConnection.setConnectTimeout(3000);
                    httpURLConnection.setReadTimeout(3000);
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    try {
                        byte[] bytes = str.getBytes(AbstractC0577hd.f56650a0);
                        t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
                        outputStream.write(bytes);
                        kj1.m213559a6(outputStream, null);
                        httpURLConnection.getResponseCode();
                        httpURLConnection.disconnect();
                        return;
                    } finally {
                    }
                } catch (Exception e2) {
                    e2.getMessage();
                    return;
                }
            default:
                dqtvuisjd dqtvuisjdVar = (dqtvuisjd) this.f55947a2;
                boolean z3 = this.f55946a1;
                dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                dqtvuisjdVar.m211457e6(z3);
                return;
        }
    }

    public /* synthetic */ RunnableC0449ea(boolean z, C0454ef c0454ef) {
        this.f55945a0 = 0;
        this.f55946a1 = z;
        this.f55947a2 = c0454ef;
    }
}
