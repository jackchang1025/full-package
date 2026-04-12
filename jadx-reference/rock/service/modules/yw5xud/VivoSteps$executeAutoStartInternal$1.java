package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.VivoSteps", m214403f = "VivoSteps.kt", m214404l = {2325, 2331, 2338, 2342, 2348, 2357}, m214405m = "executeAutoStartInternal")
/* loaded from: classes2.dex */
final class VivoSteps$executeAutoStartInternal$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0371a8 f54819a0;

    /* renamed from: a1 */
    public int f54820a1;

    /* renamed from: a2 */
    public int f54821a2;

    /* renamed from: a3 */
    public /* synthetic */ Object f54822a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0371a8 f54823a4;

    /* renamed from: a5 */
    public int f54824a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VivoSteps$executeAutoStartInternal$1(C0371a8 c0371a8, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54823a4 = c0371a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54822a3 = obj;
        this.f54824a5 |= Integer.MIN_VALUE;
        return C0371a8.m212372a1(this.f54823a4, this);
    }
}
