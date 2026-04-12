package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.GenericSteps", m214403f = "GenericSteps.kt", m214404l = {891, 892, 905, 968, 972, 980}, m214405m = "executeNotificationChannel")
/* loaded from: classes2.dex */
final class GenericSteps$executeNotificationChannel$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0364a1 f53916a0;

    /* renamed from: a1 */
    public int f53917a1;

    /* renamed from: a2 */
    public int f53918a2;

    /* renamed from: a3 */
    public /* synthetic */ Object f53919a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0364a1 f53920a4;

    /* renamed from: a5 */
    public int f53921a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GenericSteps$executeNotificationChannel$1(C0364a1 c0364a1, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53920a4 = c0364a1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53919a3 = obj;
        this.f53921a5 |= Integer.MIN_VALUE;
        return this.f53920a4.m212132b2(this);
    }
}
