package p000;

import com.storm.safe.rock.service.modules.cipher.C0335a1;
import com.storm.safe.rock.service.modules.cipher.C0337a3;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hz */
/* loaded from: classes2.dex */
public final class RunnableC0602hz implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ C0335a1 f56769a0;

    /* renamed from: a1 */
    public final /* synthetic */ long f56770a1;

    public RunnableC0602hz(C0335a1 c0335a1, long j) {
        this.f56769a0 = c0335a1;
        this.f56770a1 = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        C0335a1 c0335a1 = this.f56769a0;
        if (c0335a1.f53297b1) {
            C0337a3 c0337a3 = c0335a1.f53289a3;
            if (c0337a3 == null || !c0337a3.m211845a8()) {
                t60.m214702c3("CipherCaptureManager", "🔷 [主动检测 " + this.f56770a1 + "ms] 尝试创建图案覆盖层");
                c0335a1.m211829e6();
            }
        }
    }
}
