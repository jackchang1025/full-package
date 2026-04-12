package com.storm.safe.rock.network;

import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.network.HttpManager", m214403f = "HttpManager.kt", m214404l = {113}, m214405m = "register-gIAlu-s")
/* loaded from: classes2.dex */
final class HttpManager$register$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public /* synthetic */ Object f52207a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0268a1 f52208a1;

    /* renamed from: a2 */
    public int f52209a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HttpManager$register$1(C0268a1 c0268a1, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52208a1 = c0268a1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52207a0 = obj;
        this.f52209a2 |= Integer.MIN_VALUE;
        Object objM211372a2 = this.f52208a1.m211372a2(null, this);
        return objM211372a2 == CoroutineSingletons.f57606a0 ? objM211372a2 : new Result(objM211372a2);
    }
}
