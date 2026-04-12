package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.GenericSteps", m214403f = "GenericSteps.kt", m214404l = {1463, 1475, 1477, 1479, 1487, 1488}, m214405m = "executeAllFilesAccess")
/* loaded from: classes2.dex */
final class GenericSteps$executeAllFilesAccess$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0364a1 f53898a0;

    /* renamed from: a1 */
    public int f53899a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f53900a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0364a1 f53901a3;

    /* renamed from: a4 */
    public int f53902a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GenericSteps$executeAllFilesAccess$1(C0364a1 c0364a1, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53901a3 = c0364a1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53900a2 = obj;
        this.f53902a4 |= Integer.MIN_VALUE;
        return this.f53901a3.m212129a9(this);
    }
}
