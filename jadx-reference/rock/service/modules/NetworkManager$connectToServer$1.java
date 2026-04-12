package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager", m214403f = "NetworkManager.kt", m214404l = {648, 649}, m214405m = "connectToServer")
/* loaded from: classes2.dex */
final class NetworkManager$connectToServer$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0323a8 f52823a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f52824a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0323a8 f52825a2;

    /* renamed from: a3 */
    public int f52826a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkManager$connectToServer$1(C0323a8 c0323a8, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52825a2 = c0323a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52824a1 = obj;
        this.f52826a3 |= Integer.MIN_VALUE;
        return this.f52825a2.m211642a7(this);
    }
}
