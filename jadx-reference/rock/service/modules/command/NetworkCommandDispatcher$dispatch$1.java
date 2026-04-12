package com.storm.safe.rock.service.modules.command;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.NetworkCommandDispatcher", m214403f = "NetworkCommandDispatcher.kt", m214404l = {65, 79}, m214405m = "dispatch")
/* loaded from: classes2.dex */
final class NetworkCommandDispatcher$dispatch$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public String f53520a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f53521a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0350a7 f53522a2;

    /* renamed from: a3 */
    public int f53523a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkCommandDispatcher$dispatch$1(C0350a7 c0350a7, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53522a2 = c0350a7;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53521a1 = obj;
        this.f53523a3 |= Integer.MIN_VALUE;
        return this.f53522a2.m211883a0(null, this);
    }
}
