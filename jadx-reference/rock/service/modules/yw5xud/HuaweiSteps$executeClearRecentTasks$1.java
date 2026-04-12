package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps", m214403f = "HuaweiSteps.kt", m214404l = {3427, 3432, 3437, 3448}, m214405m = "executeClearRecentTasks")
/* loaded from: classes2.dex */
final class HuaweiSteps$executeClearRecentTasks$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0365a2 f54026a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f54027a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0365a2 f54028a2;

    /* renamed from: a3 */
    public int f54029a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HuaweiSteps$executeClearRecentTasks$1(C0365a2 c0365a2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54028a2 = c0365a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54027a1 = obj;
        this.f54029a3 |= Integer.MIN_VALUE;
        return this.f54028a2.m212167b4(this);
    }
}
