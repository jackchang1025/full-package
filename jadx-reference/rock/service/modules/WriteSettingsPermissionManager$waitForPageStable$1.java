package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {3118}, m214405m = "waitForPageStable")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$waitForPageStable$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0327b2 f53008a0;

    /* renamed from: a1 */
    public int f53009a1;

    /* renamed from: a2 */
    public int f53010a2;

    /* renamed from: a3 */
    public int f53011a3;

    /* renamed from: a4 */
    public long f53012a4;

    /* renamed from: a5 */
    public long f53013a5;

    /* renamed from: a6 */
    public long f53014a6;

    /* renamed from: a7 */
    public /* synthetic */ Object f53015a7;

    /* renamed from: a8 */
    public final /* synthetic */ C0327b2 f53016a8;

    /* renamed from: a9 */
    public int f53017a9;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$waitForPageStable$1(C0327b2 c0327b2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53016a8 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53015a7 = obj;
        this.f53017a9 |= Integer.MIN_VALUE;
        return this.f53016a8.m211754g1(0, 0L, 0L, this);
    }
}
