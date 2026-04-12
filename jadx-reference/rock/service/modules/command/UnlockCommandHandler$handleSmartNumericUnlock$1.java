package com.storm.safe.rock.service.modules.command;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;
import p000.b60;
import p000.fd0;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.UnlockCommandHandler", m214403f = "UnlockCommandHandler.kt", m214404l = {89, 104, 110, 137, 143, 145}, m214405m = "handleSmartNumericUnlock")
/* loaded from: classes2.dex */
final class UnlockCommandHandler$handleSmartNumericUnlock$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0352a9 f53558a0;

    /* renamed from: a1 */
    public uz0 f53559a1;

    /* renamed from: a2 */
    public String f53560a2;

    /* renamed from: a3 */
    public fd0 f53561a3;

    /* renamed from: a4 */
    public b60 f53562a4;

    /* renamed from: a5 */
    public int f53563a5;

    /* renamed from: a6 */
    public int f53564a6;

    /* renamed from: a7 */
    public int f53565a7;

    /* renamed from: a8 */
    public int f53566a8;

    /* renamed from: a9 */
    public /* synthetic */ Object f53567a9;

    /* renamed from: b0 */
    public final /* synthetic */ C0352a9 f53568b0;

    /* renamed from: b1 */
    public int f53569b1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnlockCommandHandler$handleSmartNumericUnlock$1(C0352a9 c0352a9, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53568b0 = c0352a9;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53567a9 = obj;
        this.f53569b1 |= Integer.MIN_VALUE;
        return this.f53568b0.m211891a7(null, null, this);
    }
}
