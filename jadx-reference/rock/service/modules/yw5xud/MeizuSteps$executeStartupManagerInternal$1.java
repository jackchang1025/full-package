package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.MeizuSteps", m214403f = "MeizuSteps.kt", m214404l = {207, 213, 221, 225, 228, 232, 235, 239}, m214405m = "executeStartupManagerInternal")
/* loaded from: classes2.dex */
final class MeizuSteps$executeStartupManagerInternal$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0366a3 f54242a0;

    /* renamed from: a1 */
    public String f54243a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f54244a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0366a3 f54245a3;

    /* renamed from: a4 */
    public int f54246a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MeizuSteps$executeStartupManagerInternal$1(C0366a3 c0366a3, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54245a3 = c0366a3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54244a2 = obj;
        this.f54246a4 |= Integer.MIN_VALUE;
        return C0366a3.m212221a3(this.f54245a3, null, this);
    }
}
