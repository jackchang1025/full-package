package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.MiuiSteps", m214403f = "MiuiSteps.kt", m214404l = {1439}, m214405m = "scrollDownWithStability")
/* loaded from: classes2.dex */
final class MiuiSteps$scrollDownWithStability$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public boolean f54448a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f54449a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0367a4 f54450a2;

    /* renamed from: a3 */
    public int f54451a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MiuiSteps$scrollDownWithStability$1(C0367a4 c0367a4, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54450a2 = c0367a4;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54449a1 = obj;
        this.f54451a3 |= Integer.MIN_VALUE;
        return this.f54450a2.m212281e6(this);
    }
}
