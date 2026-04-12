package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.LocalHttpServer", m214403f = "LocalHttpServer.kt", m214404l = {531}, m214405m = "executeLockScreen")
/* loaded from: classes2.dex */
final class LocalHttpServer$executeLockScreen$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0322a7 f52787a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f52788a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0322a7 f52789a2;

    /* renamed from: a3 */
    public int f52790a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalHttpServer$executeLockScreen$1(C0322a7 c0322a7, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52789a2 = c0322a7;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52788a1 = obj;
        this.f52790a3 |= Integer.MIN_VALUE;
        return this.f52789a2.m211599a4(this);
    }
}
