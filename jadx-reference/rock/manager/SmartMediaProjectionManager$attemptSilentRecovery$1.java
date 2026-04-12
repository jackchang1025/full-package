package com.storm.safe.rock.manager;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.SmartMediaProjectionManager", m214403f = "SmartMediaProjectionManager.kt", m214404l = {275}, m214405m = "attemptSilentRecovery")
/* loaded from: classes2.dex */
final class SmartMediaProjectionManager$attemptSilentRecovery$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0262a4 f52049a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f52050a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0262a4 f52051a2;

    /* renamed from: a3 */
    public int f52052a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartMediaProjectionManager$attemptSilentRecovery$1(C0262a4 c0262a4, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52051a2 = c0262a4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52050a1 = obj;
        this.f52052a3 |= Integer.MIN_VALUE;
        return C0262a4.m211336a0(this.f52051a2, this);
    }
}
