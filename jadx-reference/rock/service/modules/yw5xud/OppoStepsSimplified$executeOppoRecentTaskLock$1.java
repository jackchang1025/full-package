package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified", m214403f = "OppoStepsSimplified.kt", m214404l = {5588, 5593, 5603, 5613, 5625, 5655, 5662, 5676, 5685}, m214405m = "executeOppoRecentTaskLock")
/* loaded from: classes2.dex */
final class OppoStepsSimplified$executeOppoRecentTaskLock$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0368a5 f54577a0;

    /* renamed from: a1 */
    public int f54578a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f54579a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0368a5 f54580a3;

    /* renamed from: a4 */
    public int f54581a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OppoStepsSimplified$executeOppoRecentTaskLock$1(C0368a5 c0368a5, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54580a3 = c0368a5;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54579a2 = obj;
        this.f54581a4 |= Integer.MIN_VALUE;
        return this.f54580a3.m212328c6(this);
    }
}
