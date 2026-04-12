package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.MiuiSteps", m214403f = "MiuiSteps.kt", m214404l = {467}, m214405m = "interruptibleDelay")
/* loaded from: classes2.dex */
final class MiuiSteps$interruptibleDelay$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public long f54430a0;

    /* renamed from: a1 */
    public long f54431a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f54432a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0367a4 f54433a3;

    /* renamed from: a4 */
    public int f54434a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MiuiSteps$interruptibleDelay$1(C0367a4 c0367a4, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54433a3 = c0367a4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54432a2 = obj;
        this.f54434a4 |= Integer.MIN_VALUE;
        return this.f54433a3.m212272d6(0L, this);
    }
}
