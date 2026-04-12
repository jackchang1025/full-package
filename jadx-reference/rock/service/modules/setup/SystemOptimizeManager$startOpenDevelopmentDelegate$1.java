package com.storm.safe.rock.service.modules.setup;

import kotlin.jvm.internal.Lambda;
import p000.C1351vv;
import p000.t60;
import p000.w00;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
final class SystemOptimizeManager$startOpenDevelopmentDelegate$1 extends Lambda implements w00 {

    /* renamed from: a0 */
    public final /* synthetic */ C0360a2 f53784a0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SystemOptimizeManager$startOpenDevelopmentDelegate$1(C0360a2 c0360a2) {
        super(0);
        this.f53784a0 = c0360a2;
    }

    @Override // p000.w00
    public final Object invoke() {
        t60.m214714d6("SystemOptimize", "OpenDevelopmentDelegate 回调 onComplete");
        C0360a2 c0360a2 = this.f53784a0;
        c0360a2.f53820a5.set(SystemOptimizeManager$DevOptState.ENABLE_DEV_OPT_SUCCESS);
        c0360a2.m212093k3();
        return C1351vv.f60710b1;
    }
}
