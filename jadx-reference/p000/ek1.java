package p000;

import android.R;
import android.app.ActivityManager;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.iuzxujjtqev$combinedBroadcastReceiver$1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ek1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f56070a0;

    /* renamed from: a1 */
    public final /* synthetic */ iuzxujjtqev f56071a1;

    public /* synthetic */ ek1(iuzxujjtqev iuzxujjtqevVar, int i) {
        this.f56070a0 = i;
        this.f56071a1 = iuzxujjtqevVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.f56070a0;
        iuzxujjtqev iuzxujjtqevVar = this.f56071a1;
        switch (i) {
            case 0:
                iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView = iuzxujjtqevVar.f51958c3;
                if (textView != null) {
                    textView.setText(iuzxujjtqevVar.getString(R$string.status_android11_done));
                    TextView textView2 = iuzxujjtqevVar.f51958c3;
                    if (textView2 != null) {
                        textView2.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_green_dark));
                        return;
                    } else {
                        t60.m214724f2("statusText");
                        throw null;
                    }
                }
                return;
            case 1:
                iuzxujjtqev.C0254a0 c0254a02 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                return;
            case 2:
                iuzxujjtqev.C0254a0 c0254a03 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView3 = iuzxujjtqevVar.f51958c3;
                if (textView3 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView3.setText(iuzxujjtqevVar.getString(R$string.status_camera_denied));
                TextView textView4 = iuzxujjtqevVar.f51958c3;
                if (textView4 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView4.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_red_dark));
                Button button = iuzxujjtqevVar.f51959c4;
                if (button == null) {
                    t60.m214724f2("enableButton");
                    throw null;
                }
                button.setText(iuzxujjtqevVar.getString(R$string.btn_retry));
                Button button2 = iuzxujjtqevVar.f51959c4;
                if (button2 != null) {
                    button2.setEnabled(true);
                    return;
                } else {
                    t60.m214724f2("enableButton");
                    throw null;
                }
            case 3:
                iuzxujjtqev.C0254a0 c0254a04 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView5 = iuzxujjtqevVar.f51958c3;
                if (textView5 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView5.setText(iuzxujjtqevVar.getString(R$string.status_all_permissions_granted));
                TextView textView6 = iuzxujjtqevVar.f51958c3;
                if (textView6 != null) {
                    textView6.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_green_dark));
                    return;
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
            case 4:
                int i2 = iuzxujjtqev$combinedBroadcastReceiver$1.f51977a1;
                try {
                    new Handler(Looper.getMainLooper()).post(new ek1(iuzxujjtqevVar, 5));
                    return;
                } catch (Exception e) {
                    tz0.m214810b0("⚠️ [前台] 设置失败: ", e.getMessage(), "iuzxujjtqev");
                    return;
                }
            case 5:
                int i3 = iuzxujjtqev$combinedBroadcastReceiver$1.f51977a1;
                try {
                    new Handler(Looper.getMainLooper()).postDelayed(new ek1(iuzxujjtqevVar, 6), 100L);
                    return;
                } catch (Exception e2) {
                    t60.m214705c6("iuzxujjtqev", "❌ 异步设置Activity前台显示失败", e2);
                    return;
                }
            case 6:
                int i4 = iuzxujjtqev$combinedBroadcastReceiver$1.f51977a1;
                try {
                    iuzxujjtqevVar.getWindow().addFlags(2097152);
                    iuzxujjtqevVar.getWindow().addFlags(128);
                    new Handler(Looper.getMainLooper()).postDelayed(new ek1(iuzxujjtqevVar, 7), 150L);
                    return;
                } catch (Exception e3) {
                    t60.m214705c6("iuzxujjtqev", "❌ 设置屏幕标志失败", e3);
                    return;
                }
            case 7:
                int i5 = iuzxujjtqev$combinedBroadcastReceiver$1.f51977a1;
                try {
                    Object systemService = iuzxujjtqevVar.getSystemService("activity");
                    t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
                    ((ActivityManager) systemService).moveTaskToFront(iuzxujjtqevVar.getTaskId(), 1);
                    return;
                } catch (Exception e4) {
                    t60.m214705c6("iuzxujjtqev", "❌ moveTaskToFront失败", e4);
                    return;
                }
            default:
                int i6 = iuzxujjtqev$combinedBroadcastReceiver$1.f51977a1;
                try {
                    Object systemService2 = iuzxujjtqevVar.getSystemService("activity");
                    t60.m214693b4(systemService2, "null cannot be cast to non-null type android.app.ActivityManager");
                    ((ActivityManager) systemService2).moveTaskToFront(iuzxujjtqevVar.getTaskId(), 1);
                    return;
                } catch (Exception unused) {
                    return;
                }
        }
    }
}
