package com.storm.safe.rock.service.modules.command;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.UnlockCommandHandler", m214403f = "UnlockCommandHandler.kt", m214404l = {386}, m214405m = "waitForUnlockResult")
/* loaded from: classes2.dex */
final class UnlockCommandHandler$waitForUnlockResult$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public uz0 f53588a0;

    /* renamed from: a1 */
    public long f53589a1;

    /* renamed from: a2 */
    public long f53590a2;

    /* renamed from: a3 */
    public /* synthetic */ Object f53591a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0352a9 f53592a4;

    /* renamed from: a5 */
    public int f53593a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnlockCommandHandler$waitForUnlockResult$1(C0352a9 c0352a9, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53592a4 = c0352a9;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53591a3 = obj;
        this.f53593a5 |= Integer.MIN_VALUE;
        return this.f53592a4.m211894b2(null, 0L, this);
    }
}
