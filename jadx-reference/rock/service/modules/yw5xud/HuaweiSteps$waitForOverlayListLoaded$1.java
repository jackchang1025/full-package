package com.storm.safe.rock.service.modules.yw5xud;

import java.util.ArrayList;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps", m214403f = "HuaweiSteps.kt", m214404l = {3080, 3104, 3113, 3123, 3136}, m214405m = "waitForOverlayListLoaded")
/* loaded from: classes2.dex */
final class HuaweiSteps$waitForOverlayListLoaded$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0365a2 f54141a0;

    /* renamed from: a1 */
    public ArrayList f54142a1;

    /* renamed from: a2 */
    public long f54143a2;

    /* renamed from: a3 */
    public long f54144a3;

    /* renamed from: a4 */
    public long f54145a4;

    /* renamed from: a5 */
    public /* synthetic */ Object f54146a5;

    /* renamed from: a6 */
    public final /* synthetic */ C0365a2 f54147a6;

    /* renamed from: a7 */
    public int f54148a7;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HuaweiSteps$waitForOverlayListLoaded$1(C0365a2 c0365a2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54147a6 = c0365a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54146a5 = obj;
        this.f54148a7 |= Integer.MIN_VALUE;
        return this.f54147a6.m212214h2(0L, this);
    }
}
