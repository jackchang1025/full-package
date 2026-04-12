package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.LocalHttpServer", m214403f = "LocalHttpServer.kt", m214404l = {1701}, m214405m = "handleBlockView")
/* loaded from: classes2.dex */
final class LocalHttpServer$handleBlockView$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0322a7 f52795a0;

    /* renamed from: a1 */
    public String f52796a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f52797a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0322a7 f52798a3;

    /* renamed from: a4 */
    public int f52799a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalHttpServer$handleBlockView$1(C0322a7 c0322a7, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52798a3 = c0322a7;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52797a2 = obj;
        this.f52799a4 |= Integer.MIN_VALUE;
        return this.f52798a3.m211608b6(null, this);
    }
}
