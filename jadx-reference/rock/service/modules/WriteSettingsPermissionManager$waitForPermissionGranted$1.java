package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {129}, m214405m = "waitForPermissionGranted")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$waitForPermissionGranted$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0327b2 f53018a0;

    /* renamed from: a1 */
    public int f53019a1;

    /* renamed from: a2 */
    public int f53020a2;

    /* renamed from: a3 */
    public long f53021a3;

    /* renamed from: a4 */
    public /* synthetic */ Object f53022a4;

    /* renamed from: a5 */
    public final /* synthetic */ C0327b2 f53023a5;

    /* renamed from: a6 */
    public int f53024a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$waitForPermissionGranted$1(C0327b2 c0327b2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53023a5 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53022a4 = obj;
        this.f53024a6 |= Integer.MIN_VALUE;
        return this.f53023a5.m211755g2(0, 0L, this);
    }
}
