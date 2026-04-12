package com.storm.safe.rock.service.modules;

import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager", m214403f = "NetworkManager.kt", m214404l = {1267}, m214405m = "uploadInjectionData-gIAlu-s")
/* loaded from: classes2.dex */
final class NetworkManager$uploadInjectionData$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public /* synthetic */ Object f52881a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0323a8 f52882a1;

    /* renamed from: a2 */
    public int f52883a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkManager$uploadInjectionData$1(C0323a8 c0323a8, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52882a1 = c0323a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        this.f52881a0 = obj;
        this.f52883a2 |= Integer.MIN_VALUE;
        Object objM211673e0 = this.f52882a1.m211673e0(null, this);
        return objM211673e0 == CoroutineSingletons.f57606a0 ? objM211673e0 : new Result(objM211673e0);
    }
}
