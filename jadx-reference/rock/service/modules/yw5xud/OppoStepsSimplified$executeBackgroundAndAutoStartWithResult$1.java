package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified", m214403f = "OppoStepsSimplified.kt", m214404l = {1758}, m214405m = "executeBackgroundAndAutoStartWithResult")
/* loaded from: classes2.dex */
final class OppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0368a5 f54548a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f54549a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0368a5 f54550a2;

    /* renamed from: a3 */
    public int f54551a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1(C0368a5 c0368a5, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54550a2 = c0368a5;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54549a1 = obj;
        this.f54551a3 |= Integer.MIN_VALUE;
        return this.f54550a2.m212322c0(this);
    }
}
