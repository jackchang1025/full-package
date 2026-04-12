package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.LocalHttpServer", m214403f = "LocalHttpServer.kt", m214404l = {537}, m214405m = "executeWakeScreen")
/* loaded from: classes2.dex */
final class LocalHttpServer$executeWakeScreen$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0322a7 f52791a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f52792a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0322a7 f52793a2;

    /* renamed from: a3 */
    public int f52794a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalHttpServer$executeWakeScreen$1(C0322a7 c0322a7, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52793a2 = c0322a7;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52792a1 = obj;
        this.f52794a3 |= Integer.MIN_VALUE;
        return this.f52793a2.m211600a5(this);
    }
}
