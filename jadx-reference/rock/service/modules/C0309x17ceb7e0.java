package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Ref$BooleanRef;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {1672, 1700, 1706, 1770}, m214405m = "checkPageAfterClickWithControlTracking")
/* renamed from: com.storm.safe.rock.service.modules.WriteSettingsPermissionManager$checkPageAfterClickWithControlTracking$1 */
/* loaded from: classes2.dex */
final class C0309x17ceb7e0 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0327b2 f52948a0;

    /* renamed from: a1 */
    public String f52949a1;

    /* renamed from: a2 */
    public String f52950a2;

    /* renamed from: a3 */
    public String f52951a3;

    /* renamed from: a4 */
    public Ref$BooleanRef f52952a4;

    /* renamed from: a5 */
    public int f52953a5;

    /* renamed from: a6 */
    public int f52954a6;

    /* renamed from: a7 */
    public /* synthetic */ Object f52955a7;

    /* renamed from: a8 */
    public final /* synthetic */ C0327b2 f52956a8;

    /* renamed from: a9 */
    public int f52957a9;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0309x17ceb7e0(C0327b2 c0327b2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52956a8 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52955a7 = obj;
        this.f52957a9 |= Integer.MIN_VALUE;
        return C0327b2.m211694a1(this.f52956a8, null, null, this);
    }
}
