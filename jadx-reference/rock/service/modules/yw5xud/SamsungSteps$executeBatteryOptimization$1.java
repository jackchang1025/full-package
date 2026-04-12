package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.SamsungSteps", m214403f = "SamsungSteps.kt", m214404l = {589, 593, 600}, m214405m = "executeBatteryOptimization")
/* loaded from: classes2.dex */
final class SamsungSteps$executeBatteryOptimization$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0370a7 f54696a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f54697a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0370a7 f54698a2;

    /* renamed from: a3 */
    public int f54699a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SamsungSteps$executeBatteryOptimization$1(C0370a7 c0370a7, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54698a2 = c0370a7;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54697a1 = obj;
        this.f54699a3 |= Integer.MIN_VALUE;
        return this.f54698a2.m212364a7(this);
    }
}
