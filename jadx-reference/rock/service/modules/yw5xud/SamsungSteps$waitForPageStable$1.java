package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.SamsungSteps", m214403f = "SamsungSteps.kt", m214404l = {719}, m214405m = "waitForPageStable")
/* loaded from: classes2.dex */
final class SamsungSteps$waitForPageStable$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0370a7 f54717a0;

    /* renamed from: a1 */
    public long f54718a1;

    /* renamed from: a2 */
    public long f54719a2;

    /* renamed from: a3 */
    public int f54720a3;

    /* renamed from: a4 */
    public int f54721a4;

    /* renamed from: a5 */
    public /* synthetic */ Object f54722a5;

    /* renamed from: a6 */
    public final /* synthetic */ C0370a7 f54723a6;

    /* renamed from: a7 */
    public int f54724a7;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SamsungSteps$waitForPageStable$1(C0370a7 c0370a7, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54723a6 = c0370a7;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54722a5 = obj;
        this.f54724a7 |= Integer.MIN_VALUE;
        return this.f54723a6.m212370b5(0L, this);
    }
}
