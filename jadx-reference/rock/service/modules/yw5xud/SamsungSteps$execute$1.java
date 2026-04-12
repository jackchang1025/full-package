package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.SamsungSteps", m214403f = "SamsungSteps.kt", m214404l = {372, 377, 382, 387, 392}, m214405m = "execute")
/* loaded from: classes2.dex */
final class SamsungSteps$execute$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0370a7 f54679a0;

    /* renamed from: a1 */
    public int f54680a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f54681a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0370a7 f54682a3;

    /* renamed from: a4 */
    public int f54683a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SamsungSteps$execute$1(C0370a7 c0370a7, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54682a3 = c0370a7;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54681a2 = obj;
        this.f54683a4 |= Integer.MIN_VALUE;
        return this.f54682a3.m212361a4(this);
    }
}
