package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps", m214403f = "HuaweiSteps.kt", m214404l = {3470, 3475, 3508, 3511, 3530, 3542}, m214405m = "tryLockAppInRecents")
/* loaded from: classes2.dex */
final class HuaweiSteps$tryLockAppInRecents$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0365a2 f54125a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f54126a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0365a2 f54127a2;

    /* renamed from: a3 */
    public int f54128a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HuaweiSteps$tryLockAppInRecents$1(C0365a2 c0365a2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54127a2 = c0365a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54126a1 = obj;
        this.f54128a3 |= Integer.MIN_VALUE;
        return this.f54127a2.m212209g7(this);
    }
}
