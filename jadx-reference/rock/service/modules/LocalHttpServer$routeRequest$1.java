package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.LocalHttpServer", m214403f = "LocalHttpServer.kt", m214404l = {363, 364, 367, 368, 369, 372, 373, 376, 379, 382, 386, 446}, m214405m = "routeRequest")
/* loaded from: classes2.dex */
final class LocalHttpServer$routeRequest$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0322a7 f52818a0;

    /* renamed from: a1 */
    public String f52819a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f52820a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0322a7 f52821a3;

    /* renamed from: a4 */
    public int f52822a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalHttpServer$routeRequest$1(C0322a7 c0322a7, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52821a3 = c0322a7;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52820a2 = obj;
        this.f52822a4 |= Integer.MIN_VALUE;
        return C0322a7.m211584a0(this.f52821a3, null, null, null, this);
    }
}
