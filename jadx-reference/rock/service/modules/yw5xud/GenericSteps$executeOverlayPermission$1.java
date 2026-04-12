package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.GenericSteps", m214403f = "GenericSteps.kt", m214404l = {1058, 1059, 1062, 1070, 1071}, m214405m = "executeOverlayPermission")
/* loaded from: classes2.dex */
final class GenericSteps$executeOverlayPermission$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0364a1 f53922a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f53923a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0364a1 f53924a2;

    /* renamed from: a3 */
    public int f53925a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GenericSteps$executeOverlayPermission$1(C0364a1 c0364a1, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53924a2 = c0364a1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53923a1 = obj;
        this.f53925a3 |= Integer.MIN_VALUE;
        return this.f53924a2.m212133b3(this);
    }
}
