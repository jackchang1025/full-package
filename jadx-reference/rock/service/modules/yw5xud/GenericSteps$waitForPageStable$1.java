package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.GenericSteps", m214403f = "GenericSteps.kt", m214404l = {220}, m214405m = "waitForPageStable")
/* loaded from: classes2.dex */
final class GenericSteps$waitForPageStable$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0364a1 f53958a0;

    /* renamed from: a1 */
    public long f53959a1;

    /* renamed from: a2 */
    public long f53960a2;

    /* renamed from: a3 */
    public int f53961a3;

    /* renamed from: a4 */
    public int f53962a4;

    /* renamed from: a5 */
    public /* synthetic */ Object f53963a5;

    /* renamed from: a6 */
    public final /* synthetic */ C0364a1 f53964a6;

    /* renamed from: a7 */
    public int f53965a7;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GenericSteps$waitForPageStable$1(C0364a1 c0364a1, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53964a6 = c0364a1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53963a5 = obj;
        this.f53965a7 |= Integer.MIN_VALUE;
        return this.f53964a6.m212141c7(0L, this);
    }
}
