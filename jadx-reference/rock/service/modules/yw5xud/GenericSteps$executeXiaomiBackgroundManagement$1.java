package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.GenericSteps", m214403f = "GenericSteps.kt", m214404l = {2220, 2221, 2236, 2237, 2248, 2250, 2251, 2257, 2258}, m214405m = "executeXiaomiBackgroundManagement")
/* loaded from: classes2.dex */
final class GenericSteps$executeXiaomiBackgroundManagement$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0364a1 f53942a0;

    /* renamed from: a1 */
    public String f53943a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f53944a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0364a1 f53945a3;

    /* renamed from: a4 */
    public int f53946a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GenericSteps$executeXiaomiBackgroundManagement$1(C0364a1 c0364a1, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53945a3 = c0364a1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53944a2 = obj;
        this.f53946a4 |= Integer.MIN_VALUE;
        return this.f53945a3.m212136b6(this);
    }
}
