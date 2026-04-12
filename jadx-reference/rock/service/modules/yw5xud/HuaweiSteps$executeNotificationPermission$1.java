package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps", m214403f = "HuaweiSteps.kt", m214404l = {4279, 4290, 4296, 4307, 4310}, m214405m = "executeNotificationPermission")
/* loaded from: classes2.dex */
final class HuaweiSteps$executeNotificationPermission$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0365a2 f54054a0;

    /* renamed from: a1 */
    public int f54055a1;

    /* renamed from: a2 */
    public int f54056a2;

    /* renamed from: a3 */
    public int f54057a3;

    /* renamed from: a4 */
    public /* synthetic */ Object f54058a4;

    /* renamed from: a5 */
    public final /* synthetic */ C0365a2 f54059a5;

    /* renamed from: a6 */
    public int f54060a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HuaweiSteps$executeNotificationPermission$1(C0365a2 c0365a2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54059a5 = c0365a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54058a4 = obj;
        this.f54060a6 |= Integer.MIN_VALUE;
        return this.f54059a5.m212171b8(this);
    }
}
