package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps", m214403f = "HuaweiSteps.kt", m214404l = {2496, 2504, 2507, 2513, 2516, 2521, 2547, 2549, 2552, 2557, 2560, 2562, 2568, 2569, 2575, 2583, 2590, 2597, 2625}, m214405m = "executeAutoStart")
/* loaded from: classes2.dex */
final class HuaweiSteps$executeAutoStart$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0365a2 f54007a0;

    /* renamed from: a1 */
    public int f54008a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f54009a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0365a2 f54010a3;

    /* renamed from: a4 */
    public int f54011a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HuaweiSteps$executeAutoStart$1(C0365a2 c0365a2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54010a3 = c0365a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54009a2 = obj;
        this.f54011a4 |= Integer.MIN_VALUE;
        return this.f54010a3.m212164b1(this);
    }
}
