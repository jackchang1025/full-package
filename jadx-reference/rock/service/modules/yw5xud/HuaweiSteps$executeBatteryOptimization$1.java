package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps", m214403f = "HuaweiSteps.kt", m214404l = {2066, 2069, 2078, 2081, 2087, 2094, 2104, 2105, 2115, 2116, 2128, 2129, 2130, 2131, 2136, 2148, 2157}, m214405m = "executeBatteryOptimization")
/* loaded from: classes2.dex */
final class HuaweiSteps$executeBatteryOptimization$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0365a2 f54012a0;

    /* renamed from: a1 */
    public int f54013a1;

    /* renamed from: a2 */
    public int f54014a2;

    /* renamed from: a3 */
    public int f54015a3;

    /* renamed from: a4 */
    public /* synthetic */ Object f54016a4;

    /* renamed from: a5 */
    public final /* synthetic */ C0365a2 f54017a5;

    /* renamed from: a6 */
    public int f54018a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HuaweiSteps$executeBatteryOptimization$1(C0365a2 c0365a2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54017a5 = c0365a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54016a4 = obj;
        this.f54018a6 |= Integer.MIN_VALUE;
        return this.f54017a5.m212165b2(this);
    }
}
