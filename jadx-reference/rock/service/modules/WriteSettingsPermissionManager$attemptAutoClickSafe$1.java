package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {606, 636, 641}, m214405m = "attemptAutoClickSafe")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$attemptAutoClickSafe$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public /* synthetic */ Object f52898a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0327b2 f52899a1;

    /* renamed from: a2 */
    public int f52900a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$attemptAutoClickSafe$1(C0327b2 c0327b2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52899a1 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52898a0 = obj;
        this.f52900a2 |= Integer.MIN_VALUE;
        return C0327b2.m211693a0(this.f52899a1, this);
    }
}
