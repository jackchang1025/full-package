package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {2788, 2794, 2800, 2826, 2827, 2833, 2851, 2857}, m214405m = "attemptCoordinateClick")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$attemptCoordinateClick$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0327b2 f52913a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f52914a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0327b2 f52915a2;

    /* renamed from: a3 */
    public int f52916a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$attemptCoordinateClick$1(C0327b2 c0327b2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52915a2 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52914a1 = obj;
        this.f52916a3 |= Integer.MIN_VALUE;
        return this.f52915a2.m211714a3(this);
    }
}
