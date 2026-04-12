package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.MeizuSteps", m214403f = "MeizuSteps.kt", m214404l = {130, 137, 144, 151}, m214405m = "execute")
/* loaded from: classes2.dex */
final class MeizuSteps$execute$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0366a3 f54197a0;

    /* renamed from: a1 */
    public String f54198a1;

    /* renamed from: a2 */
    public int f54199a2;

    /* renamed from: a3 */
    public /* synthetic */ Object f54200a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0366a3 f54201a4;

    /* renamed from: a5 */
    public int f54202a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MeizuSteps$execute$1(C0366a3 c0366a3, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54201a4 = c0366a3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54200a3 = obj;
        this.f54202a5 |= Integer.MIN_VALUE;
        return this.f54201a4.m212227a7(null, this);
    }
}
