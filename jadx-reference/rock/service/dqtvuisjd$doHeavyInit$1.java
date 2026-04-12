package com.storm.safe.rock.service;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd", m214403f = "dqtvuisjd.kt", m214404l = {899, 908}, m214405m = "doHeavyInit")
/* loaded from: classes2.dex */
final class dqtvuisjd$doHeavyInit$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public dqtvuisjd f52508a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f52509a1;

    /* renamed from: a2 */
    public final /* synthetic */ dqtvuisjd f52510a2;

    /* renamed from: a3 */
    public int f52511a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$doHeavyInit$1(dqtvuisjd dqtvuisjdVar, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52510a2 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52509a1 = obj;
        this.f52511a3 |= Integer.MIN_VALUE;
        return dqtvuisjd.m211405a4(this.f52510a2, this);
    }
}
