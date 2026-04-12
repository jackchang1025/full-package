package com.storm.safe.rock.service.modules.command;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.UnlockCommandHandler", m214403f = "UnlockCommandHandler.kt", m214404l = {467}, m214405m = "handleNumericPinInput")
/* loaded from: classes2.dex */
final class UnlockCommandHandler$handleNumericPinInput$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public uz0 f53532a0;

    /* renamed from: a1 */
    public String f53533a1;

    /* renamed from: a2 */
    public int f53534a2;

    /* renamed from: a3 */
    public int f53535a3;

    /* renamed from: a4 */
    public int f53536a4;

    /* renamed from: a5 */
    public int f53537a5;

    /* renamed from: a6 */
    public /* synthetic */ Object f53538a6;

    /* renamed from: a7 */
    public final /* synthetic */ C0352a9 f53539a7;

    /* renamed from: a8 */
    public int f53540a8;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnlockCommandHandler$handleNumericPinInput$1(C0352a9 c0352a9, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53539a7 = c0352a9;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53538a6 = obj;
        this.f53540a8 |= Integer.MIN_VALUE;
        return this.f53539a7.m211889a5(null, null, this);
    }
}
