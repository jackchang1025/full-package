package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Ref$BooleanRef;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {3892}, m214405m = "performCoordinateClick")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$performCoordinateClick$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public Ref$BooleanRef f52978a0;

    /* renamed from: a1 */
    public Ref$BooleanRef f52979a1;

    /* renamed from: a2 */
    public int f52980a2;

    /* renamed from: a3 */
    public /* synthetic */ Object f52981a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0327b2 f52982a4;

    /* renamed from: a5 */
    public int f52983a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$performCoordinateClick$1(C0327b2 c0327b2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52982a4 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52981a3 = obj;
        this.f52983a5 |= Integer.MIN_VALUE;
        return this.f52982a4.m211747f2(0.0f, 0.0f, this);
    }
}
