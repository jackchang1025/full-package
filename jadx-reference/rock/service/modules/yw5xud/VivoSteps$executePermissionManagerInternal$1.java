package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.VivoSteps", m214403f = "VivoSteps.kt", m214404l = {2275}, m214405m = "executePermissionManagerInternal")
/* loaded from: classes2.dex */
final class VivoSteps$executePermissionManagerInternal$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0371a8 f54915a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f54916a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0371a8 f54917a2;

    /* renamed from: a3 */
    public int f54918a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VivoSteps$executePermissionManagerInternal$1(C0371a8 c0371a8, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54917a2 = c0371a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54916a1 = obj;
        this.f54918a3 |= Integer.MIN_VALUE;
        return this.f54917a2.m212408c6(this);
    }
}
